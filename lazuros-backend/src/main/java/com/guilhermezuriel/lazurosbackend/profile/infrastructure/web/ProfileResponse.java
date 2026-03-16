package com.guilhermezuriel.lazurosbackend.profile.infrastructure.web;

import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;

import java.util.UUID;

record ProfileResponse(UUID id, String descricao) {

    static ProfileResponse from(ProfileResult result) {
        return new ProfileResponse(result.id(), result.descricao());
    }
}
