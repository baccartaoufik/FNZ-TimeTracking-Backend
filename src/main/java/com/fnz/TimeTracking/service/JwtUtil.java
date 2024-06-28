package com.fnz.TimeTracking.service;
import com.fnz.TimeTracking.model.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key"; //Json secret Key used as token signature


    // Method to generate a JWT token for the user
    public String generateToken(Utilisateur userDetails) {
        Map<String, Object> claims = new HashMap<>(); // Claims to be included in the token
        return createToken(claims, userDetails.getEmail()); // Generate the token with claims and subject (email)
    }

    // Method to create the JWT token
    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Set custom claims
                .setSubject(subject) // Set the subject (usually the user's email or username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued at time to the current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Set the expiration time to 1 hour(1000 milliseconds )
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token with the secret key using HS256 algorithm
                .compact(); // Build the JWT and serialize it to a compact URL-safe string
    }


    // Method to validate the token against user details
    public Boolean validateToken(String token, Utilisateur userDetails) {
        final String username = extractUsername(token); // Extract username from the token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Check if the username matches and the token is not expired
    }

    // Method to extract the username (subject) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extract the subject (username) from the token claims
    }

    // Method to extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Extract the expiration date from the token claims
    }


    // Generic method to extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimsResolver.apply(claims); // Apply the claims resolver to get the specific claim
    }

    // Method to extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody(); // Parse the token and extract the body (claims)
    }

    // Method to check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Check if the expiration date is before the current date
    }


}