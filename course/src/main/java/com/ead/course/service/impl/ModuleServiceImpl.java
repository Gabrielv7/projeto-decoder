package com.ead.course.service.impl;

import com.ead.course.domain.Course;
import com.ead.course.domain.Lesson;
import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.ModuleRequest;
import com.ead.course.exception.NotFoundException;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseService;
import com.ead.course.service.ModuleService;
import com.ead.course.validator.ModuleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private CourseService courseService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModuleValidator validator;

    @Transactional
    @Override
    public void delete(UUID moduleId) {
        List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(moduleId);
        if(!lessons.isEmpty()){
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.deleteById(moduleId);
    }

    @Transactional
    @Override
    public Module save(Module module, UUID courseId) {
        validator.validTitleAndDescriptionAlreadyExists(module);
        Course course = courseService.findById(courseId);
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    public Module findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId).
                orElseThrow(()-> new NotFoundException(messageSource.getMessage("module-not-found-in-course", null, LocaleContextHolder.getLocale())));

    }

    @Override
    public Module findById(UUID moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(()-> new NotFoundException(messageSource.getMessage("module-not-found", null, LocaleContextHolder.getLocale())));
    }

    @Transactional
    public void validateAndDelete(UUID courseId, UUID moduleId){
        Module module = this.findModuleIntoCourse(courseId, moduleId);
        this.delete(module.getModuleId());
    }

    @Transactional
    @Override
    public Module updateModule(UUID courseId, UUID moduleId, ModuleRequest moduleRequest) {
        validator.validTitleAndDescriptionAlreadyExists(moduleRequest);
        Module module = this.findModuleIntoCourse(courseId, moduleId);
        module.setTitle(moduleRequest.getTitle());
        module.setDescription(moduleRequest.getDescription());
        return module;
    }

    @Override
    public List<Module> findAllByCourseId(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

}
