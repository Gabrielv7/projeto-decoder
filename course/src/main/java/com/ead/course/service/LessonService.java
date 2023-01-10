package com.ead.course.service;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.dto.request.LessonRequest;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    Lesson save(UUID moduleId, Lesson lesson);

    void validatedAndDelete(UUID moduleId, UUID lessonId);

    Lesson findLessonIntoModule(UUID moduleId, UUID lessonId);

    Lesson update(UUID moduleId, UUID lessonId, LessonRequest lessonRequest);

    List<Lesson> findAllLessonsByModuleId(UUID moduleId);
}
