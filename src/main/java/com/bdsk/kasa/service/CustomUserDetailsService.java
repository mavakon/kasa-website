package com.bdsk.kasa.service;

import com.bdsk.kasa.domain.User;
import com.bdsk.kasa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> possiblyUser = userRepository.findByUsername(username);
        if (possiblyUser.isEmpty()) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        return possiblyUser.get();
    }

}
