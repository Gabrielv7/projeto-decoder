package com.ead.course.domain.services.impl;

import com.ead.course.domain.repositories.LessonRepository;
import com.ead.course.domain.repositories.ModuleRepository;
import com.ead.course.domain.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(UUID moduleId) {

        var listLesson = lessonRepository.findAllLessonIntoModule(moduleId);

        if(!listLesson.isEmpty()){

            lessonRepository.deleteAll(listLesson);

        }

        moduleRepository.deleteById(moduleId);

    }
}
