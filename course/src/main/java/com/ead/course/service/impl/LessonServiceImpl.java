package com.ead.course.service.impl;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.LessonRequest;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleService;
import com.ead.course.validator.LessonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LessonValidator validator;

    @Transactional
    @Override
    public Lesson save(UUID moduleId, Lesson lesson) {
        validator.validTitleAndDescriptionAlreadyExists(lesson);
        Module module = moduleService.findById(moduleId);
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public void validatedAndDelete(UUID moduleId, UUID lessonId) {
        Lesson lesson = this.findLessonIntoModule(moduleId, lessonId);
        lessonRepository.deleteById(lesson.getLessonId());
    }

    public Lesson findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId)
                .orElseThrow(()-> new BusinessException(messageSource.getMessage("lesson-not-found-in-module", null, LocaleContextHolder.getLocale())));
    }

    @Transactional
    @Override
    public Lesson update(UUID moduleId, UUID lessonId, LessonRequest lessonRequest) {
        validator.validTitleAndDescriptionAlreadyExists(lessonRequest);
        Lesson lesson = this.findLessonIntoModule(moduleId, lessonId);
        lesson.setTitle(lessonRequest.getTitle());
        lesson.setDescription(lessonRequest.getDescription());
        if(Objects.nonNull(lessonRequest.getVideoUrl())){
            lesson.setVideoUrl(lessonRequest.getVideoUrl());
        }
        return lesson;
    }

    @Override
    public List<Lesson> findAllLessonsByModuleId(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }

}
