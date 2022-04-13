package com.ead.course.api.controllers;

import com.ead.course.infrastructure.clients.MsAuthUser;
import com.ead.course.infrastructure.models.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    MsAuthUser msAuthUser;

    @GetMapping("/courses/{courseId}/users")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAllUsersByCourse(@PathVariable(value = "courseId") UUID courseId,
                                             @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        log.info("GET getAllUsersByCourse received courseId {} ", courseId);

        return msAuthUser.getAllUsersByCourse(pageable, courseId);

    }


}
