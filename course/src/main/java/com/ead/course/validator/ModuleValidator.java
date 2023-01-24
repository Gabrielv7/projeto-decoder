package com.ead.course.validator;

import com.ead.course.exception.BusinessException;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.util.ConstantsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ModuleValidator {

    @Autowired
    private ModuleRepository repository;

    @Autowired
    private MessageSource messageSource;

    public void validTitleAndDescriptionAlreadyExists(String title, String description) {

        if(repository.existsByTitle(title) && repository.existsByDescription(description)){

            String message = messageSource.getMessage("module-already-exists", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "validTitleAndDescriptionAlreadyExists", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);
        }
    }

}
