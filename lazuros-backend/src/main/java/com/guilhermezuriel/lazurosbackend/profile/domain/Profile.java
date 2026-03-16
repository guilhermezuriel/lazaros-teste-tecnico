package com.guilhermezuriel.lazurosbackend.profile.domain;

import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.profile.domain.exception.InvalidProfileException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Profile {

    private final ProfileId id;
    private String descricao;

    private Profile(ProfileId id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static Profile create(String descricao) {
        validateDescricao(descricao);
        return new Profile(ProfileId.generate(), descricao);
    }

    public static List<Profile> create(Set<String> descricoes) {
        List<Profile> profiles = new ArrayList<>();
        for(String descricao: descricoes){
            validateDescricao(descricao);
            profiles.add( new Profile(ProfileId.generate(), descricao));
        }
        return profiles;
    }

    public static Profile reconstitute(ProfileId id, String descricao) {
        return new Profile(id, descricao);
    }

    public void updateDescricao(String descricao) {
        validateDescricao(descricao);
        this.descricao = descricao;
    }

    private static void validateDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new InvalidProfileException("Descrição é obrigatória");
        }
        if (descricao.trim().length() < 5) {
            throw new InvalidProfileException("Descrição deve ter no mínimo 5 caracteres");
        }
    }

    public ProfileId getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
