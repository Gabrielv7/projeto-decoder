package com.ead.course.service;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.dto.request.LessonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface LessonService {

    Lesson save(UUID moduleId, Lesson lesson);

    void deleteById(UUID moduleId, UUID lessonId);

    Lesson findLessonIntoModule(UUID moduleId, UUID lessonId);

    Lesson update(UUID moduleId, UUID lessonId, LessonRequest lessonRequest);

    Page<Lesson> findLessonsByModuleId(Specification<Lesson> spec, Pageable pageable);
}
