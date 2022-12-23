package com.ead.authuser.validator;

import com.ead.authuser.domain.User;
import com.ead.authuser.exception.BusinessException;
import com.ead.authuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public void validUsernameAndEmailAlreadyExists(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new BusinessException(messageSource.getMessage("email-already-registered", null, LocaleContextHolder.getLocale()));
        }else if(userRepository.existsByUsername(user.getUsername())){
            throw new BusinessException(messageSource.getMessage("username-already-registered", null, LocaleContextHolder.getLocale()));
        }

    }

    public void matchOldPassword(String password, String oldPassword) {
        if(!password.equalsIgnoreCase(oldPassword)){
            throw new BusinessException(messageSource.getMessage("password-invalid", null, LocaleContextHolder.getLocale()));
        }
    }
}