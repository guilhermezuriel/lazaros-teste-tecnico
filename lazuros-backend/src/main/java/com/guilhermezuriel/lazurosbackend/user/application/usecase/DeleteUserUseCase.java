package com.guilhermezuriel.lazurosbackend.user.application.usecase;

import com.guilhermezuriel.lazurosbackend.user.domain.UserId;
import com.guilhermezuriel.lazurosbackend.user.domain.UserRepository;
import com.guilhermezuriel.lazurosbackend.user.domain.exception.UserNotFoundException;

public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UserId id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.delete(id);
    }
}
