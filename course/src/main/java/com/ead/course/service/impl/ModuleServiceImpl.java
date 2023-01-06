package com.ead.course.service.impl;

import com.ead.course.domain.Lesson;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(UUID moduleId) {
        List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(moduleId);
        if(!lessons.isEmpty()){
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.deleteById(moduleId);
    }
}
