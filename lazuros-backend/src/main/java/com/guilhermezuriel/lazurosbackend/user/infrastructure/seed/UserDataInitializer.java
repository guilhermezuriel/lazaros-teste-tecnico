package com.guilhermezuriel.lazurosbackend.user.infrastructure.seed;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.user.UserService;
import com.guilhermezuriel.lazurosbackend.user.application.dto.CreateUserCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@Order(2)
class UserDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(UserDataInitializer.class);

    private final UserService userService;
    private final ProfileService profileService;
    private final Random random = new Random();

    UserDataInitializer(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!userService.listAll().isEmpty()) {
            log.info("Users already seeded, skipping.");
            return;
        }

        var allProfiles = profileService.listAll();
        if (allProfiles.isEmpty()) {
            log.warn("No profiles found, cannot seed users.");
            return;
        }

        log.info("Seeding users...");

        var userNames = List.of(
                "Alice Ferreira da Silva",
                "Bruno Costa Mendes",
                "Carla Rodrigues Alves",
                "Daniel Santos Oliveira",
                "Eduardo Lima Pereira"
        );

        userNames.forEach(name -> {
            // Randomly assign 1 to 3 profiles to each user
            int count = 1 + random.nextInt(Math.min(3, allProfiles.size()));
            List<UUID> profileIds = allProfiles.stream()
                    .map(p -> p.id())
                    .sorted((a, b) -> random.nextInt(3) - 1)
                    .limit(count)
                    .toList();

            var result = userService.create(new CreateUserCommand(name, profileIds));
            log.info("Created user: [{}] {} with {} profile(s).", result.id(), result.nome(), result.perfis().size());
        });

        log.info("Users seeded successfully: {} records.", userNames.size());
    }
}
