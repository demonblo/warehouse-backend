package com.example.werehouse.component;

import com.example.werehouse.service.UserService;
import com.example.werehouse.util.AuthUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            SecurityContextHolder.getContext().setAuthentication(null);
            chain.doFilter(request, response);
            return;
        }

        try {
            AuthUtils.verifyToken(token);
        } catch (JwtException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getWriter(), e.getMessage());
            return;
        }

        Claims claims = AuthUtils.getClaimsFromToken(token);

        List<String> authorities = claims.get("authorities", ArrayList.class);

        List<SimpleGrantedAuthority> roleList = new ArrayList<>();
        if (authorities != null && !authorities.isEmpty()) {
            roleList = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        String username = claims.getSubject();
        if (username != null) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userService.loadUserByUsername(username),
                            null,
                            roleList
                    )
            );
            chain.doFilter(request, response);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
            response.setStatus(401);
        }
    }
}
