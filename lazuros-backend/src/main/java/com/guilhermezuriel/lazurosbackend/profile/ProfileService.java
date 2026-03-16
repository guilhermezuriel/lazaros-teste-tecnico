package com.guilhermezuriel.lazurosbackend.profile;

import com.guilhermezuriel.lazurosbackend.profile.application.dto.CreateProfileCommand;
import com.guilhermezuriel.lazurosbackend.profile.ProfileResult;
import com.guilhermezuriel.lazurosbackend.profile.application.dto.UpdateProfileCommand;
import com.guilhermezuriel.lazurosbackend.profile.application.usecase.*;
import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final CreateProfileUseCase createProfileUseCase;
    private final GetProfileUseCase getProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final DeleteProfileUseCase deleteProfileUseCase;
    private final ListProfilesUseCase listProfilesUseCase;

    public ProfileService(
            CreateProfileUseCase createProfileUseCase,
            GetProfileUseCase getProfileUseCase,
            UpdateProfileUseCase updateProfileUseCase,
            DeleteProfileUseCase deleteProfileUseCase,
            ListProfilesUseCase listProfilesUseCase) {
        this.createProfileUseCase = createProfileUseCase;
        this.getProfileUseCase = getProfileUseCase;
        this.updateProfileUseCase = updateProfileUseCase;
        this.deleteProfileUseCase = deleteProfileUseCase;
        this.listProfilesUseCase = listProfilesUseCase;
    }

    public ProfileResult create(CreateProfileCommand command) {
        return createProfileUseCase.execute(command);
    }

    public List<ProfileResult> create(List<CreateProfileCommand> commands){
        return createProfileUseCase.execute(commands);
    }

    public ProfileResult getById(ProfileId id) {
        return getProfileUseCase.execute(id);
    }

    public ProfileResult update(ProfileId id, UpdateProfileCommand command) {
        return updateProfileUseCase.execute(id, command);
    }

    public void delete(ProfileId id) {
        deleteProfileUseCase.execute(id);
    }

    public List<ProfileResult> listAll() {
        return listProfilesUseCase.execute();
    }

    public boolean existsAllById(List<ProfileId> ids) {
        return ids.stream().allMatch(id -> getProfileUseCase.execute(id) != null);
    }

    public List<ProfileResult> findAllById(List<ProfileId> ids) {
        return ids.stream().map(getProfileUseCase::execute).toList();
    }
}
