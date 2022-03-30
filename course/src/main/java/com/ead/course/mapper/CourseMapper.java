package com.ead.course.mapper;

import com.ead.course.domain.dtos.CourseDto;
import com.ead.course.domain.forms.CourseForm;
import com.ead.course.domain.models.CourseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    @Autowired
    ModelMapper modelMapper;


    public CourseModel toEntity (CourseForm courseForm){

        return modelMapper.map(courseForm, CourseModel.class);

    }

    public CourseDto toDto(CourseModel courseModel){

        return modelMapper.map(courseModel, CourseDto.class);

    }

    public List<CourseDto> toCollectionDto(List<CourseModel> courses){

        return courses.stream().map(this::toDto).collect(Collectors.toList());

    }

}
