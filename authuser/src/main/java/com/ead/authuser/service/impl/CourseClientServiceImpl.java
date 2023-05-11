package com.ead.authuser.service.impl;

import com.ead.authuser.controller.client.CourseClient;
import com.ead.authuser.domain.dto.response.CourseResponse;
import com.ead.authuser.service.CourseClientService;
import com.ead.authuser.service.UserService;
import com.ead.authuser.util.ConstantsLog;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class CourseClientServiceImpl implements CourseClientService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserService userService;

    @CircuitBreaker(name = "circuitbreakerInstance")
    @Override
    public Page<CourseResponse> getAllCoursesByUserId(UUID userId, Pageable pageable) {

        userService.findById(userId);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_USER_ID,
                "getAllCoursesByUserId", "request ms-course", userId);

        return courseClient.getAllCourses(userId, pageable);
    }

}
