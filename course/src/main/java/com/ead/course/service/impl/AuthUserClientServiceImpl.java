package com.ead.course.service.impl;

import com.ead.course.client.AuthUserClient;
import com.ead.course.domain.dto.response.UserResponse;
import com.ead.course.service.AuthUserClientService;
import com.ead.course.util.ConstantsLog;
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
public class AuthUserClientServiceImpl implements AuthUserClientService {

    @Autowired
    private AuthUserClient authUserClient;

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
}
