package com.ead.authuser.service;

import com.ead.authuser.domain.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseClientService {

    Page<CourseResponse> getAllCoursesByUserId(UUID userId, Pageable pageable);

    void deleteCourseUserByUser(UUID userId);

}
