package com.OlympusRiviera.service.JWT.filter;

import com.OlympusRiviera.service.JWT.JwtService;
import com.OlympusRiviera.service.UserDetailsServiceImpl;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
////    private final JwtService jwtService;
////    private final UserDetailsServiceImpl userDetailsService;
////
////    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
////        this.jwtService = jwtService;
////        this.userDetailsService = userDetailsService;
////    }
////
////
////    @Override
////    protected void doFilterInternal(
////            @NonNull HttpServletRequest request,
////            @NonNull HttpServletResponse response,
////            @NonNull FilterChain filterChain)
////            throws ServletException, IOException {
////        String authHeader = request.getHeader("Authorization");
////
////        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
////            filterChain.doFilter(request, response);
////            return;
////        }
////
////        String token = authHeader.substring(7);
////        String username = jwtService.extractUsername(token);
////
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////
////            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
////            if (jwtService.isValid(token, userDetails)) {
////                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
////                        userDetails, null, userDetails.getAuthorities()
////                );
////                authToken.setDetails(
////                        new WebAuthenticationDetailsSource().buildDetails(request)
////                );
////                SecurityContextHolder.getContext().setAuthentication(authToken);
////            }
////
////        }
////        filterChain.doFilter(request, response);
////
////    }
//private final JwtService jwtService;
//    private final UserDetailsServiceImpl userDetailsService;
//
//    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // Log to check if the request matches the JWT filter
//        System.out.println("JwtAuthenticationFilter triggered for: " + request.getRequestURI());
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            // Log to show when no token is present
//            System.out.println("No JWT token found in the request");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authHeader.substring(7);
//        String username = jwtService.extractUsername(token);
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            // Log to confirm the username extraction and JWT validation
//            System.out.println("Token validated for username: " + username);
//
//            if (jwtService.isValid(token, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//
//                // Log successful authentication
//                System.out.println("User authenticated: " + username);
//            } else {
//                System.out.println("Invalid token for user: " + username);
//            }
//
//        } else {
//            System.out.println("No user found for token or security context already contains authentication");
//        }
//        filterChain.doFilter(request, response);
//    }
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Skip filtering for login and register endpoints
        String path = request.getServletPath();
        return path.startsWith("/api/login") || path.startsWith("/api/register");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Log to check if the request matches the JWT filter
        System.out.println("JwtAuthenticationFilter triggered for: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Log to show when no token is present
            System.out.println("No JWT token found in the request");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Log to confirm the username extraction and JWT validation
            System.out.println("Token validated for username: " + username);

            if (jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

                // Log successful authentication
                System.out.println("User authenticated: " + username);
            } else {
                System.out.println("Invalid token for user: " + username);
            }

        } else {
            System.out.println("No user found for token or security context already contains authentication");
        }
        filterChain.doFilter(request, response);
    }
}


