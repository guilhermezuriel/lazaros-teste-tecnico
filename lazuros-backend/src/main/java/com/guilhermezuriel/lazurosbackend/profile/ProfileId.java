package com.guilhermezuriel.lazurosbackend.profile;

import java.util.UUID;

public record ProfileId(UUID value) {

    public static ProfileId generate() {
        return new ProfileId(UUID.randomUUID());
    }

    public static ProfileId of(UUID value) {
        return new ProfileId(value);
    }

    public static ProfileId of(String value) {
        return new ProfileId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
