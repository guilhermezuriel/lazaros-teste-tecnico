package com.guilhermezuriel.lazurosbackend.profile.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;
import com.guilhermezuriel.lazurosbackend.profile.application.dto.UpdateProfileCommand;
import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.profile.domain.ProfileRepository;
import com.guilhermezuriel.lazurosbackend.profile.ProfileNotFoundException;

public class UpdateProfileUseCase {

    private final ProfileRepository profileRepository;

    public UpdateProfileUseCase(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileResult execute(ProfileId id, UpdateProfileCommand command) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        if (command.descricao() != null) {
            profile.updateDescricao(command.descricao());
        }

        var updated = profileRepository.save(profile);
        return ProfileResult.from(updated);
    }
}
