package com.ead.course.service.impl;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.LessonRequest;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleService;
import com.ead.course.validator.LessonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;
    private final MessageSource messageSource;
    private final LessonValidator lessonValidator;

    @Transactional
    @Override
    public Lesson save(UUID moduleId, Lesson lesson) {
        lessonValidator.validTitleAndDescriptionAlreadyExists(lesson.getTitle(), lesson.getDescription());
        Module module = moduleService.findById(moduleId);
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public void deleteById(UUID moduleId, UUID lessonId) {
        Lesson lesson = findLessonIntoModule(moduleId, lessonId);
        lessonRepository.deleteById(lesson.getLessonId());
    }

    @Transactional
    @Override
    public Lesson update(UUID moduleId, UUID lessonId, LessonRequest lessonRequest) {
        lessonValidator.validTitleAndDescriptionAlreadyExists(lessonRequest.getTitle(), lessonRequest.getDescription());
        Lesson lesson = findLessonIntoModule(moduleId, lessonId);
        lesson.setTitle(lessonRequest.getTitle());
        lesson.setDescription(lessonRequest.getDescription());
        if(Objects.nonNull(lessonRequest.getVideoUrl())){
            lesson.setVideoUrl(lessonRequest.getVideoUrl());
        }
        return lesson;
    }

    public Lesson findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId)
                .orElseThrow(()-> new BusinessException(messageSource.getMessage("lesson-not-found-in-module", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public Page<Lesson> findLessonsByModuleId(Specification<Lesson> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }

}
