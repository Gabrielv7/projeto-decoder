package com.ead.authuser.api.controllers;

import com.ead.authuser.domain.dtos.UserDto;
import com.ead.authuser.domain.forms.UserPasswordForm;
import com.ead.authuser.domain.forms.UserUpdateForm;
import com.ead.authuser.domain.forms.UserUpdateImageForm;
import com.ead.authuser.domain.services.UserService;
import com.ead.authuser.api.mapper.UserMapper;
import com.ead.authuser.especifications.SpecificationTemplate;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
                                     @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){

        var page = userService.findAll(spec, pageable).map(mapper::toDto);

        if(!page.isEmpty()){

         page.toList()
                 .forEach(userDto -> userDto.add(linkTo(methodOn(UserController.class)
                         .getOneUser(userDto.getUserId()))
                         .withSelfRel()));
        }

        return page;

    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getOneUser(@PathVariable(value = "userId") UUID userId){

        return mapper.toDto(userService.findById(userId));

    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "userId") UUID userId){

        userService.delete(userId);

    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable(value = "userId") UUID userId,
                              @RequestBody @Valid UserUpdateForm userUpdateForm){


        return mapper.toDto(userService.updateUser(userUpdateForm, userId));


    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updatePassword(@PathVariable(value = "userId") UUID userId,
                                  @RequestBody @Valid UserPasswordForm userPasswordForm){

       var userModel = userService.updatePassword(userId, userPasswordForm.getOldPassword(), userPasswordForm.getPassword());

       return mapper.toDto(userModel);


    }

    @PutMapping("/{userId}/image")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateImage(@PathVariable(value = "userId") UUID userId,
                               @RequestBody @Valid UserUpdateImageForm userUpdateImageForm){


         return mapper.toDto(userService.updateImageUser(userUpdateImageForm, userId));


    }


}
