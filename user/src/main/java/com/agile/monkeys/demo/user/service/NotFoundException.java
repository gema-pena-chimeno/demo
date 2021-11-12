package com.agile.monkeys.demo.user.service;
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
