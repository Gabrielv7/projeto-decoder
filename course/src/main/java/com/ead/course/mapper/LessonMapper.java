package com.ead.course.mapper;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.dto.request.LessonRequest;
import com.ead.course.domain.dto.response.LessonResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonMapper {

    @Autowired
    private ModelMapper mapper;

    public Lesson toEntity(LessonRequest lessonRequest){
        return mapper.map(lessonRequest, Lesson.class);
    }

    public LessonResponse toResponse(Lesson lesson){
        return mapper.map(lesson, LessonResponse.class);
    }

    public List<LessonResponse> toCollectionResponse(List<Lesson> lessons){
        return lessons.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
