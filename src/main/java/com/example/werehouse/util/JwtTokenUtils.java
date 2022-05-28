package com.example.werehouse.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

@UtilityClass
public class JwtTokenUtils {
    private final static int tokenExpirationTime = 30 * 60 * 1000;
    private final static String tokenKey = "ut1FfO9sSPjG1OKxVh";

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = new DefaultClaims();
        claims.put("authorities", authorities);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS512, tokenKey)
                .compact();
    }

    public void verifyToken(String token) throws JwtException {
        Jwts.parser()
                .setSigningKey(tokenKey)
                .parse(token.substring(7));
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenKey)
                .parseClaimsJws(token.substring(7))
                .getBody();
    }
}
