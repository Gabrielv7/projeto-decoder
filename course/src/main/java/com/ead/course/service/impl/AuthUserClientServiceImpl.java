package com.ead.course.service.impl;

import com.ead.course.client.AuthUserClient;
import com.ead.course.domain.dto.request.UserCourseRequest;
import com.ead.course.domain.dto.response.UserResponse;
import com.ead.course.exception.NotFoundException;
import com.ead.course.service.AuthUserClientService;
import com.ead.course.util.ConstantsLog;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
@Log4j2
@Service
public class AuthUserClientServiceImpl implements AuthUserClientService {

    @Autowired
    private AuthUserClient authUserClient;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Page<UserResponse> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        Page<UserResponse> result = null;
        try {
            log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_COURSE_ID,
                    "getAllUsersByCourse", "request ms-authuser", courseId);

            result = authUserClient.getAllUsers(courseId, pageable);

        }catch (FeignException ex){
            log.error("FeignException = {}", ex.getMessage());
        }

        return result == null ? new PageImpl<>(new ArrayList<>()) : result;
    }

    @Override
    public UserResponse getOneUser(UUID userId) {
        UserResponse userResponse = null;
        try {
            log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_USER_ID,
                    "getOneUser", "request ms-authuser", userId);

            userResponse = authUserClient.getOneUser(userId);

        } catch (FeignException ex) {
            if(HttpStatus.NOT_FOUND.value() == ex.status()) {
                log.error("FeignException = {}", ex.getMessage());
                throw new NotFoundException(messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale()));
            }
        }
        return userResponse;
    }

    @Override
    public void saveSubscriptionUserInCourse(UUID userId, UserCourseRequest userCourseRequest) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_USER_ID + ConstantsLog.LOG_ENTITY,
                "saveSubscriptionUserInCourse", "request ms-authuser", userId, userCourseRequest);

        authUserClient.saveSubscriptionUserInCourse(userId, userCourseRequest);
    }

}
