package com.ead.authuser.service.impl;

import com.ead.authuser.client.CourseClient;
import com.ead.authuser.domain.dto.response.CourseResponse;
import com.ead.authuser.service.CourseClientService;
import com.ead.authuser.service.UserService;
import com.ead.authuser.util.ConstantsLog;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Log4j2
@Service
public class CourseClientServiceImpl implements CourseClientService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserService userService;

    @Override
    public Page<CourseResponse> getAllCoursesByUserId(UUID userId, Pageable pageable) {
        Page<CourseResponse> result = null;
        try {

            userService.findById(userId);

            log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_USER_ID,
                    "getAllCoursesByUserId", "request ms-course", userId);

            result = courseClient.getAllCourses(userId, pageable);
            
        }catch (FeignException ex){
            log.error("FeignException = {}", ex.getMessage());
        }
        return result == null ? new PageImpl<>(new ArrayList<>()) : result;
    }

}
