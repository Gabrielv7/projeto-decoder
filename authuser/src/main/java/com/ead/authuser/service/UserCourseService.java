package com.ead.authuser.service;

import com.ead.authuser.domain.UserCourse;

import java.util.UUID;

public interface UserCourseService {

    UserCourse saveSubscriptionUserInCourse(UUID userId, UUID courseId);

}
