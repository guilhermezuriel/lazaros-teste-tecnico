package com.guilhermezuriel.lazurosbackend.user.infrastructure.web;

import java.util.List;
import java.util.UUID;

record UserRequest(String nome, List<UUID> perfis) {
}
