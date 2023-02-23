package com.ead.authuser.controller;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.dto.request.InstructorRequest;
import com.ead.authuser.domain.dto.response.UserResponse;
import com.ead.authuser.mapper.UserMapper;
import com.ead.authuser.service.InstructorService;
import com.ead.authuser.util.ConstantsLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private UserMapper mapper;

    @PostMapping("/subscription")
    public ResponseEntity<UserResponse> saveSubscriptionInstructor(@RequestBody @Valid InstructorRequest instructorRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY,
                "saveSubscriptionInstructor", "POST", "Saving user type instructor", instructorRequest);

        User userInstructor = instructorService.saveUserTypeInstructor(instructorRequest.getUserId());

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_ENTITY_ID,
                "saveSubscriptionInstructor", ConstantsLog.LOG_EVENT_INFO, "User type instructor saved", ConstantsLog.LOG_HTTP_CODE_CREATED, userInstructor.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(userInstructor));
    }

}
