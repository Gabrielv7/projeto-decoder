package com.ead.course.service;

import java.util.UUID;

public interface CourseUserService {

    Object saveSubscriptionUserInCourse(UUID courseId, UUID userId);

}
