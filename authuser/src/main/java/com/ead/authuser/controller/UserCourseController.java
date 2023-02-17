package com.ead.authuser.controller;

import com.ead.authuser.domain.UserCourse;
import com.ead.authuser.domain.dto.request.UserCourseRequest;
import com.ead.authuser.domain.dto.response.CourseResponse;
import com.ead.authuser.domain.dto.response.UserCourseResponse;
import com.ead.authuser.mapper.UserCourseMapper;
import com.ead.authuser.service.CourseClientService;
import com.ead.authuser.service.UserCourseService;
import com.ead.authuser.util.ConstantsLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    private CourseClientService courseClientService;

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private UserCourseMapper mapper;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseResponse>> getAllCoursesByUser(@PathVariable(value = "userId") UUID userId,
                                                                    @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_USER_ID,
                "getAllCoursesByUser", "GET", "Searching a list of courses by user", userId);

        Page<CourseResponse> coursesResponse = courseClientService.getAllCoursesByUserId(userId, pageable);
        return ResponseEntity.ok(coursesResponse);
    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<UserCourseResponse> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                                           @RequestBody @Valid UserCourseRequest userCourseRequest){

        UserCourse userCourseSaved = userCourseService.saveSubscriptionUserInCourse(userId, userCourseRequest.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(userCourseSaved));
    }

}
