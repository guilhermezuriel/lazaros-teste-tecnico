package com.guilhermezuriel.lazurosbackend.profile.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.profile.domain.ProfileRepository;
import com.guilhermezuriel.lazurosbackend.profile.ProfileNotFoundException;

public class DeleteProfileUseCase {

    private final ProfileRepository profileRepository;

    public DeleteProfileUseCase(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void execute(ProfileId id) {
        if (!profileRepository.existsById(id)) {
            throw new ProfileNotFoundException(id);
        }
        profileRepository.delete(id);
    }
}
