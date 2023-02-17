package com.ead.authuser.mapper;

import com.ead.authuser.domain.UserCourse;
import com.ead.authuser.domain.dto.response.UserCourseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCourseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserCourseResponse toResponse(UserCourse userCourse) {
        return modelMapper.map(userCourse, UserCourseResponse.class);
    }
}
