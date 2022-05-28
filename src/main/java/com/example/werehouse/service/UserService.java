package com.example.werehouse.service;

import com.example.werehouse.model.User;
import com.example.werehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new EntityExistsException("User " + name + " doesn't exist in database"));
    }

    public User findByNameAndPassword(String name, String password) {
        User user = findByName(name);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new BadCredentialsException("Invalid username or password");
    }

    public UserDetails mapUserToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(),
                Collections.emptyList());
    }
}
