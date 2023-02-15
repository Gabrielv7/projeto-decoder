package com.ead.authuser.client;

import com.ead.authuser.domain.dto.response.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "CourseClient", url = "${ms.course.url}")
public interface CourseClient {

    @GetMapping
    Page<CourseResponse> getAllCourses(@RequestParam UUID userId, Pageable pageable);

}
