package com.ead.course.domain.services;

import com.ead.course.domain.models.CourseModel;
import com.ead.course.domain.models.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {

    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

    CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel);
}
