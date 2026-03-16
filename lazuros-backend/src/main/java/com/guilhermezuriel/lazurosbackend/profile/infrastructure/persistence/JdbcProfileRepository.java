package com.guilhermezuriel.lazurosbackend.profile.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface JdbcProfileRepository extends CrudRepository<ProfileJdbcEntity, UUID> {
}
