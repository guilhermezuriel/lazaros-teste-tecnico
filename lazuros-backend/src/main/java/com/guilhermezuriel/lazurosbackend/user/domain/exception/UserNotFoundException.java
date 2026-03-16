package com.guilhermezuriel.lazurosbackend.user.domain.exception;

import com.guilhermezuriel.lazurosbackend.user.domain.UserId;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UserId id) {
        super("Usuário não encontrado: " + id.value());
    }
}
