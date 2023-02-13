package com.ead.course.validator;

import com.ead.course.exception.BusinessException;
import com.ead.course.repository.CourseRepository;
import com.ead.course.util.ConstantsLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CourseValidator {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private MessageSource messageSource;

    public void validNameAndDescriptionAlreadyExists(String name, String description) {

        if(repository.existsByName(name) && repository.existsByDescription(description)){

            String message = messageSource.getMessage("course-already-exists", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "validNameAndDescriptionAlreadyExists", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);

        }
    }

}
