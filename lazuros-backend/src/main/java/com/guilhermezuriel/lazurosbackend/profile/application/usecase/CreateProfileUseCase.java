package com.guilhermezuriel.lazurosbackend.profile.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.application.dto.CreateProfileCommand;
import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;
import com.guilhermezuriel.lazurosbackend.profile.domain.Profile;
import com.guilhermezuriel.lazurosbackend.profile.domain.ProfileRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateProfileUseCase {

    private final ProfileRepository profileRepository;

    public CreateProfileUseCase(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileResult execute(CreateProfileCommand command) {
        var profile = Profile.create(command.descricao());
        var saved = profileRepository.save(profile);
        return ProfileResult.from(saved);
    }

    public List<ProfileResult> execute(List<CreateProfileCommand> command){
        Set<String> descricoes = command.stream().map(CreateProfileCommand::descricao).collect(Collectors.toSet());
        var profiles = Profile.create(descricoes);
        var saved = profileRepository.saveAll(profiles);
        return ProfileResult.from(saved);
    }
}
