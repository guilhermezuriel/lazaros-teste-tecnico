package com.guilhermezuriel.lazurosbackend.user.infrastructure.web;

import com.guilhermezuriel.lazurosbackend.user.domain.exception.InvalidUserException;
import com.guilhermezuriel.lazurosbackend.user.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(basePackageClasses = UserController.class)
class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<Map<String, String>> handleNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "UserNotFoundException",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidUserException.class)
    ResponseEntity<Map<String, String>> handleInvalid(InvalidUserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "InvalidUserException",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Map.of(
                        "error", ex.getClass().getSimpleName(),
                        "message", ex.getMessage()
                ));
    }
}
