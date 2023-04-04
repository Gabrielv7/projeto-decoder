package com.ead.authuser.service.impl;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.assembler.UserAssembler;
import com.ead.authuser.domain.dto.rabbit.UserEventDto;
import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.domain.enums.ActionTypeEnum;
import com.ead.authuser.domain.enums.UserTypeEnum;
import com.ead.authuser.exception.NotFoundException;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.sender.UserEventExchangeSender;
import com.ead.authuser.service.UserService;
import com.ead.authuser.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserValidator validator;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private UserEventExchangeSender userEventExchangeSender;

    @Override
    public Page<User> findAllUsers(Pageable pageable, Specification<User> spec) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException(messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale())));
    }

    @Transactional
    @Override
    public void deleteById(UUID userId) {
        User user = this.findById(userId);
        userRepository.deleteById(user.getUserId());
        this.assemblerAndSendToUserEventExchange(user, ActionTypeEnum.DELETE);
    }

    @Override
    @Transactional
    public User save(User user) {
        validator.validUsernameAndEmailAlreadyExists(user);
        User userSaved = userRepository.save(user);
        this.assemblerAndSendToUserEventExchange(userSaved, ActionTypeEnum.CREATE);
        return userSaved;
    }

    @Override
    @Transactional
    public User update(UUID userId, User user) {
        User userFind = this.findById(userId);
        userFind.setFullName(user.getFullName());
        userFind.setPhoneNumber(user.getPhoneNumber());
        userFind.setCpf(user.getCpf());
        this.assemblerAndSendToUserEventExchange(userFind, ActionTypeEnum.UPDATE);
        return userFind;
    }

    @Transactional
    @Override
    public void updatePassword(UUID userId, UserRequest userRequest) {
        User userFind = this.findById(userId);
        validator.matchOldPassword(userFind.getPassword(), userRequest.getOldPassword());
        userFind.setPassword(userRequest.getPassword());
    }

    @Override
    @Transactional
    public User updateImage(UUID userId, User user) {
        User userFind = this.findById(userId);
        userFind.setImageUrl(user.getImageUrl());
        this.assemblerAndSendToUserEventExchange(userFind, ActionTypeEnum.UPDATE);
        return userFind;
    }

    public void assemblerAndSendToUserEventExchange(User user, ActionTypeEnum actionTypeEnum){
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(user, actionTypeEnum);
        userEventExchangeSender.sendToUserEventExchange(userEventDto);
    }

}
