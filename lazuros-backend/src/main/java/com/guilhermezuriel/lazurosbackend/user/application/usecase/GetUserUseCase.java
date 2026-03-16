package com.guilhermezuriel.lazurosbackend.user.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UserResult;
import com.guilhermezuriel.lazurosbackend.user.domain.UserId;
import com.guilhermezuriel.lazurosbackend.user.domain.UserRepository;
import com.guilhermezuriel.lazurosbackend.user.domain.exception.UserNotFoundException;

public class GetUserUseCase {

    private final UserRepository userRepository;
    private final ProfileService profileService;

    public GetUserUseCase(UserRepository userRepository, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
    }

    public UserResult execute(UserId id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        var profileResults = profileService.findAllById(user.getPerfis());

        return UserResult.from(user, profileResults);
    }
}
