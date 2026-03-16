package com.guilhermezuriel.lazurosbackend.profile.domain;

import com.guilhermezuriel.lazurosbackend.profile.ProfileId;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

    Profile save(Profile profile);

    List<Profile> saveAll(List<Profile> profile);

    Optional<Profile> findById(ProfileId id);

    List<Profile> findAll();

    List<Profile> findAllById(List<ProfileId> ids);

    void delete(ProfileId id);

    boolean existsById(ProfileId id);

    boolean existsAllById(List<ProfileId> ids);
}
