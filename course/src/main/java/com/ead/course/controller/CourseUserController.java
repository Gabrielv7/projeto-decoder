package com.ead.course.controller;

import com.ead.course.domain.CourseUser;
import com.ead.course.domain.dto.request.SubscriptionRequest;
import com.ead.course.domain.dto.response.CourseUserResponse;
import com.ead.course.domain.dto.response.UserResponse;
import com.ead.course.mapper.CourseUserMapper;
import com.ead.course.service.AuthUserClientService;
import com.ead.course.service.CourseUserService;
import com.ead.course.util.ConstantsLog;
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
public class CourseUserController {

    @Autowired
    private AuthUserClientService authUserClientService;

    @Autowired
    private CourseUserService courseUserService;

    @Autowired
    private CourseUserMapper mapper;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserResponse>> getAllUsersByCourse(@PathVariable(value = "courseId") UUID courseId,
                                                                  @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID,
                "getAllUsersByCourse", "GET", "Searching a list of users by course", courseId);

        Page<UserResponse> usersResponse = authUserClientService.getAllUsersByCourse(courseId, pageable);
        return ResponseEntity.ok(usersResponse);
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<CourseUserResponse> createSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                                             @RequestBody @Valid SubscriptionRequest subscriptionRequest){

        CourseUser courseUserSaved = courseUserService.saveSubscriptionUserInCourse(courseId, subscriptionRequest.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(courseUserSaved));
    }

}