package com.guilhermezuriel.lazurosbackend.user.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UpdateUserCommand;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UserResult;
import com.guilhermezuriel.lazurosbackend.user.domain.UserId;
import com.guilhermezuriel.lazurosbackend.user.domain.UserRepository;
import com.guilhermezuriel.lazurosbackend.user.domain.exception.UserNotFoundException;

public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final ProfileService profileService;

    public UpdateUserUseCase(UserRepository userRepository, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
    }

    public UserResult execute(UserId id, UpdateUserCommand command) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (command.nome() != null) {
            user.updateNome(command.nome());
        }

        if (command.perfisIds() != null) {
            var profileIds = command.perfisIds().stream()
                    .map(ProfileId::of)
                    .toList();
            user.updatePerfis(profileIds);
        }

        var updated = userRepository.save(user);
        var currentProfileIds = updated.getPerfis();
        var profileResults = profileService.findAllById(currentProfileIds);
        
        return UserResult.from(updated, profileResults);
    }
}
