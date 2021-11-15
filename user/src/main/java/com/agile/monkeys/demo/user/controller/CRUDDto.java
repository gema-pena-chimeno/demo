package com.agile.monkeys.demo.user.controller;

import com.agile.monkeys.demo.data.User;
import com.agile.monkeys.demo.data.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CRUDDto {

    @Pattern(message="Username can contain alphanumeric characters only", regexp = "[a-zA-Z0-9 ]+")
    private String userName;

    @Pattern(message="Password must be secure enough", regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&\\(\\)\\{\\}\\[\\]:;<>,.?/~_+-=|]).{8,32}$")
    private String password;

    @Pattern(message="Invalid values in role", regexp = "(USER_ROLE|ADMIN_ROLE)")
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
