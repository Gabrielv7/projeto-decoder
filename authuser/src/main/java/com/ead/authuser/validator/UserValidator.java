package com.ead.authuser.validator;

import com.ead.authuser.domain.model.User;
import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.exception.BusinessException;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.util.ConstantsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class UserValidator {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public void validateCreate(UserRequest userRequest) {
        this.validUsernameAndEmailAlreadyExists(userRequest);
    }

    public void validateUpdatePassword(User user, UserRequest userRequest) {
        this.matchOldPassword(user.getPassword(), userRequest.getOldPassword());
    }

    private void validUsernameAndEmailAlreadyExists(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {

            String message = messageSource.getMessage("email-already-registered", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "validUsernameAndEmailAlreadyExists", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);

        } else if (userRepository.existsByUsername(userRequest.getUsername())) {

            String message = messageSource.getMessage("username-already-registered", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "validUsernameAndEmailAlreadyExists", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);
        }

    }

    private void matchOldPassword(String newPassword, String oldPassword) {
        if (!newPassword.equalsIgnoreCase(oldPassword)) {

            String message = messageSource.getMessage("password-invalid", null, LocaleContextHolder.getLocale());

            log.error(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MESSAGE,
                    "matchOldPassword", "BusinessException", ConstantsLog.LOG_HTTP_CODE_BAD_REQUEST, message);

            throw new BusinessException(message);
        }
    }
}
