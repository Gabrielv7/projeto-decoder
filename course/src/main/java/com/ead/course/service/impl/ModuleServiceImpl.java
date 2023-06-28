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
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final CourseService courseService;
    private final MessageSource messageSource;
    private final ModuleValidator moduleValidator;

    @Transactional
    @Override
    public void delete(UUID courseId, UUID moduleId) {
        findModuleIntoCourse(courseId, moduleId);
        List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(moduleId);
        if(!lessons.isEmpty()){
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.deleteById(moduleId);
    }

    @Transactional
    @Override
    public Module save(Module module, UUID courseId) {
        moduleValidator.validTitleAndDescriptionAlreadyExists(module.getTitle(), module.getDescription());
        Course course = courseService.findById(courseId);
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    @Transactional
    @Override
    public Module updateModule(UUID courseId, UUID moduleId, ModuleRequest moduleRequest) {
        moduleValidator.validTitleAndDescriptionAlreadyExists(moduleRequest.getTitle(), moduleRequest.getDescription());
        Module module = findModuleIntoCourse(courseId, moduleId);
        module.setTitle(moduleRequest.getTitle());
        module.setDescription(moduleRequest.getDescription());
        return module;
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

    @Override
    public Page<Module> findModulesByCourseId(Specification<Module> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }

}
