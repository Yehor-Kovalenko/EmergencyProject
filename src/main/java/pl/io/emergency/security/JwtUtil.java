package pl.io.emergency.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.io.emergency.entity.users.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtil {
    private final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 5;
    public static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 3;
    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .claims()
                .add("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .and()
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .claims()
                .add("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .and()
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> extractClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            log.info("Token valid.");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getPayload().get("username").toString();
    }

    public String extractId(String token) {
        return extractClaims(token).getPayload().get("userId").toString();
    }
}
