package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        com.agile.monkeys.demo.data.User user = userRepository.findByUserName(username);
        return User
                .withUsername(user.getUserName())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole().toString())
                .build();
    }
}
