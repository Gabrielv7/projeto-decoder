package com.ead.course.service;

import com.ead.course.domain.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuthUserClientService {

    Page<UserResponse> getAllUsersByCourse(UUID courseId, Pageable pageable);

}
