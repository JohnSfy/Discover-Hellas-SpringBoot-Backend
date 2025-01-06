package com.OlympusRiviera.service.JWT.filter;

import com.OlympusRiviera.service.JWT.JwtService;
import com.OlympusRiviera.service.UserService;
import com.OlympusRiviera.service.impl.UserServiceimpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserServiceimpl userServiceimpl;
    public JwtAuthenticationFilter(JwtService jwtService, UserServiceimpl userServiceimpl) {
        this.jwtService = jwtService;
        this.userServiceimpl = userServiceimpl;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        String user_id = jwtService.extractUserId(token);

        if(user_id != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserServiceimpl userServiceimpl =

            UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            )
        }

    }
}
