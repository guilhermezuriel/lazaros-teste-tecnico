package com.guilhermezuriel.lazurosbackend.profile.infrastructure.persistence;

import com.guilhermezuriel.lazurosbackend.profile.domain.Profile;
import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.profile.domain.ProfileRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class ProfileRepositoryAdapter implements ProfileRepository {

    private final JdbcProfileRepository jdbcProfileRepository;

    ProfileRepositoryAdapter(JdbcProfileRepository jdbcProfileRepository) {
        this.jdbcProfileRepository = jdbcProfileRepository;
    }

    @Override
    public Profile save(Profile profile) {
        var entity = toEntity(profile);
        var saved = jdbcProfileRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Profile> saveAll(List<Profile> profile) {
        var entities =  profile.stream().map(this::toEntity).toList();
        var saved = jdbcProfileRepository.saveAll(entities);
        return ((List<ProfileJdbcEntity>) saved)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Profile> findById(ProfileId id) {
        return jdbcProfileRepository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public List<Profile> findAll() {
        return ((List<ProfileJdbcEntity>) jdbcProfileRepository.findAll())
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Profile> findAllById(List<ProfileId> ids) {
        var uuids = ids.stream().map(ProfileId::value).toList();
        return ((List<ProfileJdbcEntity>) jdbcProfileRepository.findAllById(uuids))
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void delete(ProfileId id) {
        jdbcProfileRepository.deleteById(id.value());
    }

    @Override
    public boolean existsById(ProfileId id) {
        return jdbcProfileRepository.existsById(id.value());
    }

    @Override
    public boolean existsAllById(List<ProfileId> ids) {
        var uuids = ids.stream().map(ProfileId::value).toList();
        long count = jdbcProfileRepository.findAllById(uuids).spliterator().estimateSize();
        return count == ids.size();
    }

    private ProfileJdbcEntity toEntity(Profile profile) {
        var entity = new ProfileJdbcEntity(
                profile.getId().value(),
                profile.getDescricao()
        );

        boolean isNew = !jdbcProfileRepository.existsById(profile.getId().value());
        entity.setNew(isNew);
        
        return entity;
    }

    private Profile toDomain(ProfileJdbcEntity entity) {
        entity.setNew(false);
        return Profile.reconstitute(
                ProfileId.of(entity.getId()),
                entity.getDescricao()
        );
    }
}
