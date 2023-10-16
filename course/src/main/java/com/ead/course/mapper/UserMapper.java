package com.ead.course.mapper;

import com.ead.course.domain.User;
import com.ead.course.messaging.dto.UserEventDto;
import com.ead.course.domain.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse toResponse(User user){
        return modelMapper.map(user, UserResponse.class);
    }

    public User toEntity(UserEventDto userEventDto){
        return modelMapper.map(userEventDto, User.class);
    }

    public Page<UserResponse> convertToPageUsersResponse(Page<User> users) {
        return users.map(this::toResponse);
    }

}
