package com.agile.monkeys.demo.data;

import java.util.List;
import java.util.stream.Collectors;

public enum UserRole {

    USER_ROLE,
    ADMIN_ROLE;

    public static String regularExpression() {
        return List.of(UserRole.values())
                .stream()
                .map(UserRole::toString)
                .collect(Collectors.joining("|","(",")"));
    }
}
