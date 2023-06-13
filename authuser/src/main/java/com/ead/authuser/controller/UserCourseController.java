package com.ead.authuser.controller;

import com.ead.authuser.domain.dto.response.CourseResponse;
import com.ead.authuser.service.CourseClientService;
import com.ead.authuser.util.ConstantsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserCourseController {

    private final CourseClientService courseClientService;

    @GetMapping("/{userId}/courses")
    public ResponseEntity<Page<CourseResponse>> getAllCoursesByUser(@PathVariable(value = "userId") UUID userId,
                                                                    @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_USER_ID,
                "getAllCoursesByUser", "GET", "Searching a list of courses by user", userId);

        Page<CourseResponse> coursesResponse = courseClientService.getAllCoursesByUserId(userId, pageable);
        return ResponseEntity.ok(coursesResponse);
    }

}
