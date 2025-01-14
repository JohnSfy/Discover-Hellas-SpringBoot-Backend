//package com.OlympusRiviera.service;
//
//import com.OlympusRiviera.repository.User.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserRepository repository;
//
//    public UserDetailsServiceImpl(UserRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Check if the username ends with "googleusercontent.com" to determine if it's a Google ID
//        if (username.endsWith("googleusercontent.com")) {
//            // If it's a Google ID, search by google_id
//            return repository.findByGoogleid(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User with Google ID not found"));
//        } else {
//            // If it's not a Google ID, search by username
//            return repository.findByUsername(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
//        }
//    }
//
//
//    // Load user by google_id instead of username
//    public UserDetails loadUserByGoogleId(String google_id) throws UsernameNotFoundException {
//        return repository.findByGoogleid(google_id)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with google_id: " + google_id));
//    }
//}
