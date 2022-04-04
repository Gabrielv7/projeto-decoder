package com.ead.course.domain.services;

import com.ead.course.domain.forms.LessonForm;
import com.ead.course.domain.models.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    LessonModel save(LessonModel lessonModel, UUID moduleId);

    LessonModel findLessonIntoModule(UUID moduleId, UUID lessonId);

    void deleteById(UUID lessonId);

    LessonModel updateLesson(LessonForm lessonForm, LessonModel lessonModel);

    List<LessonModel> findAllByModule(UUID moduleId);

    Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable);
}
