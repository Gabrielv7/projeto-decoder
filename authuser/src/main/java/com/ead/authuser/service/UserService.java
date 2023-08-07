package com.ead.authuser.service;

import com.ead.authuser.model.User;
import com.ead.authuser.dto.request.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {

    Page<User> findAllUsers(Pageable pageable, Specification<User> spec);

    User findById(UUID userId);

    void deleteById(UUID userId);

    User save(UserRequest userRequest);

    User update(UUID userId, User user);

    void updatePassword(UUID userId, UserRequest userRequest);

    User updateImage(UUID userId, User user);

}
