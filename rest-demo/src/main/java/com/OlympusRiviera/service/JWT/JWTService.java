

package com.OlympusRiviera.service.JWT;

import com.OlympusRiviera.model.User.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private String secretKey;

    public JWTService() {
        try {
            // Generate a secret key for HMAC-SHA256
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    public String generateToken(User user) {
        // Claims to include in the JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUser_id());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstname());
        claims.put("lastName", user.getLastname());
        claims.put("role", user.getRole().toString());
        claims.put("googleId", user.getGoogleId());
        claims.put("photo", user.getPhoto());
        // Generate the token
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername()) // Subject typically represents the username or principal
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1-hour expiration
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////

//
//import com.OlympusRiviera.model.User.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtParser;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class JWTService {
//
//    private String secretKey;
//
//    public JWTService() {
//        try {
//            // Generate a secret key for HMAC-SHA256
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error generating secret key", e);
//        }
//    }
//
//    public String generateToken(User user) {
//        // Claims to include in the JWT
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", user.getUser_id());
//        claims.put("email", user.getEmail());
//        claims.put("firstName", user.getFirstname());
//        claims.put("lastName", user.getLastname());
//        claims.put("role", user.getRole().toString());
//        claims.put("googleId", user.getGoogleid());
//
//        // Generate the token
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(user.getUsername()) // Subject typically represents the username or principal
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1-hour expiration
//                .signWith(getKey())
//                .compact();
//    }
//
//    // Extract user data from the JWT token
//    // Method to extract user data from the JWT token
//    public Map<String, Object> extractUserDataFromToken(String jwtToken) {
//        // Create JwtParser and use the signing key to validate and parse the JWT
//        JwtParser parser = Jwts.parser()
//                .setSigningKey(getKey()) // Set the signing key
//                .build(); // Use the parser builder API in the latest version of jjwt
//
//        // Parse the JWT token and extract claims
//        Claims claims = parser.parseClaimsJws(jwtToken).getBody();
//
//        // Create a Map to hold the extracted user data
//        Map<String, Object> userData = new HashMap<>();
//        userData.put("email", claims.get("email", String.class));  // Extract email from claims
//        userData.put("firstName", claims.get("firstName", String.class)); // Extract firstName
//        userData.put("lastName", claims.get("lastName", String.class)); // Extract lastName
//        userData.put("googleId", claims.get("googleId", String.class)); // Extract googleId
//        userData.put("token_expiry", claims.get("exp")); // Extract expiration time (as Date)
//
//        return userData; // Return the extracted data
//    }
//
//
//    private Key getKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}
///////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
//
//import com.OlympusRiviera.model.User.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtParser;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class JWTService {
//
//    private final String secretKey;
//    private final long expirationTime;
//
//    public JWTService() {
//        // Load secret key from environment or configuration
//        this.secretKey = System.getenv("Ynl3aGljaGZlZXRoYWxmZmFjaW5nbGFuZ3VhZ2VpbnNpZGVzZWF0c2lsZW5jZWJleW8=");
//        if (this.secretKey == null || this.secretKey.isEmpty()) {
//            throw new IllegalArgumentException("JWT_SECRET_KEY is not set.");
//        }
//
//        // Load expiration time from configuration (default to 1 hour)
//        String expirationEnv = System.getenv("JWT_EXPIRATION_TIME");
//        this.expirationTime = (expirationEnv != null) ? Long.parseLong(expirationEnv) : 3600000;
//    }
//
//    // Generate a JWT token
//    public String generateToken(User user) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", user.getUser_id());
//        claims.put("email", user.getEmail());
//        claims.put("firstName", user.getFirstname());
//        claims.put("lastName", user.getLastname());
//        claims.put("role", user.getRole().toString());
//        claims.put("googleId", user.getGoogleid());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(user.getUsername()) // Set the subject to username
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Expiration time
//                .signWith(getKey()) // Sign with the secret key
//                .compact();
//    }
//
//    // Extract user data from a JWT token
//    public Map<String, Object> extractUserDataFromToken(String jwtToken) {
//        try {
//            JwtParser parser = Jwts.parser()
//                    .setSigningKey(getKey())
//                    .build();
//
//            Claims claims = parser.parseClaimsJws(jwtToken).getBody();
//
//            Map<String, Object> userData = new HashMap<>();
//            userData.put("userId", claims.get("userId", String.class));
//            userData.put("email", claims.get("email", String.class));
//            userData.put("firstName", claims.get("firstName", String.class));
//            userData.put("lastName", claims.get("lastName", String.class));
//            userData.put("role", claims.get("role", String.class));
//            userData.put("googleId", claims.get("googleId", String.class));
//            userData.put("token_expiry", claims.getExpiration());
//
//            return userData;
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid or expired token", e);
//        }
//    }
//
//    // Validate a JWT token
//    public boolean isTokenValid(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(getKey())
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // Get the secret key as a Key object
//    private Key getKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}

////////////////////////////////////////////////////////////////

