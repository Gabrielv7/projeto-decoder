package com.ead.authuser.service.impl;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.UserCourse;
import com.ead.authuser.domain.assembler.UserCourseAssembler;
import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.service.UserCourseService;
import com.ead.authuser.service.UserService;
import com.ead.authuser.validator.UserCourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCourseValidator validator;

    @Autowired
    private UserCourseAssembler assembler;

    @Override
    public UserCourse saveSubscriptionUserInCourse(UUID userId, UUID courseId) {
        User user = userService.findById(userId);
        validator.validSubscriptionUserInCourse(user, courseId);
        UserCourse userCourse = assembler.assemblerUserCourseBeforeSave(user, courseId);
        return userCourseRepository.save(userCourse);
    }
}
