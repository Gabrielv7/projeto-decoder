package com.ead.course.domain.services;

import com.ead.course.domain.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    UserModel save(UserModel userModel);

    void delete(UUID userId);

    UserModel findById(UUID userInstructor);

    Optional<UserModel> findByIdOpt(UUID userInstructor);

    UserModel verifyUserIsBlocked(UserModel userModel);
}
