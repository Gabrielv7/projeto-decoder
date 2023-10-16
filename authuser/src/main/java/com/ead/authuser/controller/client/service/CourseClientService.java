package com.ead.authuser.controller.client.service;

import com.ead.authuser.controller.client.CourseClient;
import com.ead.authuser.controller.client.dto.CourseDTO;
import com.ead.authuser.service.UserService;
import com.ead.authuser.util.ConstantsLog;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class CourseClientService {

    private final CourseClient courseClient;
    private final  UserService userService;

    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<CourseDTO> getAllCoursesByUserId(UUID userId, Pageable pageable, String token) {

        userService.findById(userId);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_USER_ID,
                "getAllCoursesByUserId", "request ms-course", userId);

        return courseClient.getAllCourses(userId, pageable, token);
    }

}
