package com.agile.monkeys.demo.user.service;

import com.agile.monkeys.demo.user.controller.CRUDDto;
import com.agile.monkeys.demo.user.controller.UserDto;

import javax.transaction.Transactional;

public interface UserService {

    @Transactional
    UserDto create(CRUDDto dto);
}
