package com.DiscoverHellas.service.JWT.filter;

import com.DiscoverHellas.service.JWT.JWTService;
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
