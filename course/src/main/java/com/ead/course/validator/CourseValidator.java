package com.ead.course.validator;

import com.ead.course.configuration.security.AuthenticationCurrentUserService;
import com.ead.course.domain.Course;
import com.ead.course.domain.User;
import com.ead.course.domain.enums.UserTypeEnum;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.CourseRepository;
import com.ead.course.service.UserService;
import com.ead.course.util.ConstantsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class CourseValidator {

    private final CourseRepository courseRepository;
    private final MessageSource messageSource;
    private final UserService userService;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    public void validateCreate(Course course) {
        this.validNameAndDescriptionAlreadyExists(course.getName(), course.getDescription());
        this.validUserInstructor(course.getUserInstructor());
    }

    public void validateUpdate(Course course) {
        this.validNameAndDescriptionAlreadyExists(course.getName(), course.getDescription());
    }

    private void validNameAndDescriptionAlreadyExists(String name, String description) {

        if (courseRepository.existsByName(name) && courseRepository.existsByDescription(description)) {

            String message = messageSource.getMessage("course-already-exists", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "validNameAndDescriptionAlreadyExists", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);

        }
    }

    private void validUserInstructor(UUID userId) {
        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if (currentUserId.equals(userId)) {
            User user = userService.findById(userId);
            if (!UserTypeEnum.INSTRUCTOR.equals(user.getUserType())) {

                String errorMessage = messageSource.getMessage("not-permission-create-course", null, LocaleContextHolder.getLocale());

                log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                        "validUserInstructor", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, errorMessage);

                throw new BusinessException(errorMessage);
            }
        } else {
            throw  new AccessDeniedException("Forbidden");
        }

    }

}
