package com.guilhermezuriel.lazurosbackend.user.application.usecase;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.user.application.dto.UserResult;
import com.guilhermezuriel.lazurosbackend.user.domain.UserRepository;

import java.util.List;

public class ListUsersUseCase {

    private final UserRepository userRepository;
    private final ProfileService profileService;

    public ListUsersUseCase(UserRepository userRepository, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
    }

    public List<UserResult> execute() {
        return userRepository.findAll().stream()
                .map(user -> {
                    var profileResults = profileService.findAllById(user.getPerfis());
                    return UserResult.from(user, profileResults);
                })
                .toList();
    }
}
