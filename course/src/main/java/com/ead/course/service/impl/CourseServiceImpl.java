package com.ead.course.service.impl;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.Module;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(UUID courseId) {
        List<Module> modules = moduleRepository.findAllModulesIntoCourse(courseId);
        if(!modules.isEmpty()){
            modules.forEach(module -> {
                List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if(!lessons.isEmpty()){
                    lessonRepository.deleteAll(lessons);
                }
            });
            moduleRepository.deleteAll(modules);
        }
        courseRepository.deleteById(courseId);
    }
}
