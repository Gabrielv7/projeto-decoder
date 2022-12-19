package com.ead.authuser.service.impl;

import com.ead.authuser.domain.User;
import com.ead.authuser.exception.NotFoundException;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.service.UserService;
import com.ead.authuser.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserValidator validator;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException(messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public void deleteById(UUID userId) {
        this.findById(userId);
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public User save(User user) {
        validator.validUsernameAndEmailAlreadyExists(user);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(UUID userId, User user) {
        User userFind = this.findById(userId);
        userFind.setFullName(user.getFullName());
        userFind.setPhoneNumber(user.getPhoneNumber());
        userFind.setCpf(user.getCpf());
        return userFind;
    }

    @Transactional
    @Override
    public void updatePassword(UUID userId, String oldPassword, String newPassword) {
        User userFind = this.findById(userId);
        validator.matchOldPassword(userFind.getPassword(), oldPassword);
        userFind.setPassword(newPassword);
    }

    @Override
    @Transactional
    public User updateImage(UUID userId, User user) {
        User userFind = this.findById(userId);
        userFind.setImageUrl(user.getImageUrl());
        return userFind;
    }

}
