package com.ead.course.service.impl;

import com.ead.course.domain.Course;
import com.ead.course.domain.CourseUser;
import com.ead.course.domain.assembler.CourseUserAssembler;
import com.ead.course.domain.assembler.UserCourseAssembler;
import com.ead.course.domain.dto.request.UserCourseRequest;
import com.ead.course.domain.dto.response.UserResponse;
import com.ead.course.domain.enums.UserStatus;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.AuthUserClientService;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
import com.ead.course.util.ConstantsLog;
import com.ead.course.validator.CourseUserValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseUserValidator validator;

    @Autowired
    private CourseUserAssembler courseUserAssembler;

    @Autowired
    private AuthUserClientService authUserClientService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserCourseAssembler userCourseAssembler;

    @Transactional
    @Override
    public CourseUser saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        Course course = courseService.findById(courseId);
        UserResponse user = authUserClientService.getOneUser(userId);
        this.verifyUserIsBlocked(user);
        validator.validSubscriptionUserInCourse(course, user.getUserId());
        CourseUser courseUser = courseUserAssembler.assemblerCourseUserBeforeSave(course, user.getUserId());
        courseUserRepository.save(courseUser);
        UserCourseRequest userCourseRequest = userCourseAssembler.assemblerUserCourseToRequestMsAuthUser(courseUser.getCourse().getCourseId());
        authUserClientService.saveSubscriptionUserInCourse(courseUser.getUserId(), userCourseRequest);
        return courseUser;
    }

    public void verifyUserIsBlocked(UserResponse userResponse) {
        if (UserStatus.BLOCKED.equals(userResponse.getUserStatus())) {

            String message = messageSource.getMessage("user-blocked", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "verifyUserIsBlocked", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);

        }
    }

}