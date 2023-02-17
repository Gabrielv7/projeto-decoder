package com.ead.authuser.validator;

import com.ead.authuser.domain.User;
import com.ead.authuser.exception.BusinessException;
import com.ead.authuser.repository.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCourseValidator {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private MessageSource messageSource;

    public void validSubscriptionUserInCourse(User user, UUID courseId){

        boolean exists = userCourseRepository.existsByUserAndCourseId(user, courseId);
        if(exists){
            throw new BusinessException(messageSource.getMessage("user-already-subscription", null, LocaleContextHolder.getLocale()));
        }
    }

}
