package com.ead.course.domain.services.impl;

import com.ead.course.domain.models.CourseModel;
import com.ead.course.domain.models.CourseUserModel;
import com.ead.course.domain.repositories.CourseUserRepository;
import com.ead.course.domain.services.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseUserRepository courseUserRepository;

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {

        return courseUserRepository.existsByCourseAndUserId(courseModel, userId);

    }

    @Override
    public CourseUserModel save(CourseUserModel courseUserModel) {
        return courseUserRepository.save(courseUserModel);
    }
}
