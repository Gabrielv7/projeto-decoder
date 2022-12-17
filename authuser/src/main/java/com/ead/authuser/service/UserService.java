package com.ead.authuser.service;

import com.ead.authuser.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> findAllUsers();

    User findById(UUID userId);

    void deleteById(UUID userId);

    User save(User user);
}
