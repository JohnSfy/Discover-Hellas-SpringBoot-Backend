package com.OlympusRiviera.config;//package com.OlympusRiviera.config;
//
//import com.OlympusRiviera.service.JWT.filter.JwtRequestFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfiguration {
//
//    private final JwtRequestFilter jwtRequestFilter;
//
//    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
//
//
//     @Bean
//    public HttpSecurity security(HttpSecurity http) throws Exception {
//        // Configure Spring Security
//        http
//                .csrf().disable() // Disable CSRF protection (since we're using JWT)
//                .authorizeRequests()
//                .requestMatchers("/api/user/**").hasAnyRole("REGISTERED", "PROVIDER") // Restrict access based on roles
//                .anyRequest().authenticated() // Require authentication for other endpoints
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management (JWT-based)
//                .and()
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before Spring's default filter
//
//        return http;
//    }
//}
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Password encoder, in case you want to use it for other parts of the app
//    }
//
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            // You can customize this method to return the user details based on googleId (e.g., user roles).
//            return User.builder()
//                    .username(username) // Replace this with user details retrieval based on googleId
//                    .password("") // Password is not needed for JWT-based authentication
//                    .roles("USER") // Set roles as needed
//                    .build();
//        };
//    }
//}

import com.OlympusRiviera.service.JWT.filter.JwtRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless JWT authentication
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/user/test", "/api/user/register", "/api/user/login").permitAll() // Allow unauthenticated access
//                        .anyRequest().authenticated() // Require authentication for all other endpoints
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Define a local AccessDeniedHandler inside the securityFilterChain method
        AccessDeniedHandler customAccessDeniedHandler = (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
            // Log the error for debugging purposes
            System.out.println("Access Denied: " + accessDeniedException.getMessage());

            // Set the status and response type
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");

            // Write the response body
            try {
                response.getWriter().write("{\"message\": \"Access Denied: You do not have the required role to access this resource.\"}");
            } catch (IOException e) {
                e.printStackTrace(); // Handle any IOException during response writing
            }
        };

        // Configure the HTTP Security
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for stateless JWT
                .authorizeRequests(auth -> auth
                        .requestMatchers("/api/user/register", "/api/user/login").permitAll() // Public APIs
                        // Restrict access to Admin APIs
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // Restrict access to Provider APIs
                        .requestMatchers("/api/provider/**").hasRole("PROVIDER")
                        .anyRequest().authenticated() // All other requests must be authenticated
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) // Add the JWT filter
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(customAccessDeniedHandler) // Add the custom access denied handler
                ); // Remove the deprecated exceptionHandling()

        return http.build();
    }

}
