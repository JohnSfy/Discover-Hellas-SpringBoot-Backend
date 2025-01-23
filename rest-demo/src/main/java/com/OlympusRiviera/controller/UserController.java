package com.OlympusRiviera.controller;

import com.OlympusRiviera.model.User.RegisteredUser;
import com.OlympusRiviera.model.User.Role;
import com.OlympusRiviera.model.User.User;
import com.OlympusRiviera.service.JWT.JWTService;
import com.OlympusRiviera.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.OlympusRiviera.service.JWT.verifyAndDecodeGoogleJwt.verifyAndDecodeGoogleJwt;


@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;
    JWTService jwtService;


    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;

    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, Object> requestBody) throws JsonProcessingException {
        Map<String, Object> response = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Step 1: Extract Google JWT token from request
            String googleJwtToken = (String) requestBody.get("jwt_token");
            String role = (String) requestBody.get("role");

            if (googleJwtToken == null || googleJwtToken.trim().isEmpty()) {
                response.put("error", "Google JWT token is required.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString(response));
            }

            // Step 2: Verify and Decode Google JWT token
            try {
                Map<String, Object> claims = verifyAndDecodeGoogleJwt(googleJwtToken);

                // Extract specific user details from the claims
                String email = (String) claims.get("email");
                String firstName = (String) claims.get("given_name");
                String lastName = (String) claims.get("family_name");
                String googleId = (String) claims.get("sub");
                String picture = (String) claims.get("picture");

                // Step 3: Check if the user already exists
                Optional<User> currentUser = userService.findUserByGoogleId(googleId);
                if (currentUser.isPresent()) {
                    // If user already exists, return a message with the user's details
                    response.put("message", "User with googleId " + googleId + " already exists.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString(response));
                }

                // Handle expiration (exp)
                Object expirationObj = claims.get("exp");
                String expiration = null;

                if (expirationObj instanceof Long) {
                    long expirationLong = (Long) expirationObj;
                    expiration = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(expirationLong * 1000));  // Convert from seconds to milliseconds
                } else if (expirationObj instanceof java.util.Date) {
                    expiration = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((java.util.Date) expirationObj);
                }

                // Add details to the response
                response.put("email", email);
                response.put("firstName", firstName);
                response.put("lastName", lastName);
                response.put("googleId", googleId);
                response.put("picture", picture);
                response.put("expiration", expiration);

                User user = new User();
                user.setEmail(email);
                user.setFirstname(firstName);
                user.setLastname(lastName);
                user.setGoogleId(googleId);
                user.setPhoto(picture);
                user.setRole(Role.valueOf(role));


//                RegisteredUser registeredUser = new RegisteredUser();
//                registeredUser.setUser_id(user.getUser_id());
//                userService.createRegisteredUser(registeredUser);

                if(role.equals("REGISTERED")){
                    RegisteredUser registeredUser = new RegisteredUser(
                            user.getUser_id(),  // User ID (make sure it's properly set)
                            null,  // User name
                            user.getFirstname(),
                            user.getLastname(),
                            user.getEmail(),
                            user.getRole(),
                            null,
                            user.getGoogleId(),
                            user.getPhoto(),
                            null,
                            null,
                            null,  // Assuming hobbies is available
                            null  // Assuming preferences is available
                    );
                    userService.createRegisteredUser(registeredUser);
                }
                //                userService.createUser(user);






                // Now, user_id will be inherited from the User parent class

                response.put("jwt_token", jwtService.generateToken(user));
                response.put("message", "User with id " +  user.getUser_id() + " created successfully");
            } catch (IllegalArgumentException e) {
                response.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString(response));
            } catch (Exception e) {
                response.put("error", "Failed to verify Google JWT token: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString(response));
            }

            return ResponseEntity.ok(objectMapper.writeValueAsString(response));

        } catch (Exception e) {
            response.put("error", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(response));
        }
    }






    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, Object> requestBody) throws JsonProcessingException {
        Map<String, Object> response = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Step 1: Extract Google JWT token from request
            String googleJwtToken = (String) requestBody.get("jwt_token");
            if (googleJwtToken == null || googleJwtToken.trim().isEmpty()) {
                response.put("error", "Google JWT token is required.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString(response));
            }

            // Step 2: Verify and Decode Google JWT token
            try {
                Map<String, Object> claims = verifyAndDecodeGoogleJwt(googleJwtToken);

                // Extract specific user details from the claims
                String googleId = (String) claims.get("sub");

                // Step 3: Check if the user exists
                Optional<User> currentUser = userService.findUserByGoogleId(googleId);
                if (currentUser.isEmpty()) {
                    // If user does not exist, return an error message
                    response.put("error", "User with googleId " + googleId + " does not exist.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.writeValueAsString(response));
                }

                // User exists, generate a new JWT token
                String jwtToken = jwtService.generateToken(currentUser.get());

                // Return the generated token
                response.put("message", "Login successful");
                response.put("jwt_token", jwtToken);

            } catch (IllegalArgumentException e) {
                response.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString(response));
            } catch (Exception e) {
                response.put("error", "Failed to verify Google JWT token: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString(response));
            }

            return ResponseEntity.ok(objectMapper.writeValueAsString(response));

        } catch (Exception e) {
            response.put("error", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(response));
        }
    }

//    @PostMapping("/register/test")
//    public ResponseEntity<String> test(@RequestBody Map<String, Object> requestBody) {
//        String user_id = (String) requestBody.get("user_id");
//        String hobbies = (String) requestBody.get("hobbies").toString();
//        String preferences = (String) requestBody.get("preferences").toString();
//
//        //User existingUser = userService.getUser(googleId);
//
//            // If the user exists, create RegisteredUser
//          //  userService.createRegisteredUser(user_id, hobbies, preferences);
//            return ResponseEntity.ok("Registered user added successfully." + hobbies);
//
//    }

//    @PostMapping("/register/test")
//    public ResponseEntity<Map<String, Object>> test(@RequestBody Map<String, Object> requestBody) {
//        String user_id = (String) requestBody.get("user_id");
//
//        // Cast hobbies as a List<Map<String, Object>> because hobbies is an array of objects
//        List<Map<String, Object>> hobbies = (List<Map<String, Object>>) requestBody.get("hobbies");
//
//        // Cast preferences as Map<String, Object> because it's a map
//        Map<String, Object> preferences = (Map<String, Object>) requestBody.get("preferences");
//        userService.createRegisteredUser(user_id, hobbies.toString(), preferences.toString());
//        // Create the response map to return
//        Map<String, Object> response = new HashMap<>();
//        response.put("user_id", user_id);
//        response.put("preferences", preferences);
//        response.put("hobbies", hobbies);
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/register/test/{id}")
    public ResponseEntity<Optional<User>> getTest(@PathVariable String id) {
        return ResponseEntity.ok(userService.getRegisteredUser(id));
    }


}

