package com.ead.course.service;

import java.util.UUID;

public interface CourseUserService {

    void saveSubscriptionUserInCourse(UUID courseId, UUID userId);

}
