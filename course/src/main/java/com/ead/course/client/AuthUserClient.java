package com.ead.course.client;

import com.ead.course.domain.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "AuthUserClient", url = "${authuser.url}")
public interface AuthUserClient {

    @GetMapping
    Page<UserResponse> getAllUsers(@RequestParam UUID courseId, Pageable pageable);

}
