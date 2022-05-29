package com.example.werehouse.service;

import com.example.werehouse.component.ClientDatabaseContextHolder;
import com.example.werehouse.model.ClientDatabase;
import com.example.werehouse.model.User;
import com.example.werehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientDatabaseContextHolder.set(ClientDatabase.ASSISTANT);
        User user = findByName(username);
        ClientDatabaseContextHolder.clear();
        return user;
    }

    public User findByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityExistsException("User " + username + " doesn't exist in the database"));
    }
}
