package com.ead.course.validator;

import com.ead.course.domain.Course;
import com.ead.course.domain.User;
import com.ead.course.domain.enums.UserStatusEnum;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.CourseUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class CourseUserValidator {

    private final CourseUserRepository courseUserRepository;
    private final MessageSource messageSource;

    public void validateSubscription(Course course, User user) {
        this.validSubscriptionUserInCourse(course, user);
    }

    private void validSubscriptionUserInCourse(Course course, User user) {
        if (courseUserRepository.hasExistsSubscription(course.getCourseId(), user.getUserId())) {
            throw new BusinessException(this.messageSource.getMessage("subscription-already-exists", null, LocaleContextHolder.getLocale()));
        }
        if (UserStatusEnum.BLOCKED.equals(user.getUserStatus())) {
            throw new BusinessException(messageSource.getMessage("user-is-blocked", null, LocaleContextHolder.getLocale()));
        }
    }

}
