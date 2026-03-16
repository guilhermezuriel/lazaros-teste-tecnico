package com.guilhermezuriel.lazurosbackend.profile.infrastructure.seed;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.profile.application.dto.CreateProfileCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Order(1)
class ProfileDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ProfileDataInitializer.class);

    private final ProfileService profileService;

    ProfileDataInitializer(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!profileService.listAll().isEmpty()) {
            log.info("Profiles already seeded, skipping.");
            return;
        }

        log.info("Seeding profiles...");

        var profiles = List.of(
                new CreateProfileCommand("Administrador"),
                new CreateProfileCommand("Usuário Padrão"),
                new CreateProfileCommand("Gerente de Projetos"),
                new CreateProfileCommand("Analista de Sistemas"),
                new CreateProfileCommand("Suporte Técnico")
        );

        profileService.create(profiles);

        log.info("Profiles seeded successfully: {} records.", profiles.size());
    }
}
