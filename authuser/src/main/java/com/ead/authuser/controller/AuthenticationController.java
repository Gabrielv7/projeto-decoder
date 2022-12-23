package com.ead.authuser.controller;

import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.mapper.UserMapper;
import com.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService service;
    @Autowired
    private UserMapper mapper;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserRequest.UserView.RegistrationPost.class)
                                               @JsonView(UserRequest.UserView.RegistrationPost.class) UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(mapper.toEntity(userRequest)));
    }

}
