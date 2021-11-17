package com.agile.monkeys.demo.users.service;
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
