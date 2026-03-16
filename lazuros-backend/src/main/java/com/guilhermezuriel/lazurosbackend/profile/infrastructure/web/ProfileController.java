package com.guilhermezuriel.lazurosbackend.profile.infrastructure.web;

import com.guilhermezuriel.lazurosbackend.profile.ProfileService;
import com.guilhermezuriel.lazurosbackend.profile.application.dto.CreateProfileCommand;
import com.guilhermezuriel.lazurosbackend.profile.application.dto.UpdateProfileCommand;
import com.guilhermezuriel.lazurosbackend.profile.ProfileId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
class ProfileController {

    private final ProfileService profileService;

    ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    ResponseEntity<ProfileResponse> create(@RequestBody ProfileRequest request) {
        var result = profileService.create(new CreateProfileCommand(request.descricao()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProfileResponse.from(result));
    }

    @GetMapping
    ResponseEntity<List<ProfileResponse>> listAll() {
        var results = profileService.listAll()
                .stream()
                .map(ProfileResponse::from)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProfileResponse> getById(@PathVariable UUID id) {
        var result = profileService.getById(ProfileId.of(id));
        return ResponseEntity.ok(ProfileResponse.from(result));
    }

    @PutMapping("/{id}")
    ResponseEntity<ProfileResponse> update(@PathVariable UUID id, @RequestBody ProfileRequest request) {
        var result = profileService.update(ProfileId.of(id), new UpdateProfileCommand(request.descricao()));
        return ResponseEntity.ok(ProfileResponse.from(result));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        profileService.delete(ProfileId.of(id));
        return ResponseEntity.noContent().build();
    }
}
