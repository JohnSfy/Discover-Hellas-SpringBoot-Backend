package com.OlympusRiviera.controller;

import com.OlympusRiviera.model.Activity.ActivityStat;
import com.OlympusRiviera.model.Destination.DestinationStat;
import com.OlympusRiviera.model.User.ProviderUser;
import com.OlympusRiviera.model.User.RegisteredUser;
import com.OlympusRiviera.model.User.Role;
import com.OlympusRiviera.model.User.User;
import com.OlympusRiviera.model.Visit.Visit;
import com.OlympusRiviera.service.Activity.ActivityStatService;
import com.OlympusRiviera.service.Destination.DestinationStatService;
import com.OlympusRiviera.service.JWT.JWTService;
import com.OlympusRiviera.service.UserService;
import com.OlympusRiviera.service.Visit.VisitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.OlympusRiviera.service.JWT.verifyAndDecodeGoogleJwt.verifyAndDecodeGoogleJwt;


@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;
    JWTService jwtService;

    private final VisitService visitService;
    private final DestinationStatService destinationStatService;
    private final ActivityStatService activityStatService;

    public UserController(UserService userService, JWTService jwtService, VisitService visitService, DestinationStatService destinationStatService, ActivityStatService activityStatService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.visitService = visitService;
        this.destinationStatService = destinationStatService;
        this.activityStatService = activityStatService;
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
                Optional<User> currentUser = Optional.ofNullable(userService.findUserByGoogleId(googleId));
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
                }else if(role.equals("PROVIDER")){
                    ProviderUser providerUser = new ProviderUser(
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
                            null, //companyname
                            null, //tin
                            null, // legal document tin
                            null, // legal document id
                            "PENDING" // Because need approval

                    );
                    userService.createProviderUser(providerUser);

                }else {
                    userService.createUser(user);

                }

                User newUser = userService.findUserByGoogleId(user.getGoogleId());
                Visit visit = new Visit();
                visit.setUser_id(newUser.getUser_id());
                visit.setVisits("[]");
                visitService.createVisit(visit);



                response.put("jwt_token", jwtService.generateToken(newUser));
                response.put("message", "User with id created successfully");
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
                Optional<User> currentUser = Optional.ofNullable(userService.findUserByGoogleId(googleId));
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


    //fetch user profile
    @PreAuthorize("hasRole('ROLE_PROVIDER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_REGISTERED')")
    @GetMapping("/{user_id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String user_id) {
        return ResponseEntity.ok(userService.getUser(user_id));
    }




    @PreAuthorize("hasRole('ROLE_PROVIDER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/updateProfile/Provider/{user_id}")
    public ResponseEntity<Optional<User>> updateProviderProfile(@PathVariable String user_id, @RequestBody ProviderUser updatedUserData) {

        // Print the incoming request details
        System.out.println("Received request to update user profile for user_id: " + user_id);

        // Fetch the user from the service
        Optional<User> currentUserOptional = userService.getUser(user_id);
        System.out.println("Fetched user from DB: " + (currentUserOptional.isPresent() ? currentUserOptional.get() : "No user found"));

        // Check if the user exists
        if (currentUserOptional.isEmpty()) {
            System.out.println("User with id " + user_id + " not found");
            return ResponseEntity.badRequest().body(null); // User not found
        }

        // Get the current user
        User currentUser = currentUserOptional.get();

        // Update common fields (those in the User table)
        if (updatedUserData.getUsername() != null) {
            System.out.println("Updating firstname from " + currentUser.getFirstname() + " to " + updatedUserData.getFirstname());
            currentUser.setUsername(updatedUserData.getUsername());
        }
        if (updatedUserData.getLastname() != null) {
            System.out.println("Updating lastname from " + currentUser.getLastname() + " to " + updatedUserData.getLastname());
            currentUser.setLastname(updatedUserData.getLastname());
        }
        if (updatedUserData.getEmail() != null) {
            System.out.println("Updating email from " + currentUser.getEmail() + " to " + updatedUserData.getEmail());
            currentUser.setEmail(updatedUserData.getEmail());
        }
        if (updatedUserData.getPhone() != null) {
            System.out.println("Updating phone from " + currentUser.getPhone() + " to " + updatedUserData.getPhone());
            currentUser.setPhone(updatedUserData.getPhone());
        }
        if (updatedUserData.getAddress() != null) {
            System.out.println("Updating address from " + currentUser.getAddress() + " to " + updatedUserData.getAddress());
            currentUser.setAddress(updatedUserData.getAddress());
        }

        // Check if the currentUser is a ProviderUser
        if (currentUser instanceof ProviderUser) {
            ProviderUser providerUser = (ProviderUser) currentUser;

            // Update fields specific to ProviderUser
            if (updatedUserData.getCompany_name() != null) {
                System.out.println("Updating company_name from " + providerUser.getCompany_name() + " to " + updatedUserData.getCompany_name());
                providerUser.setCompany_name(updatedUserData.getCompany_name());
            }
            if (updatedUserData.getTin() != null) {
                System.out.println("Updating tin from " + providerUser.getTin() + " to " + updatedUserData.getTin());
                providerUser.setTin(updatedUserData.getTin());
            }
            if (updatedUserData.getLegal_document_tin() != null) {
                System.out.println("Updating legal_document_tin from " + providerUser.getLegal_document_tin() + " to " + updatedUserData.getLegal_document_tin());
                providerUser.setLegal_document_tin(updatedUserData.getLegal_document_tin());
            }
            if (updatedUserData.getLegal_document_id() != null) {
                System.out.println("Updating legal_document_id from " + providerUser.getLegal_document_id() + " to " + updatedUserData.getLegal_document_id());
                providerUser.setLegal_document_id(updatedUserData.getLegal_document_id());
            }
            if (updatedUserData.getStatus() != null) {
                System.out.println("Updating status from " + providerUser.getStatus() + " to " + updatedUserData.getStatus());
                providerUser.setStatus(updatedUserData.getStatus());
            }
        }

        // Save the updated user (whether it's a regular User or ProviderUser)
        userService.updateUser(Optional.of(currentUser));
        System.out.println("User with id " + currentUser.getUser_id() + " updated successfully");

        // Return the updated user as a response
        return ResponseEntity.ok(currentUserOptional);
    }


    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @PutMapping("/updateProfile/Registered/{user_id}")
    public ResponseEntity<Optional<User>> updateRegisteredUser(@PathVariable String user_id, @RequestBody Map<String, Object> requestBody) throws JsonProcessingException {


        // Fetch the user from the service
        Optional<User> currentUserOptional = userService.getUser(user_id);

        // Check if the user exists
        if (currentUserOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // User not found
        }

        // Get the current user
        User currentUser = currentUserOptional.get();

        // Print the current user details before updating

        // Update common fields (those in the User table)
        if (requestBody.containsKey("username")) {
            String username = (String) requestBody.get("username");
            currentUser.setUsername(username);
        }
        if (requestBody.containsKey("firstname")) {
            String firstname = (String) requestBody.get("firstname");
            currentUser.setFirstname(firstname);
        }
        if (requestBody.containsKey("lastname")) {
            String lastname = (String) requestBody.get("lastname");
            currentUser.setLastname(lastname);
        }
        if (requestBody.containsKey("email")) {
            String email = (String) requestBody.get("email");
            currentUser.setEmail(email);
        }
        if (requestBody.containsKey("phone")) {
            String phone = (String) requestBody.get("phone");
            currentUser.setPhone(phone);
        }
        if (requestBody.containsKey("address")) {
            String address = (String) requestBody.get("address");
            currentUser.setAddress(address);
        }


        if (currentUser instanceof RegisteredUser) {
            RegisteredUser registeredUser = (RegisteredUser) currentUser;

            // Update fields specific to RegisteredUser (hobbies and preferences)
            if (requestBody.containsKey("hobbies")) {
                // Cast hobbies as a List<Map<String, Object>> because hobbies is an array of objects
                List<Map<String, Object>> hobbies = (List<Map<String, Object>>) requestBody.get("hobbies");
                System.out.println("Updating hobbies from " + registeredUser.getHobbies() + " to " + hobbies);

                // Convert hobbies to a JSON string if needed, or store the object directly
                ObjectMapper objectMapper = new ObjectMapper();
                String hobbiesJson = objectMapper.writeValueAsString(hobbies);  // Convert to JSON string
                registeredUser.setHobbies(hobbiesJson);  // Store the hobbies as a JSON string or directly if you prefer
            }

            if (requestBody.containsKey("preferences")) {
                // Cast preferences as a List<Map<String, Object>> because it's a map (you might have preferences stored similarly)
                List<Map<String, Object>> preferences = (List<Map<String, Object>>) requestBody.get("preferences");
                System.out.println("Updating preferences from " + registeredUser.getPreferences() + " to " + preferences);

                // Convert preferences to a JSON string if needed, or store the object directly
                ObjectMapper objectMapper = new ObjectMapper();
                String preferencesJson = objectMapper.writeValueAsString(preferences);  // Convert to JSON string
                registeredUser.setPreferences(preferencesJson);  // Store the preferences as a JSON string or directly if you prefer
            }
        }


        // Save the updated user (whether it's a regular User or RegisteredUser)
        userService.updateUser(Optional.of(currentUser));
        System.out.println("User with id " + currentUser.getUser_id() + " updated successfully");

        // Return the updated user as a response
        return ResponseEntity.ok(currentUserOptional);
    }




    //----------------------------Visits-----------------------------





    //Create user Visits and update the entity statistic
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @PostMapping("/visit/create")
    public ResponseEntity<String> createVisit(@RequestBody Map<String, Object> requestBody) throws JsonProcessingException {

        // Extract user_id from the request body
        String user_id = (String) requestBody.get("user_id");

        // Fetch all visits and filter them by user_id
        List<Visit> allVisits = visitService.getAllVisits(); // Assuming visitService.getAllVisits() fetches all visits

        // Filter visits for the provided user_id
        List<Visit> existingVisits = allVisits.stream()
                .filter(visit -> visit.getUser_id().equals(user_id))
                .collect(Collectors.toList());

        // If there are any visits for the user_id, return an error response
        if (!existingVisits.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A visit already exists for user ID " + user_id);
        }

        // Extract the visits array from the request body
        List<Map<String, Object>> visits = (List<Map<String, Object>>) requestBody.get("visits");

        // Convert visits into a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String visitsJson = objectMapper.writeValueAsString(visits);

        // Create a new Visit object
        Visit visitRelationship = new Visit();
        visitRelationship.setUser_id(user_id);
        visitRelationship.setVisits(visitsJson);  // Set the visits JSON string

        // Save the new Visit object
        visitService.createVisit(visitRelationship);

        // Now, process each entity in the visits list to update statistics
        for (Map<String, Object> visit : visits) {
            String entityId = (String) visit.get("entity_id"); // Get entity_id from the visit

            // If the entity is a destination (starts with "dest_")
            if (entityId.startsWith("dest_")) {
                // Fetch all destination statistics
                List<DestinationStat> allDestinationStats = destinationStatService.getAllDestinationStats();

                // Find the destination stat that matches the destination_id (entityId)
                DestinationStat matchingDestinationStat = allDestinationStats.stream()
                        .filter(stat -> stat.getDestination_id().equals(entityId))
                        .findFirst()
                        .orElse(null);

                // If the destination stat exists, increment the total_visits by 1
                if (matchingDestinationStat != null) {
                    matchingDestinationStat.setTotal_visits(matchingDestinationStat.getTotal_visits() + 1);
                    destinationStatService.updateDestinationStat(matchingDestinationStat); // Save the updated destination stat
                }
            }
            // If the entity is an activity (starts with "activ_")
            else if (entityId.startsWith("activ_")) {
                // Fetch all activity statistics
                List<ActivityStat> allActivityStats = activityStatService.getAllActivityStats();

                // Find the activity stat that matches the activity_id (entityId)
                ActivityStat matchingActivityStat = allActivityStats.stream()
                        .filter(stat -> stat.getActivity_id().equals(entityId))
                        .findFirst()
                        .orElse(null);

                // If the activity stat exists, increment the total_visits by 1
                if (matchingActivityStat != null) {
                    matchingActivityStat.setTotal_visits(matchingActivityStat.getTotal_visits() + 1);
                    activityStatService.updateActivityStat(matchingActivityStat); // Save the updated activity stat
                }
            }
        }

        // Return a success message with the visit relationship ID
        return ResponseEntity.ok("Visit with ID " + visitRelationship.getVisit_relationship_id() + " created successfully");
    }





    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/visit/all/{user_id}")
    public ResponseEntity<List<Visit>> getAllVisitsforSpecificUser(@PathVariable String user_id) {
        // Fetch all visits for the given user_id
        List<Visit> visits = visitService.getAllVisits();

        // Filter the visits to only include those belonging to the specific user_id
        List<Visit> userVisits = visits.stream()
               .filter(visit -> visit.getUser_id().equals(user_id))
               .collect(Collectors.toList());


        // Return the list of visits
        return ResponseEntity.ok(userVisits);
    }





    //Update a visit and the Stats for entities ids(total visits)
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @PutMapping("/visit/update/{user_id}")
    public ResponseEntity<String> updateVisitForUser(@PathVariable String user_id,
                                                     @RequestBody Map<String, Object> requestBody) throws JsonProcessingException {

        // Extract the visits array from the request body
        List<Map<String, Object>> visits = (List<Map<String, Object>>) requestBody.get("visits");

        // Convert visits into a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String visitsJson = objectMapper.writeValueAsString(visits);

        // Fetch all visits for all users
        List<Visit> allVisits = visitService.getAllVisits();

        // Filter the visits to find the one for the given user_id
        Visit existingVisit = allVisits.stream()
                .filter(visit -> visit.getUser_id().equals(user_id)) // Filter by user_id
                .findFirst()
                .orElse(null);

        // If the visit doesn't exist for the given user, return an error message
        if (existingVisit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No visits found for user ID " + user_id);
        }

        // Get the old visits from the existing visit JSON string
        ObjectMapper existingVisitMapper = new ObjectMapper();
        List<Map<String, Object>> oldVisits = existingVisitMapper.readValue(existingVisit.getVisits(), List.class);

        // Loop through the new visits to compare with old visits and update the statistics
        for (Map<String, Object> visit : visits) {
            String entityId = (String) visit.get("entity_id");

            // If entity_id is null, skip the current iteration
            if (entityId == null) {
                continue;
            }

            // Check if this entity was visited before (i.e., exists in the old visits list)
            boolean entityAlreadyVisited = oldVisits.stream()
                    .anyMatch(oldVisit -> {
                        // Safely check for null values in old visits
                        Object oldEntityId = oldVisit.get("entity_id");
                        return oldEntityId != null && oldEntityId.equals(entityId);
                    });

            // If the entity is new or its visit count has changed, increment the visit count
            if (!entityAlreadyVisited) {
                // Check if the entity is a destination
                if (entityId.startsWith("dest_")) {
                    // Fetch all destination statistics
                    List<DestinationStat> allDestinationStats = destinationStatService.getAllDestinationStats();

                    // Find the destination stat that matches the destination_id (entityId)
                    DestinationStat matchingDestinationStat = allDestinationStats.stream()
                            .filter(stat -> stat.getDestination_id().equals(entityId))
                            .findFirst()
                            .orElse(null);

                    // If the destination stat exists, increment the total_visits by 1
                    if (matchingDestinationStat != null) {
                        matchingDestinationStat.setTotal_visits(matchingDestinationStat.getTotal_visits() + 1);
                        destinationStatService.updateDestinationStat(matchingDestinationStat); // Save the updated destination stat
                    }
                }
                // Check if the entity is an activity
                else if (entityId.startsWith("activ_")) {
                    // Fetch all activity statistics
                    List<ActivityStat> allActivityStats = activityStatService.getAllActivityStats();

                    // Find the activity stat that matches the activity_id (entityId)
                    ActivityStat matchingActivityStat = allActivityStats.stream()
                            .filter(stat -> stat.getActivity_id().equals(entityId))
                            .findFirst()
                            .orElse(null);

                    // If the activity stat exists, increment the total_visits by 1
                    if (matchingActivityStat != null) {
                        matchingActivityStat.setTotal_visits(matchingActivityStat.getTotal_visits() + 1);
                        activityStatService.updateActivityStat(matchingActivityStat); // Save the updated activity stat
                    }
                }
            }
        }

        // Decrement total_visits for entities that are removed (i.e., were present in old visits but not in new visits)
        for (Map<String, Object> oldVisit : oldVisits) {
            String oldEntityId = (String) oldVisit.get("entity_id");

            // Skip if entity_id is null
            if (oldEntityId == null) {
                continue;
            }

            // Check if the entity is missing from the new visits list
            boolean entityMissingInNewVisits = visits.stream()
                    .noneMatch(newVisit -> {
                        String newEntityId = (String) newVisit.get("entity_id");
                        return newEntityId != null && newEntityId.equals(oldEntityId);
                    });

            // If the entity is missing, decrement the total_visits
            if (entityMissingInNewVisits) {
                // Check if the entity is a destination
                if (oldEntityId.startsWith("dest_")) {
                    // Fetch all destination statistics
                    List<DestinationStat> allDestinationStats = destinationStatService.getAllDestinationStats();

                    // Find the destination stat that matches the destination_id (entityId)
                    DestinationStat matchingDestinationStat = allDestinationStats.stream()
                            .filter(stat -> stat.getDestination_id().equals(oldEntityId))
                            .findFirst()
                            .orElse(null);

                    // If the destination stat exists, decrement the total_visits by 1
                    if (matchingDestinationStat != null && matchingDestinationStat.getTotal_visits() > 0) {
                        matchingDestinationStat.setTotal_visits(matchingDestinationStat.getTotal_visits() - 1);
                        destinationStatService.updateDestinationStat(matchingDestinationStat); // Save the updated destination stat
                    }
                }
                // Check if the entity is an activity
                else if (oldEntityId.startsWith("activ_")) {
                    // Fetch all activity statistics
                    List<ActivityStat> allActivityStats = activityStatService.getAllActivityStats();

                    // Find the activity stat that matches the activity_id (entityId)
                    ActivityStat matchingActivityStat = allActivityStats.stream()
                            .filter(stat -> stat.getActivity_id().equals(oldEntityId))
                            .findFirst()
                            .orElse(null);

                    // If the activity stat exists, decrement the total_visits by 1
                    if (matchingActivityStat != null && matchingActivityStat.getTotal_visits() > 0) {
                        matchingActivityStat.setTotal_visits(matchingActivityStat.getTotal_visits() - 1);
                        activityStatService.updateActivityStat(matchingActivityStat); // Save the updated activity stat
                    }
                }
            }
        }

        // Update the visits information for the found visit
        existingVisit.setVisits(visitsJson); // Set the updated visits data

        // Save the updated visit object
        visitService.updateVisit(existingVisit);

        // Return a success message with the user ID
        return ResponseEntity.ok("Visits for user ID " + user_id + " updated successfully");
    }



}

