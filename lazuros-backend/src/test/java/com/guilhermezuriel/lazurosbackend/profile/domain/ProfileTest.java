package com.guilhermezuriel.lazurosbackend.profile.domain;

import com.guilhermezuriel.lazurosbackend.profile.domain.exception.InvalidProfileException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {

    @Test
    void shouldCreateProfileWithValidDescricao() {
        var profile = Profile.create("Administrador");

        assertThat(profile.getId()).isNotNull();
        assertThat(profile.getDescricao()).isEqualTo("Administrador");
    }

    @Test
    void shouldThrowWhenDescricaoIsNull() {
        assertThatThrownBy(() -> Profile.create((String) null))
                .isInstanceOf(InvalidProfileException.class)
                .hasMessage("Descrição é obrigatória");
    }

    @Test
    void shouldThrowWhenDescricaoIsBlank() {
        assertThatThrownBy(() -> Profile.create("   "))
                .isInstanceOf(InvalidProfileException.class)
                .hasMessage("Descrição é obrigatória");
    }

    @Test
    void shouldThrowWhenDescricaoIsTooShort() {
        assertThatThrownBy(() -> Profile.create("Adm"))
                .isInstanceOf(InvalidProfileException.class)
                .hasMessage("Descrição deve ter no mínimo 5 caracteres");
    }

    @Test
    void shouldThrowWhenDescricaoHasExactly4Chars() {
        assertThatThrownBy(() -> Profile.create("Test"))
                .isInstanceOf(InvalidProfileException.class)
                .hasMessage("Descrição deve ter no mínimo 5 caracteres");
    }

    @Test
    void shouldAcceptDescricaoWithExactly5Chars() {
        var profile = Profile.create("Admin");

        assertThat(profile.getDescricao()).isEqualTo("Admin");
    }

    @Test
    void shouldUpdateDescricaoWithValidValue() {
        var profile = Profile.create("Administrador");
        profile.updateDescricao("Usuário Comum");

        assertThat(profile.getDescricao()).isEqualTo("Usuário Comum");
    }

    @Test
    void shouldThrowWhenUpdatingWithInvalidDescricao() {
        var profile = Profile.create("Administrador");

        assertThatThrownBy(() -> profile.updateDescricao("Adm"))
                .isInstanceOf(InvalidProfileException.class)
                .hasMessage("Descrição deve ter no mínimo 5 caracteres");
    }

    @Test
    void shouldGenerateUniqueIds() {
        var profile1 = Profile.create("Administrador");
        var profile2 = Profile.create("Usuário Comum");

        assertThat(profile1.getId()).isNotEqualTo(profile2.getId());
    }
}
