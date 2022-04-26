package com.ead.authuser.api.controllers;

import com.ead.authuser.domain.model.UserCourseModel;
import com.ead.authuser.domain.model.dtos.UserCourseForm;
import com.ead.authuser.domain.services.UserCourseService;
import com.ead.authuser.domain.services.UserService;
import com.ead.authuser.infrastructure.clients.MsCourse;
import com.ead.authuser.infrastructure.domain.model.dtos.CourseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class UserCourseController {

    @Autowired
    MsCourse msCourse;

    @Autowired
    UserService userService;

    @Autowired
    UserCourseService userCourseService;

    @GetMapping("/users/{userId}/courses")
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseDto> getAllCoursesByUser(@PathVariable(value = "userId") UUID userId,
                                                               @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable) {

        log.info("GET getAllCoursesByUser received userId {} ", userId);

        return msCourse.getAllCoursesByUser(pageable, userId);

    }

    @PostMapping("/users/{userId}/courses/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCourseModel saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                        @RequestBody @Valid UserCourseForm userCourseForm) {

        log.info("POST saveSubscriptionUserInCourse received userId {} and {} ", userId, userCourseForm);

        // busca um usuário pelo Id
        var userFind = userService.findById(userId);

        // verifica se o usuário já está cadastrado no curso
        userCourseService.existsByUserAndCourseId(userFind, userCourseForm.getCourseId());

        return userCourseService.save(userFind.convertToUserCourseModel(userCourseForm.getCourseId()));

    }

    @DeleteMapping("/users/courses/{courseId}")
    public ResponseEntity<Object> deleteUserCourseByCourse(@PathVariable(value = "courseId") UUID courseId){

        log.info("DELETE deleteUserCourseByCourse received courseId {} ", courseId);

        // verifica se existe o cursoId
        userCourseService.existsByCourseId(courseId);

        // deleta o usuário pelo cursoId
        userCourseService.deleteUserCourseByCourse(courseId);

        return ResponseEntity.ok("UserCourse deleted sucessfully.");

    }

}
