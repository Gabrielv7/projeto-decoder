package com.ead.course.domain.services.impl;

import com.ead.course.domain.models.CourseModel;
import com.ead.course.domain.models.CourseUserModel;
import com.ead.course.domain.repositories.CourseUserRepository;
import com.ead.course.domain.services.CourseUserService;
import com.ead.course.infrastructure.clients.MsAuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseUserRepository courseUserRepository;

    @Autowired
    MsAuthUser msAuthUser;

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {

        return courseUserRepository.existsByCourseAndUserId(courseModel, userId);

    }

    @Transactional
    @Override
    public CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel) {

        var courseUserModelSave = courseUserRepository.save(courseUserModel);

        msAuthUser.postSubscriptionUserInCourse(courseUserModelSave.getCourse().getCourseId(), courseUserModelSave.getUserId());

        return courseUserModelSave;
    }
}
