package com.ead.course.mapper;

import com.ead.course.domain.CourseUser;
import com.ead.course.domain.dto.response.CourseUserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseUserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CourseUserResponse toResponse(CourseUser courseUser){
        return modelMapper.map(courseUser, CourseUserResponse.class);
    }

}
