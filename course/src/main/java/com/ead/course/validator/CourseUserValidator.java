package com.ead.course.validator;

import com.ead.course.domain.Course;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.CourseUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class CourseUserValidator {

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private MessageSource messageSource;

    public void validSubscriptionUserInCourse(Course course, UUID userId){
        boolean exists = courseUserRepository.existsByCourseAndUserId(course, userId);
        if(exists){
            throw new BusinessException(messageSource.getMessage("user-already-subscription", null, LocaleContextHolder.getLocale()));
        }
    }


}
