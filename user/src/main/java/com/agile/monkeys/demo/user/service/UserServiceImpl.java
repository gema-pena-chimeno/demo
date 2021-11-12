package com.agile.monkeys.demo.user.service;

import com.agile.monkeys.demo.data.Customer;
import com.agile.monkeys.demo.data.User;
import com.agile.monkeys.demo.user.controller.CRUDDto;
import com.agile.monkeys.demo.user.controller.UserDto;
import com.agile.monkeys.demo.user.domain.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserDto findById(String id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return toUserDto(found);
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
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto create(CRUDDto dto) {
        User created = userRepository.save(dto.toUser());

        return toUserDto(created);
    }

    public UserDto update(String id, CRUDDto dto) {
        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userFromDb.setUserName(dto.getUserName());
        userFromDb.setPassword(dto.getPassword());
        User updated = userRepository.save(userFromDb);

        return toUserDto(updated);
    }

    public void delete(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }

    private UserDto toUserDto(User user) {
        return new UserDto(user);
    }

}
