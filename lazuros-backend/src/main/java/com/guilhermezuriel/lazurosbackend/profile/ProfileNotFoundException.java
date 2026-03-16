package com.guilhermezuriel.lazurosbackend.profile;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(ProfileId id) {
        super("Perfil não encontrado: " + id.value());
    }
}
