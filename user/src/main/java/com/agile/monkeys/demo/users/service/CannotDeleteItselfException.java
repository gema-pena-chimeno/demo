package com.agile.monkeys.demo.users.service;
public class CannotDeleteItselfException extends RuntimeException {

    public CannotDeleteItselfException(String message) {
        super(message);
    }
}
