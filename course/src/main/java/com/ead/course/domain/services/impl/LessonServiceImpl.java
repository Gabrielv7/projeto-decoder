package com.ead.course.domain.services.impl;

import com.ead.course.domain.exceptions.LessonOrModuleNotFoundException;
import com.ead.course.domain.forms.LessonForm;
import com.ead.course.domain.models.LessonModel;
import com.ead.course.domain.repositories.LessonRepository;
import com.ead.course.domain.services.LessonService;
import com.ead.course.domain.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    ModuleService moduleService;

    @Transactional
    @Override
    public LessonModel save(LessonModel lessonModel, UUID moduleId) {

        var module = moduleService.findById(moduleId);

        lessonModel.setModule(module);

        return lessonRepository.save(lessonModel);

    }

    @Override
    public LessonModel findLessonIntoModule(UUID moduleId, UUID lessonId) {

        return lessonRepository.findLessonIntoModule(moduleId, lessonId)
                .orElseThrow(()-> new LessonOrModuleNotFoundException("Lesson not found this module."));

    }

    @Transactional
    @Override
    public void deleteById(UUID lessonId) {

        lessonRepository.deleteById(lessonId);

    }

    @Transactional
    @Override
    public LessonModel updateLesson(LessonForm lessonForm, LessonModel lessonModel) {

        lessonModel.setTitle(lessonForm.getTitle());
        lessonModel.setDescription(lessonForm.getDescription());
        lessonModel.setVideoUrl(lessonForm.getVideoUrl());

        return lessonRepository.save(lessonModel);
    }

    @Override
    public List<LessonModel> findAllByModule(UUID moduleId) {
        return lessonRepository.findAllLessonIntoModule(moduleId);
    }

    @Override
    public Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }
}
