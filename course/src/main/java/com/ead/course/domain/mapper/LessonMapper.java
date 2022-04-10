package com.ead.course.domain.mapper;

import com.ead.course.domain.models.dtos.LessonDto;
import com.ead.course.domain.models.forms.LessonForm;
import com.ead.course.domain.models.LessonModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonMapper {

    @Autowired
    ModelMapper modelMapper;

    public LessonModel toEntity(LessonForm lessonForm){

        return modelMapper.map(lessonForm, LessonModel.class);

    }

    public LessonDto toDto(LessonModel lessonModel){

        return modelMapper.map(lessonModel, LessonDto.class);

    }

    public List<LessonDto> toCollectionDto(List<LessonModel> lessons){

        return lessons.stream().map(this::toDto).collect(Collectors.toList());

    }

}
