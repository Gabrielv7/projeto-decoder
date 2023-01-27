package com.ead.authuser.service.impl;

import com.ead.authuser.client.CourseClient;
import com.ead.authuser.domain.dto.response.CourseResponse;
import com.ead.authuser.service.CourseClientService;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;

@Log4j2
@Service
public class CourseClientServiceImpl implements CourseClientService {

    @Autowired
    private CourseClient courseClient;

    @Override
    public Page<CourseResponse> getAllCoursesByUserId(UUID userId, Pageable pageable) {
        try {
            log.info("chamada para o MS COURSE");
            return courseClient.getAllCourses(userId, pageable);
        }catch (FeignException ex){
            log.error("error fegin");
            return null;
        }catch (HttpStatusCodeException ex) {
            log.error("error status");
           return null;
        }
    }
}
