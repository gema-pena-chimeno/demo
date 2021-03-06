package com.agile.monkeys.demo.users.controller;

import com.agile.monkeys.demo.data.UserInfo;
import com.agile.monkeys.demo.data.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CRUDDto {

    @NotBlank
    @Pattern(message="it can contain alphanumeric characters only", regexp = "[a-zA-Z0-9 ]+")
    private String userName;

    @NotBlank
    @Pattern(message="it must be secure enough", regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&\\(\\)\\{\\}\\[\\]:;<>,.?/~_+-=|]).{8,32}$")
    private String password;

    // @Pattern not needed because of the conversion to UserRole.
    private UserRole role;

    public UserInfo toUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(this.userName);
        userInfo.setPassword(this.password);
        userInfo.setActive(true);
        userInfo.setRole(this.role.toString());

        return userInfo;
    }
}
