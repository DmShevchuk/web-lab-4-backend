package ru.weblab4.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.weblab4.domain.User;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    @Value("${jwt.secret.access}")
    private String jwtAccessSecret;

    @Value("${jwt.secret.refresh}")
    private String jwtRefreshSecret;


    public String generateAccessToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(336766).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(accessExpiration)
                .signWith(getKeyFromString(jwtAccessSecret))
                .claim("id", user.getId())
                .claim("login", user.getLogin())
                .compact();
    }

    public String generateRefreshToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(refreshExpiration)
                .signWith(getKeyFromString(jwtRefreshSecret))
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, getKeyFromString(jwtAccessSecret));
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, getKeyFromString(jwtRefreshSecret));
    }

    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, getKeyFromString(jwtAccessSecret));
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, getKeyFromString(jwtRefreshSecret));
    }

    private Claims getClaims(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getKeyFromString(String jwtSecretString) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretString));
    }
}
