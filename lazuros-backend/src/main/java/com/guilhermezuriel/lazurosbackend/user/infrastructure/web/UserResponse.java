package com.guilhermezuriel.lazurosbackend.user.infrastructure.web;

import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UserResult;

import java.util.List;
import java.util.UUID;

record UserResponse(UUID id, String nome, List<ProfileResponse> perfis) {

    record ProfileResponse(UUID id, String descricao) {
        static ProfileResponse from(ProfileResult result) {
            return new ProfileResponse(result.id(), result.descricao());
        }
    }

    static UserResponse from(UserResult result) {
        return new UserResponse(
                result.id(),
                result.nome(),
                result.perfis().stream().map(ProfileResponse::from).toList()
        );
    }
}
