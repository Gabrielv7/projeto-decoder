package com.ead.course.service.impl;

import com.ead.course.domain.Course;
import com.ead.course.domain.CourseUser;
import com.ead.course.domain.assembler.CourseUserAssembler;
import com.ead.course.domain.dto.response.UserResponse;
import com.ead.course.domain.enums.UserStatus;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.AuthUserClientService;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
import com.ead.course.validator.CourseUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private AuthUserClientService authUserClientService;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    @Override
    public CourseUser saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        Course course = courseService.findById(courseId);
        UserResponse user = authUserClientService.getOneUser(userId);
        this.verifyUserIsBlocked(user);
        validator.validSubscriptionUserInCourse(course, user.getUserId());
        CourseUser courseUser = assembler.assemblerCourseUserBeforeSave(course, user.getUserId());
        return courseUserRepository.save(courseUser);
    }

    public void verifyUserIsBlocked(UserResponse userResponse) {
        if (UserStatus.BLOCKED.equals(userResponse.getUserStatus())) {
            throw new BusinessException(messageSource.getMessage("user-blocked", null, LocaleContextHolder.getLocale()));
        }
    }

}