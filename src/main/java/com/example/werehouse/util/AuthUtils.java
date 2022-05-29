package com.example.werehouse.util;

import com.example.werehouse.model.ClientDatabase;
import com.example.werehouse.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AuthUtils {
    private final static int tokenExpirationTime = 30 * 60 * 1000;
    private final static String tokenKey = "ut1FfO9sSPjG1OKxVh";

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = new DefaultClaims();
        List<String> unwrappedAuthorities = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("authorities", unwrappedAuthorities);
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

    public ClientDatabase getActiveClientDatabase() {
        String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .iterator().next().getAuthority();
        return ClientDatabase.valueOf(authority);
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public User getCurrentUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
