package com.ead.authuser.validator;

import com.ead.authuser.domain.User;
import com.ead.authuser.exception.BusinessException;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.util.ConstantsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public void validUsernameAndEmailAlreadyExists(User user) {

        if(userRepository.existsByEmail(user.getEmail())){

            String message = messageSource.getMessage("email-already-registered", null, LocaleContextHolder.getLocale());
            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "validUsernameAndEmailAlreadyExists", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);

        }else if(userRepository.existsByUsername(user.getUsername())){

            String message = messageSource.getMessage("username-already-registered", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "validUsernameAndEmailAlreadyExists", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);
        }

    }

    public void matchOldPassword(String password, String oldPassword) {
        if(!password.equalsIgnoreCase(oldPassword)){

            String message = messageSource.getMessage("password-invalid", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "matchOldPassword", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);
        }
    }
}
