package com.ead.authuser.mapper;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.dto.UserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    ModelMapper modelMapper;

    public User toEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

}
