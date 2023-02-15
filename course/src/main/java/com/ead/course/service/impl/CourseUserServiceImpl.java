package com.ead.course.service.impl;

import com.ead.course.domain.Course;
import com.ead.course.domain.CourseUser;
import com.ead.course.domain.assembler.CourseUserAssembler;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
import com.ead.course.validator.CourseUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseUserValidator validator;

    @Autowired
    private CourseUserAssembler assembler;

    @Transactional
    @Override
    public Object saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        Course course = courseService.findById(courseId);
        validator.validSubscriptionUserInCourse(course, userId);
        CourseUser courseUser = assembler.assemblerCourseUserBeforeSave(course, userId);
        courseUserRepository.save(courseUser);
        return "Subscription created sucessfully";
    }
}
