package com.ead.authuser.controller;

import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.domain.dto.response.UserResponse;
import com.ead.authuser.mapper.UserMapper;
import com.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(mapper.toColletionResponse(service.findAllUsers()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable(value = "userId") UUID userId){
        return ResponseEntity.ok(mapper.toResponse(service.findById(userId)));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "userId") UUID userId){
        service.deleteById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(value = "userId") UUID userId,
                                                   @RequestBody @Validated(UserRequest.UserView.UserPut.class)
                                                   @JsonView(UserRequest.UserView.UserPut.class) UserRequest userRequest){
        return ResponseEntity.ok(mapper.toResponse(service.update(userId, mapper.toEntity(userRequest))));
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable(value = "userId") UUID userId,
                               @RequestBody @Validated(UserRequest.UserView.PasswordPut.class)
                               @JsonView(UserRequest.UserView.PasswordPut.class) UserRequest userRequest){
        service.updatePassword(userId, userRequest.getOldPassword(), userRequest.getPassword());
    }

    @PutMapping("/{userId}/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<UserResponse> updateImage(@PathVariable(value = "userId") UUID userId,
                                                     @RequestBody @Validated(UserRequest.UserView.ImagePut.class)
                                                     @JsonView(UserRequest.UserView.ImagePut.class) UserRequest userRequest){
      return ResponseEntity.ok(mapper.toResponse(service.updateImage(userId, mapper.toEntity(userRequest))));
    }
}
