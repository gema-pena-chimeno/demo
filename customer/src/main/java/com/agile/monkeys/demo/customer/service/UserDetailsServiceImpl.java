package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.domain.UserRepository;
import com.agile.monkeys.demo.data.UserInfo;
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
        UserInfo userInfo = userRepository
                .findByUserNameAndActive(username, true)
                .orElseThrow(() -> new NotFoundException("Log-in user not found"));

        return User
                .withUsername(userInfo.getUserName())
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .roles(userInfo.getRole().toString())
                .build();
    }
}
