package com.guilhermezuriel.lazurosbackend.user.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table("users")
class UserJdbcEntity implements Persistable<UUID> {

    @Id
    private UUID id;
    private String nome;

    @MappedCollection(idColumn = "user_id")
    private Set<UserProfileJdbcEntity> perfis = new HashSet<>();

    @Transient
    private boolean isNew = true;

    UserJdbcEntity() {
    }

    UserJdbcEntity(UUID id, String nome, Set<UserProfileJdbcEntity> perfis) {
        this.id = id;
        this.nome = nome;
        this.perfis = perfis;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    String getNome() {
        return nome;
    }

    Set<UserProfileJdbcEntity> getPerfis() {
        return perfis;
    }
}
