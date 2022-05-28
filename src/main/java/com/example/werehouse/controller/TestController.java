package com.example.werehouse.controller;

import com.example.werehouse.component.ClientDatabaseContextHolder;
import com.example.werehouse.model.ClientDatabase;
import com.example.werehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
    private final UserRepository userRepository;

    @GetMapping("/{client}")
    public void testUserConnection(@PathVariable ClientDatabase client) {
        ClientDatabaseContextHolder.set(client);
        userRepository.findAll();
        ClientDatabaseContextHolder.clear();
    }
}
