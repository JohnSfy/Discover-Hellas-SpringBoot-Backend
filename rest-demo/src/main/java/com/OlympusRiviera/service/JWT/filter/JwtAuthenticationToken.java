//package com.OlympusRiviera.service.JWT.filter;
//
//import lombok.Getter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.Collections;
//import java.util.Map;
//
//public class JwtAuthenticationToken extends AbstractAuthenticationToken {
//
//    private final Map<String, Object> claims;
//    @Getter
//    private final String role;
//
//    public JwtAuthenticationToken(Map<String, Object> claims, String role) {
//        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
//        this.claims = claims;
//        this.role = role;
//        setAuthenticated(true);
//    }
//
//    @Override
//    public Object getCredentials() {
//        return null;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return new User((String) claims.get("email"), "", getAuthorities());
//    }
//
//}

package com.OlympusRiviera.service.JWT.filter;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Map;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationToken.class);

    private final Map<String, Object> claims;
    @Getter
    private final String role;

    public JwtAuthenticationToken(Map<String, Object> claims, String role) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
        this.claims = claims;
        this.role = role;
        setAuthenticated(true);

        // Log the creation of the JwtAuthenticationToken
        logger.info("a1a1a1a1 - JwtAuthenticationToken created with role: {}", role);
    }

    @Override
    public Object getCredentials() {
        // Log the call to getCredentials
        logger.debug("a1a1a1a1 - getCredentials called, returning null.");
        return null;
    }

    @Override
    public Object getPrincipal() {
        // Log the call to getPrincipal
        logger.debug("a1a1a1a1 - getPrincipal called.");

        String email = (String) claims.get("email");
        if (email == null) {
            email = "default@example.com";  // Handle null email case
            logger.warn("a1a1a1a1 - Email not found in claims, using default: {}", email);
        } else {
            logger.info("a1a1a1a1 - Email found: {}", email);
        }

        return new User(email, "", getAuthorities());
    }
}

