package com.guilhermezuriel.lazurosbackend.user.domain;

import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.user.domain.exception.InvalidUserException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    private final UserId id;
    private String nome;
    private List<ProfileId> perfis;

    private User(UserId id, String nome, List<ProfileId> perfis) {
        this.id = id;
        this.nome = nome;
        this.perfis = new ArrayList<>(perfis);
    }

    public static User create(String nome, List<ProfileId> perfis) {
        validateNome(nome);
        validatePerfis(perfis);
        return new User(UserId.generate(), nome, perfis);
    }

    public static User reconstitute(UserId id, String nome, List<ProfileId> perfis) {
        return new User(id, nome, perfis);
    }

    public void updateNome(String nome) {
        validateNome(nome);
        this.nome = nome;
    }

    public void updatePerfis(List<ProfileId> perfis) {
        validatePerfis(perfis);
        this.perfis = new ArrayList<>(perfis);
    }

    private static void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new InvalidUserException("Nome é obrigatório");
        }
        if (nome.trim().length() < 10) {
            throw new InvalidUserException("Nome deve ter no mínimo 10 caracteres");
        }
    }

    private static void validatePerfis(List<ProfileId> perfis) {
        if (perfis == null || perfis.isEmpty()) {
            throw new InvalidUserException("Usuário deve ter ao menos 1 perfil");
        }
    }

    public UserId getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<ProfileId> getPerfis() {
        return Collections.unmodifiableList(perfis);
    }
}
