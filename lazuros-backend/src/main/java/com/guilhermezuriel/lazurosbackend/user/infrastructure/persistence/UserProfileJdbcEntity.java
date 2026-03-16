package com.guilhermezuriel.lazurosbackend.user.infrastructure.persistence;

import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("user_profiles")
class UserProfileJdbcEntity {

    private UUID profileId;

    UserProfileJdbcEntity() {
    }

    UserProfileJdbcEntity(UUID profileId) {
        this.profileId = profileId;
    }

    UUID getProfileId() {
        return profileId;
    }
}
