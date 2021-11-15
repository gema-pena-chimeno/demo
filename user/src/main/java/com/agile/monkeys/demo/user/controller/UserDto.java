package com.agile.monkeys.demo.user.controller;

import com.agile.monkeys.demo.data.User;
import com.agile.monkeys.demo.data.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;

    private String userName;

    private UserRole role;

    public UserDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.role = UserRole.valueOf(user.getRole());
    }
}
