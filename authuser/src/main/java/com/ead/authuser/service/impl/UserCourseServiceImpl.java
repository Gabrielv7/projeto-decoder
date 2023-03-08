package com.ead.authuser.service.impl;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.UserCourse;
import com.ead.authuser.domain.assembler.UserCourseAssembler;
import com.ead.authuser.exception.NotFoundException;
import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.service.UserCourseService;
import com.ead.authuser.service.UserService;
import com.ead.authuser.util.ConstantsLog;
import com.ead.authuser.validator.UserCourseValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
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

    @Autowired
    private MessageSource messageSource;

    @Transactional
    @Override
    public UserCourse saveSubscriptionUserInCourse(UUID userId, UUID courseId) {
        User user = userService.findById(userId);
        validator.validSubscriptionUserInCourse(user, courseId);
        UserCourse userCourse = assembler.assemblerUserCourseBeforeSave(user, courseId);
        return userCourseRepository.save(userCourse);
    }

    @Transactional
    @Override
    public void deleteUserCourseByCourseId(UUID courseId) {
        if(!userCourseRepository.existsByCourseId(courseId)){

            String messageError = messageSource.getMessage("user-course-not-found", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "deleteUserCourseByCourseId", "NotFoundException", ConstantsLog.LOG_HTTP_CODE_NOT_FOUND, messageError);

            throw new NotFoundException(messageError);
        }
        userCourseRepository.deleteAllByCourseId(courseId);
    }

}
