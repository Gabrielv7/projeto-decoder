package com.ead.authuser.client;

import com.ead.authuser.domain.dto.response.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "ead-course", path = "/ead-course")
public interface CourseClient {

    @GetMapping("/courses")
    Page<CourseResponse> getAllCourses(@RequestParam UUID userId, Pageable pageable);

    @DeleteMapping("/courses/users/{userId}")
    void deleteCourseUserByUser(@PathVariable(value = "userId") UUID userId);

}
