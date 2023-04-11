package com.ead.course.service.impl;

import com.ead.course.domain.Course;
import com.ead.course.domain.Lesson;
import com.ead.course.domain.Module;
import com.ead.course.exception.NotFoundException;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseService;
import com.ead.course.specification.SpecificationTemplate;
import com.ead.course.validator.CourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CourseValidator courseValidator;

    @Transactional
    @Override
    public Course save(Course course) {
        courseValidator.validateCreate(course);
        return courseRepository.save(course);
    }

    @Override
    public Course findById(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("course-not-found", null, LocaleContextHolder.getLocale())));

    }

    @Transactional
    @Override
    public Course update(UUID courseId, Course course) {
        courseValidator.validateUpdate(course);
        Course courseFind = this.findById(courseId);
        courseFind.setName(course.getName());
        courseFind.setDescription(course.getDescription());
        courseFind.setImageUrl(course.getImageUrl());
        courseFind.setCourseStatus(course.getCourseStatus());
        courseFind.setCourseLevel(course.getCourseLevel());
        return courseFind;
    }

    private Page<Course> findAll(Specification<Course> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    public Page<Course> decideWhichSpecToCall(UUID userId, Specification<Course> spec, Pageable pageable){
        //se o userId não for nulo, executa a busca de cursos pelo userId
        if(Objects.nonNull(userId)){
            return this.findAll(SpecificationTemplate.findCoursesByUserId(userId).and(spec), pageable);
        }

        return this.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        courseValidator.validSubscriptionUserInCourse(courseId, userId);
        courseRepository.saveCourseUser(courseId, userId);
    }

    @Transactional
    @Override
    public void delete(UUID courseId) {

        this.findById(courseId);
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
