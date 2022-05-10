package com.ead.course.api.controllers;

import com.ead.course.domain.models.forms.SubscriptionForm;
import com.ead.course.domain.services.CourseService;
import com.ead.course.domain.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/courses/{courseId}/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllUsersByCourse(@PathVariable(value = "courseId") UUID courseId,
                                             @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        courseService.findById(courseId);

        log.info("GET getAllUsersByCourse received courseId {} ", courseId);

        return ResponseEntity.ok("");

    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionForm subscriptionForm) {

        log.info("POST saveSubscriptionUserInCourse received courseId {} and subscriptionForm {} ", courseId, subscriptionForm);

        // Busca o curso pelo ID
        var courseFind = courseService.findById(courseId);

        //verificações state transfer
        return ResponseEntity.status(HttpStatus.CREATED).body("");

    }

}
