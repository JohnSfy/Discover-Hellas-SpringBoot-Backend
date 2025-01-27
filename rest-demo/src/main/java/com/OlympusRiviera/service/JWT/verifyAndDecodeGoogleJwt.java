package com.OlympusRiviera.service.JWT;

import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

public class verifyAndDecodeGoogleJwt {

    // Static method for verifying and decoding the Google JWT
    public static Map<String, Object> verifyAndDecodeGoogleJwt(String googleJwtToken) throws Exception {
        // Google JWKS URL
        final String GOOGLE_JWKS_URL = "https://www.googleapis.com/oauth2/v3/certs";

        // Parse the JWT
        SignedJWT signedJWT = SignedJWT.parse(googleJwtToken);

        // Fetch Google's JWKS
        JWKSet jwkSet = JWKSet.load(new URL(GOOGLE_JWKS_URL));

        // Get the JWT Header Key ID (kid)
        String kid = signedJWT.getHeader().getKeyID();

        // Find the matching key in the JWKS
        JWK jwk = jwkSet.getKeyByKeyId(kid);
        if (jwk == null) {
            throw new IllegalArgumentException("No matching key found in JWKS for kid: " + kid);
        }

        // Verify the signature
        RSAKey rsaKey = jwk.toRSAKey();
        RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) rsaKey.toPublicKey());
        if (!signedJWT.verify(verifier)) {
            throw new IllegalArgumentException("Invalid JWT signature.");
        }

        // Validate claims
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        String issuer = claims.getIssuer();
        if (!"https://accounts.google.com".equals(issuer)) {
            throw new IllegalArgumentException("Invalid issuer: " + issuer);
        }

//        if (claims.getExpirationTime().before(new Date())) {
//            throw new IllegalArgumentException("Token has expired.");
//        }

        return claims.getClaims();
    }
}
