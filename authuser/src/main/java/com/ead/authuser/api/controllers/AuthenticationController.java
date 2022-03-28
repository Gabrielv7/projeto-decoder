package com.ead.authuser.api.controllers;

import com.ead.authuser.api.mapper.UserMapper;
import com.ead.authuser.domain.dtos.UserDto;
import com.ead.authuser.domain.forms.UserForm;
import com.ead.authuser.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


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

        var userModel = userMapper.toEntity(userForm);

        return userMapper.toDto(userService.save(userModel));

    }

}
