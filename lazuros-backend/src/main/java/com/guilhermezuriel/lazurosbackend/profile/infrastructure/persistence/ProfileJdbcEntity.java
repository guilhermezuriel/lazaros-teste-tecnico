package com.guilhermezuriel.lazurosbackend.profile.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("profiles")
public class ProfileJdbcEntity implements Persistable<UUID> {

    @Id
    private UUID id;
    private String descricao;

    @Transient
    private boolean isNew = true;

    public ProfileJdbcEntity() {
    }

    public ProfileJdbcEntity(UUID id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getDescricao() {
        return descricao;
    }
}
