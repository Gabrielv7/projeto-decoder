package com.ead.authuser.mapper;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.domain.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public UserResponse toResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

}
