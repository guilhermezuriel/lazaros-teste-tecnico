package com.guilhermezuriel.lazurosbackend.profile.infrastructure.config;

import com.guilhermezuriel.lazurosbackend.profile.application.usecase.*;
import com.guilhermezuriel.lazurosbackend.profile.domain.ProfileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProfileBeanConfiguration {

    @Bean
    CreateProfileUseCase createProfileUseCase(ProfileRepository profileRepository) {
        return new CreateProfileUseCase(profileRepository);
    }

    @Bean
    GetProfileUseCase getProfileUseCase(ProfileRepository profileRepository) {
        return new GetProfileUseCase(profileRepository);
    }

    @Bean
    UpdateProfileUseCase updateProfileUseCase(ProfileRepository profileRepository) {
        return new UpdateProfileUseCase(profileRepository);
    }

    @Bean
    DeleteProfileUseCase deleteProfileUseCase(ProfileRepository profileRepository) {
        return new DeleteProfileUseCase(profileRepository);
    }

    @Bean
    ListProfilesUseCase listProfilesUseCase(ProfileRepository profileRepository) {
        return new ListProfilesUseCase(profileRepository);
    }
}
