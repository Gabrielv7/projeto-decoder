package com.ead.course.validator;

import com.ead.course.exception.BusinessException;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.util.ConstantsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ModuleValidator {

    private final ModuleRepository repository;
    private final MessageSource messageSource;

    public void validTitleAndDescriptionAlreadyExists(String title, String description) {

        if(repository.existsByTitle(title) && repository.existsByDescription(description)){

            String message = messageSource.getMessage("module-already-exists", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "validTitleAndDescriptionAlreadyExists", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);
        }
    }

}
