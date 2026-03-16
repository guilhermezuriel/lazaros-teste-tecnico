package com.guilhermezuriel.lazurosbackend.profile.application;

import com.guilhermezuriel.lazurosbackend.profile.application.dto.CreateProfileCommand;
import com.guilhermezuriel.lazurosbackend.profile.application.usecase.CreateProfileUseCase;
import com.guilhermezuriel.lazurosbackend.profile.domain.Profile;
import com.guilhermezuriel.lazurosbackend.profile.domain.ProfileRepository;
import com.guilhermezuriel.lazurosbackend.profile.domain.exception.InvalidProfileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProfileUseCaseTest {

    @Mock
    private ProfileRepository profileRepository;

    private CreateProfileUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateProfileUseCase(profileRepository);
    }

    @Test
    void shouldCreateProfileSuccessfully() {
        var command = new CreateProfileCommand("Administrador");
        when(profileRepository.save(any(Profile.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = useCase.execute(command);

        assertThat(result).isNotNull();
        assertThat(result.descricao()).isEqualTo("Administrador");
        assertThat(result.id()).isNotNull();
    }

    @Test
    void shouldThrowWhenDescricaoIsInvalid() {
        var command = new CreateProfileCommand("Adm");

        assertThatThrownBy(() -> useCase.execute(command))
                .isInstanceOf(InvalidProfileException.class)
                .hasMessage("Descrição deve ter no mínimo 5 caracteres");
    }
}
