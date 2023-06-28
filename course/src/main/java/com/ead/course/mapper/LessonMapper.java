package com.ead.course.mapper;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.dto.request.LessonRequest;
import com.ead.course.domain.dto.response.LessonResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LessonMapper {

    private final ModelMapper mapper;

    public Lesson toEntity(LessonRequest lessonRequest){
        return mapper.map(lessonRequest, Lesson.class);
    }

    public LessonResponse toResponse(Lesson lesson){
        return mapper.map(lesson, LessonResponse.class);
    }

    public Page<LessonResponse> convertToPageLessonsResponse(Page<Lesson> lessons) {
        return lessons.map(this::toResponse);
    }
}
