package com.guilhermezuriel.lazurosbackend.user.domain;

import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import com.guilhermezuriel.lazurosbackend.user.domain.exception.InvalidUserException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private static final List<ProfileId> ONE_PROFILE = List.of(ProfileId.of(UUID.randomUUID()));

    @Test
    void shouldCreateUserWithValidData() {
        var user = User.create("João da Silva Santos", ONE_PROFILE);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getNome()).isEqualTo("João da Silva Santos");
        assertThat(user.getPerfis()).hasSize(1);
    }

    @Test
    void shouldThrowWhenNomeIsNull() {
        assertThatThrownBy(() -> User.create(null, ONE_PROFILE))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Nome é obrigatório");
    }

    @Test
    void shouldThrowWhenNomeIsBlank() {
        assertThatThrownBy(() -> User.create("   ", ONE_PROFILE))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Nome é obrigatório");
    }

    @Test
    void shouldThrowWhenNomeIsTooShort() {
        assertThatThrownBy(() -> User.create("João", ONE_PROFILE))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Nome deve ter no mínimo 10 caracteres");
    }

    @Test
    void shouldThrowWhenNomeHasExactly9Chars() {
        assertThatThrownBy(() -> User.create("João Silv", ONE_PROFILE))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Nome deve ter no mínimo 10 caracteres");
    }

    @Test
    void shouldAcceptNomeWithExactly10Chars() {
        var user = User.create("João Silva", ONE_PROFILE);

        assertThat(user.getNome()).isEqualTo("João Silva");
    }

    @Test
    void shouldThrowWhenPerfisIsNull() {
        assertThatThrownBy(() -> User.create("João da Silva Santos", null))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Usuário deve ter ao menos 1 perfil");
    }

    @Test
    void shouldThrowWhenPerfisIsEmpty() {
        assertThatThrownBy(() -> User.create("João da Silva Santos", List.of()))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Usuário deve ter ao menos 1 perfil");
    }

    @Test
    void shouldAcceptMultiplePerfis() {
        var perfis = List.of(ProfileId.of(UUID.randomUUID()), ProfileId.of(UUID.randomUUID()));
        var user = User.create("João da Silva Santos", perfis);

        assertThat(user.getPerfis()).hasSize(2);
    }

    @Test
    void shouldReturnImmutablePerfis() {
        var user = User.create("João da Silva Santos", ONE_PROFILE);

        assertThatThrownBy(() -> user.getPerfis().add(ProfileId.of(UUID.randomUUID())))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldUpdateNomeWithValidValue() {
        var user = User.create("João da Silva Santos", ONE_PROFILE);
        user.updateNome("Maria da Silva Santos");

        assertThat(user.getNome()).isEqualTo("Maria da Silva Santos");
    }

    @Test
    void shouldThrowWhenUpdatingWithInvalidNome() {
        var user = User.create("João da Silva Santos", ONE_PROFILE);

        assertThatThrownBy(() -> user.updateNome("João"))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Nome deve ter no mínimo 10 caracteres");
    }

    @Test
    void shouldUpdatePerfisWithValidList() {
        var user = User.create("João da Silva Santos", ONE_PROFILE);
        var newPerfis = List.of(ProfileId.of(UUID.randomUUID()), ProfileId.of(UUID.randomUUID()));
        user.updatePerfis(newPerfis);

        assertThat(user.getPerfis()).hasSize(2);
    }

    @Test
    void shouldThrowWhenUpdatingPerfisWithEmptyList() {
        var user = User.create("João da Silva Santos", ONE_PROFILE);

        assertThatThrownBy(() -> user.updatePerfis(List.of()))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Usuário deve ter ao menos 1 perfil");
    }
}
