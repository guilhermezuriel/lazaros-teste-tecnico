package com.guilhermezuriel.lazurosbackend.user.domain.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }
}
