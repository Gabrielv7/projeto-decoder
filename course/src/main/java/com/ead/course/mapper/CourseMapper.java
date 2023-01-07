package com.ead.course.mapper;

import com.ead.course.domain.Course;
import com.ead.course.domain.dto.request.CourseRequest;
import com.ead.course.domain.dto.response.CourseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Course toEntity(CourseRequest courseRequest){
        return modelMapper.map(courseRequest, Course.class);
    }

    public CourseResponse toResponse(Course course){
        return modelMapper.map(course, CourseResponse.class);
    }

    public List<CourseResponse> toCollectionResponse(List<Course> courses){
        return courses.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
