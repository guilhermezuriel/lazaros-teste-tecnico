package com.guilhermezuriel.lazurosbackend.profile.infrastructure.web;

import com.guilhermezuriel.lazurosbackend.profile.domain.exception.InvalidProfileException;
import com.guilhermezuriel.lazurosbackend.profile.ProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(basePackageClasses = ProfileController.class)
class ProfileExceptionHandler {

    @ExceptionHandler(ProfileNotFoundException.class)
    ResponseEntity<Map<String, String>> handleNotFound(ProfileNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "ProfileNotFoundException",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidProfileException.class)
    ResponseEntity<Map<String, String>> handleInvalid(InvalidProfileException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "InvalidProfileException",
                        "message", ex.getMessage()
                ));
    }
}
