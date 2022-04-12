package com.ead.authuser.api.controllers;

import com.ead.authuser.domain.especifications.SpecificationTemplate;
import com.ead.authuser.domain.mapper.UserMapper;
import com.ead.authuser.domain.model.dtos.UserDto;
import com.ead.authuser.domain.model.forms.UserPasswordForm;
import com.ead.authuser.domain.model.forms.UserUpdateForm;
import com.ead.authuser.domain.model.forms.UserUpdateImageForm;
import com.ead.authuser.domain.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import javax.validation.Valid;
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
    UserService userService;

    @Autowired
    UserMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAllUsers(SpecificationTemplate.UserSpec spec,
                                     @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                     @RequestParam(required = false) UUID courseId){

        Page<UserDto> userDtoPage = null;

        if(Objects.nonNull(courseId)){

            userDtoPage = userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable).map(mapper::toDto);

        }

        if(Objects.isNull(courseId)){

            userDtoPage = userService.findAll(spec, pageable).map(mapper::toDto);

        }

        if(!userDtoPage.isEmpty()){

            userDtoPage.toList()
                 .forEach(userDto -> userDto.add(linkTo(methodOn(UserController.class)
                         .getOneUser(userDto.getUserId()))
                         .withSelfRel()));
        }

        return userDtoPage;

    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getOneUser(@PathVariable(value = "userId") UUID userId){

        return mapper.toDto(userService.findById(userId));

    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "userId") UUID userId){

        log.debug("DELETE deleteUser received  {} ", userId);

        userService.delete(userId);

    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable(value = "userId") UUID userId,
                              @RequestBody @Valid UserUpdateForm userUpdateForm){

        log.debug("PUT updateUser userUpdateForm received {} ", userUpdateForm);

        var userModel = userService.updateUser(userUpdateForm, userId);

        log.debug("PUT updateUser update userId {} ", userModel.getUserId());
        log.info("User update sucessfully userId {}", userModel.getUserId());

        return mapper.toDto(userModel);


    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updatePassword(@PathVariable(value = "userId") UUID userId,
                                  @RequestBody @Valid UserPasswordForm userPasswordForm){

       log.debug("PUT updatePassword userPasswordForm received {} ", userPasswordForm);

       var userModel = userService.updatePassword(userId, userPasswordForm.getOldPassword(), userPasswordForm.getPassword());

       log.debug("PUT updatePassword update userId {} ", userModel.getUserId());
       log.info("User update password sucessfully userId {} ", userModel.getUserId());

       return mapper.toDto(userModel);


    }

    @PutMapping("/{userId}/image")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateImage(@PathVariable(value = "userId") UUID userId,
                               @RequestBody @Valid UserUpdateImageForm userUpdateImageForm){

        log.debug("PUT updateImage UserUpdateImageForm received {} ", userUpdateImageForm);

        var userModel = userService.updateImageUser(userUpdateImageForm, userId);

        log.debug("PUT updateImage update userId {} ", userModel.getUserId());
        log.info("User update image sucessfully userId {} ", userModel.getUserId());

        return mapper.toDto(userModel);


    }


}
