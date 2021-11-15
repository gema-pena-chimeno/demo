package com.agile.monkeys.demo.user.service;

import com.agile.monkeys.demo.data.User;
import com.agile.monkeys.demo.data.UserRole;
import com.agile.monkeys.demo.user.controller.CRUDDto;
import com.agile.monkeys.demo.user.controller.UserDto;
import com.agile.monkeys.demo.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository
                .findByUserNameAndActive(username, true)
                .orElseThrow(() -> new NotFoundException("Log-in user not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole().toString())
                .build();
    }

    public UserDto findById(String id) {
        return toUserDto(findUser(id));
    }

    public List<UserDto> findByQuery(String query) {
        return userRepository.findByQuery(query + "%")
                .stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .filter(User::isActive)
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto create(CRUDDto dto) {
        User created = userRepository.save(dto.toUser());

        return toUserDto(created);
    }

    public UserDto update(String id, CRUDDto dto) {
        User userFromDb = findUser(id);

        validateLastAdmin(id, dto.getRole());

        if (!userFromDb.getUserName().equals(dto.getUserName())) {
            log.info("Change of user name ignored. Field not updatable.");
        }
        userFromDb.setPassword(dto.getPassword());
        userFromDb.setRole(dto.getRole().toString());
        User updated = userRepository.save(userFromDb);

        return toUserDto(updated);
    }

    public UserDto updateRole(String id, UserRole role) {
        User userFromDb = findUser(id);

        if (!userFromDb.getRole().equals(role)) {
            log.debug("Changes of role ignored. Role already set.");
            return toUserDto(userFromDb);
        }

        validateLastAdmin(id, role);

        userFromDb.setRole(role.toString());
        User updated = userRepository.save(userFromDb);

        return toUserDto(updated);
    }

    public void delete(String id) {
        User user = findUser(id);

        user.setActive(false);
        userRepository.save(user);
    }

    private UserDto toUserDto(User user) {
        return new UserDto(user);
    }

    private User findUser(String id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!found.isActive()) {
            throw new NotFoundException("User not found");
        }

        return found;
    }

    private void validateLastAdmin(String id, UserRole role) {
        if (role.equals(UserRole.USER_ROLE) && userRepository.isLastAdmin(id, UserRole.ADMIN_ROLE.toString())) {
            throw new LastAdminException("Role cannot be change. At least one admin should exist");
        }
    }

}
