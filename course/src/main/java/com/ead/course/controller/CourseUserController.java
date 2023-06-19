package com.ead.course.controller;

import com.ead.course.domain.dto.request.SubscriptionRequest;
import com.ead.course.domain.dto.response.UserResponse;
import com.ead.course.mapper.UserMapper;
import com.ead.course.service.CourseService;
import com.ead.course.service.UserService;
import com.ead.course.specification.SpecificationTemplate;
import com.ead.course.util.Constants;
import com.ead.course.util.ConstantsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses/{courseId}/users")
public class CourseUserController {

    private final UserService userService;

    private final UserMapper mapper;

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsersByCourse(SpecificationTemplate.UserSpec spec,
                                                                  @PathVariable(value = "courseId") UUID courseId,
                                                                  @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.DESC) Pageable pageable){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID,
                "getAllUsersByCourse", "GET", "Searching a list of users by course", courseId);

        Page<UserResponse> usersResponse = userService.findAllUsersByCourseId(SpecificationTemplate.findUsersByCourseId(courseId).and(spec), pageable)
                .map(u -> mapper.toResponse(u));

        return ResponseEntity.ok(usersResponse);
    }

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionRequest subscriptionRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID + ConstantsLog.LOG_ENTITY,
                "saveSubscriptionUserInCourse", "POST", "Saving subscription user in course", courseId, subscriptionRequest);

        courseService.saveSubscriptionUserInCourse(courseId, subscriptionRequest.getUserId());

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE,
                "saveSubscriptionUserInCourse", ConstantsLog.LOG_EVENT_INFO, "Saved subscription user in course", ConstantsLog.LOG_HTTP_CODE_CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(Constants.MSG_SUCCESSFUL_SUBSCRIPTION);
    }

}
