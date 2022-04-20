package com.ead.authuser.api.controllers;

import com.ead.authuser.domain.mapper.UserMapper;
import com.ead.authuser.domain.model.dtos.UserDto;
import com.ead.authuser.domain.model.forms.InstructorForm;
import com.ead.authuser.domain.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper mapper;

    @PostMapping("/subscription")
    @ResponseStatus(HttpStatus.OK)
    public UserDto saveSubscriptionInstructor(@RequestBody @Valid InstructorForm instructorForm){

        var user = userService.findById(instructorForm.getUserId());

        userService.saveSubscriptionInstructor(user);

        return mapper.toDto(user);
    }



}
