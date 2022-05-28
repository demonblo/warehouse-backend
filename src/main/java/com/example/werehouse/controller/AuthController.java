package com.example.werehouse.controller;

import com.example.werehouse.component.ClientDatabaseContextHolder;
import com.example.werehouse.dto.JwtRequest;
import com.example.werehouse.dto.JwtResponse;
import com.example.werehouse.model.ClientDatabase;
import com.example.werehouse.model.User;
import com.example.werehouse.service.UserService;
import com.example.werehouse.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public JwtResponse auth(@RequestBody JwtRequest request) {
       ClientDatabaseContextHolder.set(ClientDatabase.ASSISTANT);
       User user = userService.findByNameAndPassword(request.getUsername(), request.getPassword());
       ClientDatabaseContextHolder.clear();
       String token = JwtTokenUtils.generateToken(user.getUsername(), user.getAuthorities());
       return JwtResponse.builder().token(token).build();
    }
}
