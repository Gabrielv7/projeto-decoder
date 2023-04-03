package com.ead.course.mapper;

import com.ead.course.domain.User;
import com.ead.course.domain.dto.rabbit.UserEventDto;
import com.ead.course.domain.dto.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse toResponse(User user){
        return modelMapper.map(user, UserResponse.class);
    }

    public User toEntity(UserEventDto userEventDto){
        return modelMapper.map(userEventDto, User.class);
    }

}
