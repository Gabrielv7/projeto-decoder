package com.ead.authuser.domain.services;

import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.model.forms.UserUpdateForm;
import com.ead.authuser.domain.model.forms.UserUpdateImageForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();

    UserModel findById(UUID userId);

    void delete(UUID userId);

    UserModel save(UserModel userModel);

    UserModel updateUser(UserUpdateForm userUpdateForm, UUID userId);

    UserModel updateImageUser(UserUpdateImageForm userUpdateImageForm, UUID userId);

    UserModel updatePassword(UUID userId, String oldPassword, String password);

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    UserModel saveSubscriptionInstructor(UserModel userModel);
}
