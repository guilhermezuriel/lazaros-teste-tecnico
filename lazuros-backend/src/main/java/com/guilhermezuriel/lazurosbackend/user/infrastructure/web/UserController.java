package com.guilhermezuriel.lazurosbackend.user.infrastructure.web;

import com.guilhermezuriel.lazurosbackend.user.UserService;
import com.guilhermezuriel.lazurosbackend.user.application.dto.CreateUserCommand;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UpdateUserCommand;
import com.guilhermezuriel.lazurosbackend.user.domain.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        var result = userService.create(new CreateUserCommand(request.nome(), request.perfis()));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(result));
    }

    @GetMapping
    ResponseEntity<List<UserResponse>> listAll() {
        var results = userService.listAll()
                .stream()
                .map(UserResponse::from)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        var result = userService.getById(UserId.of(id));
        return ResponseEntity.ok(UserResponse.from(result));
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody UserRequest request) {
        var result = userService.update(UserId.of(id), new UpdateUserCommand(request.nome(), request.perfis()));
        return ResponseEntity.ok(UserResponse.from(result));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(UserId.of(id));
        return ResponseEntity.noContent().build();
    }
}
