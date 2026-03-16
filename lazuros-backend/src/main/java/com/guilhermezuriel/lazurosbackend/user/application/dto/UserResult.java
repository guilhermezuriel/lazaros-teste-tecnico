package com.guilhermezuriel.lazurosbackend.user.application.dto;

import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;
import com.guilhermezuriel.lazurosbackend.user.domain.User;

import java.util.List;
import java.util.UUID;

public record UserResult(UUID id, String nome, List<ProfileResult> perfis) {

    public static UserResult from(User user, List<ProfileResult> perfis) {
        return new UserResult(
                user.getId().value(),
                user.getNome(),
                perfis
        );
    }
}
