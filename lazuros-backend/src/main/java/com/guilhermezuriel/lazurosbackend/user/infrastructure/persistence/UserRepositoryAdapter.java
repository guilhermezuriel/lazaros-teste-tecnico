package com.guilhermezuriel.lazurosbackend.user.infrastructure.persistence;

import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.user.domain.User;
import com.guilhermezuriel.lazurosbackend.user.domain.UserId;
import com.guilhermezuriel.lazurosbackend.user.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class UserRepositoryAdapter implements UserRepository {

    private final JdbcUserRepository jdbcUserRepository;

    UserRepositoryAdapter(JdbcUserRepository jdbcUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
    }

    @Override
    public User save(User user) {
        var entity = toEntity(user);
        var saved = jdbcUserRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jdbcUserRepository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return ((List<UserJdbcEntity>) jdbcUserRepository.findAll())
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void delete(UserId id) {
        jdbcUserRepository.deleteById(id.value());
    }

    @Override
    public boolean existsById(UserId id) {
        return jdbcUserRepository.existsById(id.value());
    }

    private UserJdbcEntity toEntity(User user) {
        var perfisEntities = user.getPerfis().stream()
                .map(profileId -> new UserProfileJdbcEntity(profileId.value()))
                .collect(Collectors.toSet());

        var entity = new UserJdbcEntity(
                user.getId().value(),
                user.getNome(),
                perfisEntities
        );
        
        // Check if entity already exists to determine if it's new or an update
        boolean isNew = !jdbcUserRepository.existsById(user.getId().value());
        entity.setNew(isNew);
        
        return entity;
    }

    private User toDomain(UserJdbcEntity entity) {
        entity.setNew(false);
        var profileIds = entity.getPerfis().stream()
                .map(up -> ProfileId.of(up.getProfileId()))
                .toList();

        return User.reconstitute(
                UserId.of(entity.getId()),
                entity.getNome(),
                profileIds
        );
    }
}
