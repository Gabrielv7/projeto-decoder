package com.ead.authuser.domain.services;

import com.ead.authuser.domain.model.UserCourseModel;
import com.ead.authuser.domain.model.UserModel;

import java.util.UUID;

public interface UserCourseService {

    void existsByUserAndCourseId(UserModel userModel, UUID courseId);

    UserCourseModel save(UserCourseModel userCourseModel);

    void existsByCourseId(UUID courseId);

    void deleteUserCourseByCourse(UUID courseId);
}
