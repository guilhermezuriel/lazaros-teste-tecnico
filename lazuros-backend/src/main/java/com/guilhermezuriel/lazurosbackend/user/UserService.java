package com.guilhermezuriel.lazurosbackend.user;

import com.guilhermezuriel.lazurosbackend.user.application.dto.CreateUserCommand;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UpdateUserCommand;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UserResult;
import com.guilhermezuriel.lazurosbackend.user.application.usecase.*;
import com.guilhermezuriel.lazurosbackend.user.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ListUsersUseCase listUsersUseCase;

    public UserService(
            CreateUserUseCase createUserUseCase,
            GetUserUseCase getUserUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase,
            ListUsersUseCase listUsersUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
    }

    public UserResult create(CreateUserCommand command) {
        return createUserUseCase.execute(command);
    }

    public UserResult getById(UserId id) {
        return getUserUseCase.execute(id);
    }

    public UserResult update(UserId id, UpdateUserCommand command) {
        return updateUserUseCase.execute(id, command);
    }

    public void delete(UserId id) {
        deleteUserUseCase.execute(id);
    }

    public List<UserResult> listAll() {
        return listUsersUseCase.execute();
    }
}
