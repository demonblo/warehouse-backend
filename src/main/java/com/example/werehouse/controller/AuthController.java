package com.example.werehouse.controller;

import com.example.werehouse.dto.JwtRequest;
import com.example.werehouse.dto.JwtResponse;
import com.example.werehouse.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IdentityService identityService;

    @PostMapping("/login")
    public JwtResponse auth(@RequestBody JwtRequest request) {
        return identityService.auth(request);
    }
}
