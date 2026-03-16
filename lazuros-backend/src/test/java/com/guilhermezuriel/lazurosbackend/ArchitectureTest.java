package com.guilhermezuriel.lazurosbackend;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ArchitectureTest {

    private static final ApplicationModules modules =
            ApplicationModules.of(LazurosBackendApplication.class);

    @Test
    void shouldHaveNoModuleViolations() {
        modules.verify();
    }

    @Test
    void shouldGenerateModuleDocumentation() {
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }
}
