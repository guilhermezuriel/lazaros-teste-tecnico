package com.guilhermezuriel.lazurosbackend.profile.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;
import com.guilhermezuriel.lazurosbackend.profile.domain.ProfileRepository;

import java.util.List;

public class ListProfilesUseCase {

    private final ProfileRepository profileRepository;

    public ListProfilesUseCase(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<ProfileResult> execute() {
        return profileRepository.findAll()
                .stream()
                .map(ProfileResult::from)
                .toList();
    }
}
