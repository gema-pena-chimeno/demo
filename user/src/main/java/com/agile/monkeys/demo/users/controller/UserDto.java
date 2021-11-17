package com.agile.monkeys.demo.users.controller;

import com.agile.monkeys.demo.data.UserInfo;
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

    public UserDto(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.userName = userInfo.getUserName();
        this.role = UserRole.valueOf(userInfo.getRole());
    }
}
