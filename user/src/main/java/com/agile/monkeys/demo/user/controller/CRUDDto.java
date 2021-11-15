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
public class CRUDDto {

    private String userName;
    private String password;
    private UserRole role;

    public User toUser() {
        User user = new User();
        user.setUserName(this.userName);
        user.setPassword(this.password);
        user.setActive(true);
        user.setRole(this.role.toString());

        return user;
    }
}
