package com.ead.course.service;

import com.ead.course.domain.CourseUser;

import java.util.UUID;

public interface CourseUserService {

    CourseUser saveSubscriptionUserInCourse(UUID courseId, UUID userId);

    void deleteCourseUserByUserId(UUID userId);
}
