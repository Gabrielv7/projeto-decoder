package com.ead.authuser.service.impl;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.assembler.UserAssembler;
import com.ead.authuser.domain.dto.rabbit.UserEventDto;
import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.domain.enums.ActionTypeEnum;
import com.ead.authuser.exception.NotFoundException;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.publisher.UserEventExchangeSender;
import com.ead.authuser.service.UserService;
import com.ead.authuser.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final UserValidator validator;
    private final UserAssembler userAssembler;
    private final UserEventExchangeSender userEventExchangeSender;

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
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(user, ActionTypeEnum.DELETE);
        this.sendToUserEventExchange(userEventDto);
    }

    @Override
    @Transactional
    public User save(User user) {
        validator.validateCreate(user);
        User userSaved = userRepository.save(user);
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(userSaved, ActionTypeEnum.CREATE);
        this.sendToUserEventExchange(userEventDto);
        return userSaved;
    }

    @Override
    @Transactional
    public User update(UUID userId, User user) {
        User userFind = this.findById(userId);
        userFind.setFullName(user.getFullName());
        userFind.setPhoneNumber(user.getPhoneNumber());
        userFind.setCpf(user.getCpf());
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(userFind, ActionTypeEnum.UPDATE);
        this.sendToUserEventExchange(userEventDto);
        return userFind;
    }

    @Transactional
    @Override
    public void updatePassword(UUID userId, UserRequest userRequest) {
        User userFind = this.findById(userId);
        validator.validateUpdatePassword(userFind, userRequest);
        userFind.setPassword(userRequest.getPassword());
    }

    @Override
    @Transactional
    public User updateImage(UUID userId, User user) {
        User userFind = this.findById(userId);
        userFind.setImageUrl(user.getImageUrl());
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(userFind, ActionTypeEnum.UPDATE);
        this.sendToUserEventExchange(userEventDto);
        return userFind;
    }

    public void sendToUserEventExchange(UserEventDto userEventDto){
        userEventExchangeSender.sendToUserEventExchange(userEventDto);
    }

}
