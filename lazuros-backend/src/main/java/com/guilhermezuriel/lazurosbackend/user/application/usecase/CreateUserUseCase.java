package com.guilhermezuriel.lazurosbackend.user.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.profile.ProfileNotFoundException;
import com.guilhermezuriel.lazurosbackend.user.application.dto.CreateUserCommand;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UserResult;
import com.guilhermezuriel.lazurosbackend.user.domain.User;
import com.guilhermezuriel.lazurosbackend.user.domain.UserRepository;

import java.util.List;

public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final ProfileService profileService;

    public CreateUserUseCase(UserRepository userRepository, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
    }

    public UserResult execute(CreateUserCommand command) {
        var profileIds = command.perfisIds().stream()
                .map(ProfileId::of)
                .toList();

        var profileResults = profileService.findAllById(profileIds);
        if (profileResults.size() != profileIds.size()) {
            throw new ProfileNotFoundException(profileIds.stream()
                    .filter(id -> profileResults.stream().noneMatch(r -> r.id().equals(id.value())))
                    .findFirst()
                    .orElseThrow());
        }

        var user = User.create(command.nome(), profileIds);
        var saved = userRepository.save(user);

        return UserResult.from(saved, profileResults);
    }
}
