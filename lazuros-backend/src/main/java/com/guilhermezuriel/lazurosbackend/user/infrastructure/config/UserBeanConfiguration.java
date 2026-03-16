package com.guilhermezuriel.lazurosbackend.user.infrastructure.config;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.user.application.usecase.*;
import com.guilhermezuriel.lazurosbackend.user.domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserBeanConfiguration {

    @Bean
    CreateUserUseCase createUserUseCase(UserRepository userRepository, ProfileService profileService) {
        return new CreateUserUseCase(userRepository, profileService);
    }

    @Bean
    GetUserUseCase getUserUseCase(UserRepository userRepository, ProfileService profileService) {
        return new GetUserUseCase(userRepository, profileService);
    }

    @Bean
    UpdateUserUseCase updateUserUseCase(UserRepository userRepository, ProfileService profileService) {
        return new UpdateUserUseCase(userRepository, profileService);
    }

    @Bean
    DeleteUserUseCase deleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }

    @Bean
    ListUsersUseCase listUsersUseCase(UserRepository userRepository, ProfileService profileService) {
        return new ListUsersUseCase(userRepository, profileService);
    }
}
