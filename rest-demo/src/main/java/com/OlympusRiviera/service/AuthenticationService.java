//package com.OlympusRiviera.service;
//
//import com.OlympusRiviera.model.AuthenticationResponse;
//import com.OlympusRiviera.model.User.User;
//import com.OlympusRiviera.repository.User.UserRepository;
//import com.OlympusRiviera.service.JWT.JwtService;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthenticationService {
//
//    private final UserRepository repository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
//        this.repository = repository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtService = jwtService;
//        this.authenticationManager = authenticationManager;
//    }
//
////    public AuthenticationResponse register(User request){
////        User user = new User();
////        user.setFirstname(request.getFirstname());
////        user.setLastname(request.getLastname());
////        user.setUsername(request.getUsername());
////        user.setEmail(request.getEmail());
////        user.setGoogleid(request.getGoogleid());
////        user.setPassword(passwordEncoder.encode(request.getPassword()));
////        user.setRole(request.getRole());
////        user = repository.save(user);
////
////        String token = jwtService.generateToken(user);
////        return new AuthenticationResponse(token);
////
////    }
//public AuthenticationResponse register(User request){
//    // If googleid is provided, we create a new user with the googleid
//    if (request.getGoogleid() == null || request.getGoogleid().isEmpty()) {
//        throw new IllegalArgumentException("Google ID must be provided for registration.");
//    }
//
//    // Check if a user with the given Google ID already exists
//    User existingUser = repository.findByGoogleid(request.getGoogleid()).orElse(null);
//    if (existingUser != null) {
//        throw new IllegalArgumentException("User with this Google ID already exists.");
//    }
//
//    // Create a new user and set the properties based on the request
//    User user = new User();
//    user.setFirstname(request.getFirstname());
//    user.setLastname(request.getLastname());
//    user.setEmail(request.getEmail());
//    user.setGoogleid(request.getGoogleid());  // Only Google ID is used here
//    user.setRole(request.getRole()); // Set default role as USER, or set based on your needs
//    // Optionally, set the username and password if needed for other purposes (e.g., for login)
//    user.setUsername(request.getUsername()); // You can leave this empty if you don't want to use it
//    user.setPassword(""); // No password needed for Google ID-based registration
//
//    // Save the new user to the database
//    user = repository.save(user);
//
//    // Generate the token
//    String token = jwtService.generateToken(user);
//
//    return new AuthenticationResponse(token);
//}
//
//
////    public AuthenticationResponse authenticate(User request){
////        authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(
////                        request.getUsername(),
////                        request.getPassword()
////                )
////        );
////
//////        User user = repository.findByUsername(request.getUsername()).orElseThrow();
////        String token = jwtService.generateToken(user);
////
////        return new AuthenticationResponse(token);
////    }
//
////    public AuthenticationResponse authenticate(User request){
////        // Authenticate using the credentials (username and password)
////        authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(
////                        request.getUsername(),
////                        request.getPassword()
////                )
////        );
////
////        // Try to find the user by username first
////        User user = null;
////        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
////            user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getUsername()));
////        }
////
////        // If the username is empty or not found, try to find the user by google_id
////        if (user == null && request.getGoogleid() != null && !request.getGoogleid().isEmpty()) {
////            user = repository.findByGoogleid(request.getGoogleid()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with google_id: " + request.getGoogleid()));
////        }
////
////        // If user is still null, throw an exception
////        if (user == null) {
////            throw new UsernameNotFoundException("User not found with either username or google_id.");
////        }
////
////        // Generate a JWT token for the user
////        String token = jwtService.generateToken(user);
////
////        return new AuthenticationResponse(token);
////    }
//
//    public AuthenticationResponse authenticate(User request){
//        // Print the request parameters for debugging
//        System.out.println("Authentication attempt for user with username: " + request.getUsername() + " and googleid: " + request.getGoogleid());
//
//        // Check if username is null, and if so, use googleid instead
//        String usernameOrGoogleId = (request.getUsername() != null && !request.getUsername().isEmpty())
//                ? request.getUsername()
//                : request.getGoogleid();
//
//        if (usernameOrGoogleId == null || usernameOrGoogleId.isEmpty()) {
//            System.out.println("Both username and googleid are empty. Cannot authenticate.");
//            throw new UsernameNotFoundException("Both username and google_id are empty.");
//        }
//
//        // Authenticate using the credentials (username or google_id)
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            usernameOrGoogleId, // use the username or googleid here
//                            request.getPassword()
//                    )
//            );
//            System.out.println("Authentication successful for " + usernameOrGoogleId);
//        } catch (Exception e) {
//            System.out.println("Authentication failed for " + usernameOrGoogleId + ". Error: " + e.getMessage());
//            throw e;  // Rethrow exception to handle it further upstream
//        }
//
//        // Try to find the user by username first
//        User user = null;
//        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
//            System.out.println("Attempting to find user by username: " + request.getUsername());
//            user = repository.findByUsername(request.getUsername()).orElse(null);
//            if (user != null) {
//                System.out.println("Found user by username: " + user.getUsername());
//            } else {
//                System.out.println("No user found with username: " + request.getUsername());
//            }
//        }
//
//        // If the username is empty or not found, try to find the user by google_id
//        if (user == null && request.getGoogleid() != null && !request.getGoogleid().isEmpty()) {
//            System.out.println("Attempting to find user by google_id: " + request.getGoogleid());
//            user = repository.findByGoogleid(request.getGoogleid()).orElse(null);
//            if (user != null) {
//                System.out.println("Found user by google_id: " + user.getGoogleid());
//            } else {
//                System.out.println("No user found with google_id: " + request.getGoogleid());
//            }
//        }
//
//        // If user is still null, throw an exception
//        if (user == null) {
//            System.out.println("User not found with either username or google_id. Username: " + request.getUsername() + ", Google ID: " + request.getGoogleid());
//            throw new UsernameNotFoundException("User not found with either username or google_id.");
//        }
//
//        // Generate a JWT token for the user
//        String token = jwtService.generateToken(user);
//        System.out.println("Generated JWT token for user: " + user.getUsername());
//
//        return new AuthenticationResponse(token);
//    }
//
//
//
//}
