package com.example.werehouse.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String password;
    private String username;
}
