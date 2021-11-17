package com.agile.monkeys.demo.users.service;

import com.agile.monkeys.demo.data.UserInfo;
import com.agile.monkeys.demo.data.UserRole;
import com.agile.monkeys.demo.users.controller.CRUDDto;
import com.agile.monkeys.demo.users.controller.UserDto;
import com.agile.monkeys.demo.users.domain.UserInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.agile.monkeys.demo.data.UserRole.ADMIN;
import static com.agile.monkeys.demo.data.UserRole.USER;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserInfoRepository userInfoRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserInfo userInfo = userInfoRepository
                .findByUserNameAndActive(username, true)
                .orElseThrow(() -> new NotFoundException("Log-in user not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(userInfo.getUserName())
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .roles(userInfo.getRole().toString())
                .build();
    }

    public UserDto findById(String id) {
        return toUserDto(findUser(id));
    }

    public List<UserDto> findByQuery(String query) {
        return userInfoRepository.findByQuery("%" + query + "%")
                .stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findAll() {
        return userInfoRepository.findAll()
                .stream()
                .filter(UserInfo::isActive)
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto create(CRUDDto dto) {
        UserInfo created = userInfoRepository.save(dto.toUser());

        return toUserDto(created);
    }

    public UserDto update(String id, CRUDDto dto) {
        UserInfo userInfoFromDb = findUser(id);

        validateLastAdmin(id, dto.getRole());

        if (!userInfoFromDb.getUserName().equals(dto.getUserName())) {
            log.info("Change of user name ignored. Field not updatable.");
        }
        userInfoFromDb.setPassword(dto.getPassword());
        userInfoFromDb.setRole(dto.getRole().toString());
        UserInfo updated = userInfoRepository.save(userInfoFromDb);

        return toUserDto(updated);
    }

    public UserDto updateRole(String id, UserRole role) {
        UserInfo userInfoFromDb = findUser(id);

        if (userInfoFromDb.getRole().equals(role.toString())) {
            log.debug("Changes of role ignored. Role already set.");
            return toUserDto(userInfoFromDb);
        }

        validateLastAdmin(id, role);

        userInfoFromDb.setRole(role.toString());
        UserInfo updated = userInfoRepository.save(userInfoFromDb);

        return toUserDto(updated);
    }

    public void delete(String id) {
        UserInfo userInfo = findUser(id);

        userInfo.setActive(false);
        userInfoRepository.save(userInfo);
    }

    private UserDto toUserDto(UserInfo userInfo) {
        return new UserDto(userInfo);
    }

    private UserInfo findUser(String id) {
        UserInfo found = userInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!found.isActive()) {
            throw new NotFoundException("User not found");
        }

        return found;
    }

    private void validateLastAdmin(String id, UserRole role) {
        if (role.equals(USER) && userInfoRepository.isLastAdmin(id, ADMIN.toString())) {
            throw new LastAdminException("Role cannot be change. At least one admin should exist");
        }
    }

}
