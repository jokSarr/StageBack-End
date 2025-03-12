package com.coursuasz.gpu.stagegpu.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.expiration-time}")
    private Long expirationTime;

    public String createToken(Map<String, Object> claims, String subject, String role, Long id) {
        Date expiryDate = Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + expirationTime));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .claim("role", role)
                .claim("id", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .compact();
    }

    public String generateToken(String username, String role, Long id){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, role, id);
    }

    public String extractUsername(String bearerToken){
        return extractClaimBody(bearerToken, Claims::getSubject);
    }

    public <T> T extractClaimBody(String bearerToken, Function<Claims, T> claimsResolver){
        Jws<Claims> jwsClaims = extractClaims(bearerToken);
        return claimsResolver.apply(jwsClaims.getBody());
    }

    private Jws<Claims> extractClaims(String bearerToken){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(bearerToken);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public Date extractExpiry(String bearerToken){
        return extractClaimBody(bearerToken, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String bearerToken){
        return extractExpiry(bearerToken).before(new Date());
    }
}