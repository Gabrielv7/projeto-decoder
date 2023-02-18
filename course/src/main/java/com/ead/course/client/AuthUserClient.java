package com.ead.course.client;

import com.ead.course.domain.dto.request.UserCourseRequest;
import com.ead.course.domain.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "AuthUserClient", url = "${ms.authuser.url}")
public interface AuthUserClient {

    @GetMapping
    Page<UserResponse> getAllUsers(@RequestParam UUID courseId, Pageable pageable);

    @GetMapping("/{userId}")
    UserResponse getOneUser(@PathVariable(value = "userId") UUID userId);

    @PostMapping("/{userId}/courses/subscription")
    void saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                      @RequestBody UserCourseRequest userCourseRequest);

}
