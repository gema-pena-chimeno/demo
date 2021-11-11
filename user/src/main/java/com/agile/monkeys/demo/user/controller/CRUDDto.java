package com.agile.monkeys.demo.user.controller;

import com.agile.monkeys.demo.data.User;
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

    public User toCustomer() {
        User user = new User();
        user.setUserName(this.userName);
        user.setPassword(this.password);

        return user;
    }
}
