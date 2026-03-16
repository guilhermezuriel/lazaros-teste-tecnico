package com.guilhermezuriel.lazurosbackend.user.application.dto;

import java.util.List;
import java.util.UUID;

public record UpdateUserCommand(String nome, List<UUID> perfisIds) {
}
