package com.guilhermezuriel.lazurosbackend.profile.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;
import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.profile.domain.ProfileRepository;
import com.guilhermezuriel.lazurosbackend.profile.ProfileNotFoundException;

public class GetProfileUseCase {

    private final ProfileRepository profileRepository;

    public GetProfileUseCase(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileResult execute(ProfileId id) {
        return profileRepository.findById(id)
                .map(ProfileResult::from)
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }
}
