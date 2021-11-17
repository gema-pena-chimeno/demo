package com.agile.monkeys.demo.users.service;

import com.agile.monkeys.demo.data.UserRole;
import com.agile.monkeys.demo.users.controller.CRUDDto;
import com.agile.monkeys.demo.users.controller.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto findById(String id);

    List<UserDto> findByQuery(String query);

    List<UserDto> findAll();

    @Transactional
    UserDto create(CRUDDto dto);

    @Transactional
    UserDto update(String id, CRUDDto dto);

    @Transactional
    UserDto updateRole(String id, UserRole role);

    @Transactional
    void delete(String id);

}

