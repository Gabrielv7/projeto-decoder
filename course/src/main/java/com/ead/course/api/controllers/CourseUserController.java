package com.ead.course.api.controllers;

import com.ead.course.domain.models.forms.SubscriptionForm;
import com.ead.course.domain.services.CourseService;
import com.ead.course.domain.services.CourseUserService;
import com.ead.course.infrastructure.clients.MsAuthUser;
import com.ead.course.infrastructure.models.dto.UserDto;
import com.ead.course.infrastructure.models.enums.UserStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    MsAuthUser msAuthUser;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseUserService courseUserService;

    @GetMapping("/courses/{courseId}/users")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAllUsersByCourse(@PathVariable(value = "courseId") UUID courseId,
                                             @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        log.info("GET getAllUsersByCourse received courseId {} ", courseId);

        return msAuthUser.getAllUsersByCourse(pageable, courseId);

    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionForm subscriptionForm) {


        // Busca o curso pelo ID
        var courseFind = courseService.findById(courseId);

        // Verifica se o usuário já está cadastrado no curso
        if(courseUserService.existsByCourseAndUserId(courseFind, subscriptionForm.getUserId())) {

            log.info("Subscription already exists!");

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Subscription already exists!");

        }

        try {
          // busca o usuário no microserviço de authuser
         var responseUser = msAuthUser.getOneUserById(subscriptionForm.getUserId());

            // verifica se o usuário está bloqueado
            if(responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)){

                log.info("User is blocked");

                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");


            }

        } catch (HttpStatusCodeException e) {

            // verifica se o usuário não existe
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){

                log.info("Response ms-authuser {} ", e.getStatusCode().value());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");

            }

        }

        var courseUserModel = courseUserService.saveAndSendSubscriptionUserInCourse(courseFind.convertToCourseUserModel(subscriptionForm.getUserId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserModel);

    }


}
