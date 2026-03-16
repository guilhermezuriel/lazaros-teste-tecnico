package com.guilhermezuriel.lazurosbackend.profile.domain.exception;

public class InvalidProfileException extends RuntimeException {

    public InvalidProfileException(String message) {
        super(message);
    }
}
