

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


    public Map<String, Object> decodeToken(String token) {
        // Parse the JWT token without verifying its signature
        Map<String, Object> claims = Jwts.parser()
                .setSigningKey(getKey()) // Use the same secret key used for signing
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public boolean validateToken(String token) {
        try {
            // Parse the token to ensure it's valid and not expired
            Jwts.parser()
                    .setSigningKey(getKey()) // Use the same secret key for verification
                    .build()
                    .parseClaimsJws(token); // This will throw an exception if the token is invalid

            // If no exception is thrown, the token is valid
            return true;
        } catch (Exception e) {
            // Token is either invalid or expired
            return false;
        }
    }


}
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
