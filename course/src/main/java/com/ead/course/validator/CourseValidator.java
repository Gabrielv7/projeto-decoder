package com.ead.course.validator;

import com.ead.course.domain.Course;
import com.ead.course.domain.dto.request.CourseRequest;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CourseValidator {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private MessageSource messageSource;

    public void validNameAndDescriptionAlreadyExists(Course course){
        if(repository.existsByName(course.getName()) && repository.existsByDescription(course.getDescription())){
            throw new BusinessException(messageSource.getMessage("course-already-exists", null, LocaleContextHolder.getLocale()));
        }
    }

    public void validNameAndDescriptionAlreadyExists(CourseRequest course){
        if(repository.existsByName(course.getName()) && repository.existsByDescription(course.getDescription())){
            throw new BusinessException(messageSource.getMessage("course-already-exists", null, LocaleContextHolder.getLocale()));
        }
    }

}
