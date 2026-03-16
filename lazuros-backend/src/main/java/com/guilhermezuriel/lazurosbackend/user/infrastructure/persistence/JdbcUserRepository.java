package com.guilhermezuriel.lazurosbackend.user.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface JdbcUserRepository extends CrudRepository<UserJdbcEntity, UUID> {
}
