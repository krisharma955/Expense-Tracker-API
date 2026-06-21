package com.K955.Expense_Tracker_API.Security;

import com.K955.Expense_Tracker_API.Entity.User;
import com.K955.Expense_Tracker_API.Exception.ResourceNotFoundException;
import com.K955.Expense_Tracker_API.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthUtil {

    private final UserRepository userRepository;

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Boolean isTokenValid(String token, User user) {
        return (extractUsername(token).equals(user.getEmail())) && (!isTokenExpired(token));
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public User getCurrentUser() {
        return userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException(getCurrentUsername(), "User"));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

}
