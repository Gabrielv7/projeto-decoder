package com.ead.course.controller;

import com.ead.course.domain.dto.response.UserResponse;
import com.ead.course.service.AuthUserClientService;
import com.ead.course.util.ConstantsLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    private AuthUserClientService authUserClientService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserResponse>> getAllUsersByCourse(@PathVariable(value = "courseId") UUID courseId,
                                                                  @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID,
                "getAllUsersByCourse", "GET", "Searching a list of users by course", courseId);

        Page<UserResponse> usersResponse = authUserClientService.getAllUsersByCourse(courseId, pageable);
        return ResponseEntity.ok(usersResponse);
    }

}
