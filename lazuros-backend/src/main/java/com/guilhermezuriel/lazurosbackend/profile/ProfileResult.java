package com.guilhermezuriel.lazurosbackend.profile;

import com.guilhermezuriel.lazurosbackend.profile.domain.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ProfileResult(UUID id, String descricao) {

    public static ProfileResult from(Profile profile) {
        return new ProfileResult(
                profile.getId().value(),
                profile.getDescricao()
        );
    }

    public static List<ProfileResult> from(List<Profile> profiles){
        List<ProfileResult> profilesResult = new ArrayList<>();
        for(Profile profile: profiles){
            profilesResult.add(new ProfileResult(profile.getId().value(), profile.getDescricao()));
        }
        return profilesResult;
    }
}
