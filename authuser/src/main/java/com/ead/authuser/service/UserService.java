package com.ead.authuser.service;

import com.ead.authuser.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {

    Page<User> findAllUsers(Pageable pageable, Specification<User> spec);

    User findById(UUID userId);

    void deleteById(UUID userId);

    User save(User user);

    User update(UUID userId, User user);

    void updatePassword(UUID userId, String oldPassword, String newPassword);

    User updateImage(UUID userId, User user);
}
