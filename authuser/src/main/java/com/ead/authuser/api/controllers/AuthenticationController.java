package com.ead.authuser.api.controllers;

import com.ead.authuser.api.mapper.UserMapper;
import com.ead.authuser.domain.dtos.UserDto;
import com.ead.authuser.domain.forms.UserForm;
import com.ead.authuser.domain.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody @Validated UserForm userForm){

        log.debug("POST registerUser userForm received {}", userForm);

        var userModel = userMapper.toEntity(userForm);

        userService.save(userModel);

        log.debug("POST registerUser saved userId {}", userModel.getUserId());
        log.info("User saved sucessfully userId {}", userModel.getUserId());

        return userMapper.toDto(userModel);

    }


}
