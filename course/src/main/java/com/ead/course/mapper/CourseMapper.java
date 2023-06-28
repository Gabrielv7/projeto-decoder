package com.ead.course.mapper;

import com.ead.course.domain.Course;
import com.ead.course.domain.dto.request.CourseRequest;
import com.ead.course.domain.dto.response.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CourseMapper {

    private final ModelMapper modelMapper;

    public Course toEntity(CourseRequest courseRequest){
        return modelMapper.map(courseRequest, Course.class);
    }

    public CourseResponse toResponse(Course course){
        return modelMapper.map(course, CourseResponse.class);
    }

    public Page<CourseResponse> convertToPageCourseResponse(Page<Course> courses) {
        return courses.map(this::toResponse);
    }

}
