package com.guilhermezuriel.lazurosbackend.user.application;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;
import com.guilhermezuriel.lazurosbackend.profile.ProfileNotFoundException;
import com.guilhermezuriel.lazurosbackend.user.application.dto.CreateUserCommand;
import com.guilhermezuriel.lazurosbackend.user.application.usecase.CreateUserUseCase;
import com.guilhermezuriel.lazurosbackend.user.domain.User;
import com.guilhermezuriel.lazurosbackend.user.domain.UserRepository;
import com.guilhermezuriel.lazurosbackend.user.domain.exception.InvalidUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileService profileService;

    private CreateUserUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateUserUseCase(userRepository, profileService);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        var profileId = UUID.randomUUID();
        var profileResult = new ProfileResult(profileId, "Administrador");
        var command = new CreateUserCommand("João da Silva Santos", List.of(profileId));

        when(profileService.findAllById(anyList())).thenReturn(List.of(profileResult));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = useCase.execute(command);

        assertThat(result).isNotNull();
        assertThat(result.nome()).isEqualTo("João da Silva Santos");
        assertThat(result.perfis()).hasSize(1);
        assertThat(result.perfis().get(0).descricao()).isEqualTo("Administrador");
    }

    @Test
    void shouldThrowWhenNomeIsInvalid() {
        var profileId = UUID.randomUUID();
        var profileResult = new ProfileResult(profileId, "Administrador");
        var command = new CreateUserCommand("João", List.of(profileId));

        when(profileService.findAllById(anyList())).thenReturn(List.of(profileResult));

        assertThatThrownBy(() -> useCase.execute(command))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Nome deve ter no mínimo 10 caracteres");
    }

    @Test
    void shouldThrowWhenPerfisIsEmpty() {
        var command = new CreateUserCommand("João da Silva Santos", List.of());

        when(profileService.findAllById(anyList())).thenReturn(List.of());

        assertThatThrownBy(() -> useCase.execute(command))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Usuário deve ter ao menos 1 perfil");
    }

    @Test
    void shouldThrowWhenProfileDoesNotExist() {
        var profileId = UUID.randomUUID();
        var command = new CreateUserCommand("João da Silva Santos", List.of(profileId));

        when(profileService.findAllById(anyList()))
                .thenThrow(new ProfileNotFoundException(
                        com.guilhermezuriel.lazurosbackend.profile.ProfileId.of(profileId)));

        assertThatThrownBy(() -> useCase.execute(command))
                .isInstanceOf(ProfileNotFoundException.class);
    }
}
