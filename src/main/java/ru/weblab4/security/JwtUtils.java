package ru.weblab4.security;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setLogin(claims.getSubject());
        return jwtInfoToken;
    }
}