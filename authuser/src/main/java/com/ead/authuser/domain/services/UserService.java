package com.ead.authuser.domain.services;

import com.ead.authuser.domain.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {

    UserModel findById(UUID userId);

    void delete(UUID userId);

    UserModel save(UserModel userModel);

    UserModel update(UUID userId, UserModel userModel);

    UserModel updatePassword(UUID userId, String oldPassword, String password);

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    UserModel saveSubscriptionInstructor(UserModel userModel);

    UserModel saveUser(UserModel userModel);

    void deleteUser(UserModel userId);

    UserModel updateUser(UUID userId, UserModel userModel);


}
