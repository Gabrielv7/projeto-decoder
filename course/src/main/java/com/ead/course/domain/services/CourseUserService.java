package com.ead.course.domain.services;

import com.ead.course.domain.models.CourseModel;
import com.ead.course.domain.models.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {

    void existsByCourseAndUserId(CourseModel courseModel, UUID userId);

    CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel);

    void existsByUserId(UUID userId);

    void deleteCourseUserByUser(UUID userId);
}
