package com.ead.authuser.controller.client;

import com.ead.authuser.controller.client.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "ead-course", path = "/ead-course")
public interface CourseClient {

    @GetMapping("/courses")
    Page<CourseDTO> getAllCourses(@RequestParam UUID userId, Pageable pageable, @RequestHeader("Authorization") String token);

}
