package com.example.werehouse.service;

import com.example.werehouse.component.ClientDatabaseContextHolder;
import com.example.werehouse.dto.JwtRequest;
import com.example.werehouse.dto.JwtResponse;
import com.example.werehouse.model.ClientDatabase;
import com.example.werehouse.model.User;
import com.example.werehouse.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse auth(JwtRequest request) {
        ClientDatabaseContextHolder.set(ClientDatabase.ASSISTANT);
        User user = userService.findByName(request.getUsername());
        ClientDatabaseContextHolder.clear();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        String token = AuthUtils.generateToken(user.getUsername(), user.getAuthorities());
        return JwtResponse.builder().token(token).build();
    }
}
