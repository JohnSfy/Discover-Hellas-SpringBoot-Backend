package com.OlympusRiviera.service;

import com.OlympusRiviera.model.AuthenticationResponse;
import com.OlympusRiviera.model.User.User;
import com.OlympusRiviera.repository.User.UserRepository;
import com.OlympusRiviera.service.JWT.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request){
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setGoogleid(request.getGoogleid());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user = repository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);

    }

//    public AuthenticationResponse authenticate(User request){
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//
//        User user = repository.findByGoogleid(request.getGoogle_id()).orElseThrow();
////        User user = repository.findByUsername(request.getUsername()).orElseThrow();
//        String token = jwtService.generateToken(user);
//
//        return new AuthenticationResponse(token);
//    }

    public AuthenticationResponse authenticate(User request){
        // Authenticate using the credentials (username and password)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Try to find the user by username first
        User user = null;
        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getUsername()));
        }

        // If the username is empty or not found, try to find the user by google_id
        if (user == null && request.getGoogleid() != null && !request.getGoogleid().isEmpty()) {
            user = repository.findByGoogleid(request.getGoogleid()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with google_id: " + request.getGoogleid()));
        }

        // If user is still null, throw an exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found with either username or google_id.");
        }

        // Generate a JWT token for the user
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

}
