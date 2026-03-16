package com.guilhermezuriel.lazurosbackend.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UserId id);

    List<User> findAll();

    void delete(UserId id);

    boolean existsById(UserId id);
}
