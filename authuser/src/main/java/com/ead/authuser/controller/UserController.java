package com.ead.authuser.controller;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.domain.dto.response.UserResponse;
import com.ead.authuser.mapper.UserMapper;
import com.ead.authuser.service.UserService;
import com.ead.authuser.specification.SpecificationTemplate;
import com.ead.authuser.util.ConstantsLog;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                          @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                          @RequestParam(required = false) UUID courseId) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE,
                "getAllUsers", "GET", "Searching a list of users");

        Page<UserResponse> pageUserResponse = null;

        if(Objects.nonNull(courseId)){
            pageUserResponse = service.findAllUsers(pageable, SpecificationTemplate.findUsersByCourseId(courseId).and(spec)).map(u -> mapper.toResponse(u));
        }else{
           pageUserResponse = service.findAllUsers(pageable, spec).map(u -> mapper.toResponse(u));
        }

        if(!pageUserResponse.isEmpty()){
           pageUserResponse.forEach(userResponse -> {
               userResponse.add(linkTo(methodOn(UserController.class).getOneUser(userResponse.getUserId())).withSelfRel());
            });
        }

        return ResponseEntity.ok(pageUserResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable(value = "userId") UUID userId){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY_ID,
                "getOneUser", "GET", "Searching one user", userId);

        return ResponseEntity.ok(mapper.toResponse(service.findById(userId)));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "userId") UUID userId){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY_ID,
                "deleteUser", "DELETE", "Deleting user", userId);

        service.deleteById(userId);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_ENTITY_ID,
                "deleteUser", ConstantsLog.LOG_EVENT_INFO, "Deleted user", ConstantsLog.LOG_HTTP_CODE_NO_CONTENT, userId);

    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(value = "userId") UUID userId,
                                                   @RequestBody @Validated(UserRequest.UserView.UserPut.class)
                                                   @JsonView(UserRequest.UserView.UserPut.class) UserRequest userRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY_ID + ConstantsLog.LOG_ENTITY,
                "updateUser", "PUT", "Updating user", userId, userRequest);

        User userUpdated = service.update(userId, mapper.toEntity(userRequest));

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_ENTITY_ID,
                "updateUser", ConstantsLog.LOG_EVENT_INFO, "User updated", ConstantsLog.LOG_HTTP_CODE_OK, userUpdated.getUserId());

        return ResponseEntity.ok(mapper.toResponse(userUpdated));
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable(value = "userId") UUID userId,
                               @RequestBody @Validated(UserRequest.UserView.PasswordPut.class)
                               @JsonView(UserRequest.UserView.PasswordPut.class) UserRequest userRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY_ID,
                "updatePassword", "PUT", "Updating user password", userId);

        service.updatePassword(userId, userRequest);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_ENTITY_ID,
                "updatePassword", ConstantsLog.LOG_EVENT_INFO, "Updated user password", ConstantsLog.LOG_HTTP_CODE_OK, userId);
    }

    @PutMapping("/{userId}/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<UserResponse> updateImage(@PathVariable(value = "userId") UUID userId,
                                                     @RequestBody @Validated(UserRequest.UserView.ImagePut.class)
                                                     @JsonView(UserRequest.UserView.ImagePut.class) UserRequest userRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY_ID + ConstantsLog.LOG_ENTITY,
                "updateImage", "PUT", "Updating user image", userId, userRequest.getImageUrl());

        User userUpdated = service.updateImage(userId, mapper.toEntity(userRequest));

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_ENTITY_ID,
                "updateImage", ConstantsLog.LOG_EVENT_INFO, "Updated user image", ConstantsLog.LOG_HTTP_CODE_OK, userUpdated.getUserId());

        return ResponseEntity.ok(mapper.toResponse(userUpdated));
    }
}
