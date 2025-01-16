package com.OlympusRiviera.service.JWT.filter;

import com.OlympusRiviera.service.JWT.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;

@Component
@WebFilter("/*")
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public JwtRequestFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }


//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String jwt = null;
//        String googleId = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7); // Extract the JWT token
//            try {
//                Map<String, Object> claims = jwtService.decodeToken(jwt); // Decode JWT to extract claims
//                googleId = (String) claims.get("googleId"); // Extract the googleId from claims
//            } catch (Exception e) {
//                logger.error("Unable to extract claims from JWT", e);
//            }
//        }
//
//        if (googleId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // If JWT is valid and no authentication exists, set up Spring Security authentication
//            if (jwtService.validateToken(jwt)) {
//                Claims claims = (Claims) jwtService.decodeToken(jwt);
//                String role = claims.get("role", String.class); // Extract role from claims (optional)
//
//                // Create a custom authentication token with googleId as the principal
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        googleId, null, null); // No credentials since it's a token-based auth
//
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((jakarta.servlet.http.HttpServletRequest) request));
//
//                // Set the authentication in the SecurityContext
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        // Continue the filter chain
//        chain.doFilter(request, response);
//    }

    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String jwt = null;
//        String username = null;
//        String role = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7); // Extract the JWT token
//
//            try {
//                // Decode token and extract claims
//                Map<String, Object> claims = jwtService.decodeToken(jwt);
//                username = (String) claims.get("email");
//                role = (String) claims.get("role");
//
//            } catch (Exception e) {
//                logger.error("Unable to extract or validate JWT token", e);
//            }
//        }
//
//        if (username != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // Create an Authentication object with the role
//            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(username, null, authorities);
//
//            // Set the Authentication object in SecurityContext
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//
//        chain.doFilter(request, response);
//    }
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the token from query parameters
        String token = request.getParameter("Authorization");
        System.out.println("to token : " + token);

        if (token != null && token.startsWith("Bearer ")) {
            // Remove the "Bearer " part of the token
            token = token.substring(7);

            System.out.println("TOKEN VALIDETED "+ jwtService.validateToken(token));
            // Validate and parse the token
            if (jwtService.validateToken(token)) {
                Map<String, Object> claims = jwtService.decodeToken(token);
                String role = (String) claims.get("role");
                System.out.println("rolosssss  : " + role);


                SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(claims, role));
                System.out.println("Authentication set in SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());
        }   }

        filterChain.doFilter(request, response);
    }

}
