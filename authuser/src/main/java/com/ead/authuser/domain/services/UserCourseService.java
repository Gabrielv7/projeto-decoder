package com.ead.authuser.domain.services;

import com.ead.authuser.domain.model.UserCourseModel;
import com.ead.authuser.domain.model.UserModel;

import java.util.UUID;

public interface UserCourseService {

    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

    UserCourseModel save(UserCourseModel userCourseModel);
}
