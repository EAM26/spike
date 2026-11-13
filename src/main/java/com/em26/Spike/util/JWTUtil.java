package com.em26.Spike.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

import static java.time.Instant.now;

@Component
public class JWTUtil {

    private final String SECRET = "43phuneGWKIsWpKoX/shw22zZHZGqRrSmDH7HZ9E3/c=";
    private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    private final Duration EXPIRATION_DATE = Duration.ofDays(100);

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now()))
                .expiration(Date.from(now().plus(EXPIRATION_DATE)))
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build().parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String username, UserDetails userDetails, String token) {
        return username.equals(userDetails.getUsername()) && expDateIsNotExpired(token);
    }

    private boolean expDateIsNotExpired(String token) {
        Date expirationDate = getClaims(token).getExpiration();
        return new Date().before(expirationDate);
    }
}
