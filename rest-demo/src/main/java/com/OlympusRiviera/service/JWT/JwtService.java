package com.OlympusRiviera.service.JWT;

import com.OlympusRiviera.model.User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "779c95f6ae0dbc2e0e53a56119e532b10ff3338a8a704b3c189184e1f5bb0c10";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user) {
        String usernameOrGoogleId = extractUsername(token); // This will extract either username or google_id from the token

        // If the extracted value is equal to either username or google_id of the user, and the token is not expired, it is valid
        return (usernameOrGoogleId.equals(user.getUsername()) || usernameOrGoogleId.equals(user.getgoogleid))
                && !isTokenExpired(token); // Ensure token is not expired
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user) {
        // Use the username if it's not empty, otherwise fall back to google_id
        String subject = (user.getUsername() != null && !user.getUsername().isEmpty())
                ? user.getUsername()
                : user.getGoogleid();

        // Generate the JWT token
        String token = Jwts.builder()
                .subject(subject) // Set the subject to username or google_id
                .issuedAt(new Date(System.currentTimeMillis())) // Set the issued time
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24 hours expiration
                .signWith(getSigninKey()) // Sign the token
                .compact(); // Build the token

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

