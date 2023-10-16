package com.ead.authuser.service.impl;

import com.ead.authuser.domain.model.Role;
import com.ead.authuser.domain.model.User;
import com.ead.authuser.domain.assembler.UserAssembler;
import com.ead.authuser.messaging.dto.UserEventDto;
import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.domain.model.enums.ActionTypeEnum;
import com.ead.authuser.domain.model.enums.RoleTypeEnum;
import com.ead.authuser.exception.NotFoundException;
import com.ead.authuser.mapper.UserMapper;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.messaging.publisher.UserEventExchangeSender;
import com.ead.authuser.service.RoleService;
import com.ead.authuser.service.UserService;
import com.ead.authuser.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final RoleService roleService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

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
        User user = findById(userId);
        userRepository.deleteById(user.getUserId());
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(user, ActionTypeEnum.DELETE);
        userEventExchangeSender.sendToUserEventExchange(userEventDto);
    }

    @Override
    @Transactional
    public User save(UserRequest userRequest) {
        validator.validateCreate(userRequest);
        User user = mapper.toEntity(userRequest);
        encodePasswordUser(user);
        associateUserWithRoleTypeStudent(user);
        User userSaved = userRepository.save(user);
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(userSaved, ActionTypeEnum.CREATE);
        userEventExchangeSender.sendToUserEventExchange(userEventDto);
        return userSaved;
    }

    @Override
    @Transactional
    public User update(UUID userId, User user) {
        User userFind = findById(userId);
        userFind.setFullName(user.getFullName());
        userFind.setPhoneNumber(user.getPhoneNumber());
        userFind.setCpf(user.getCpf());
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(userFind, ActionTypeEnum.UPDATE);
        userEventExchangeSender.sendToUserEventExchange(userEventDto);
        return userFind;
    }

    @Transactional
    @Override
    public void updatePassword(UUID userId, UserRequest userRequest) {
        User userFind = findById(userId);
        validator.validateUpdatePassword(userFind, userRequest);
        userFind.setPassword(userRequest.getPassword());
    }

    @Override
    @Transactional
    public User updateImage(UUID userId, User user) {
        User userFind = findById(userId);
        userFind.setImageUrl(user.getImageUrl());
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(userFind, ActionTypeEnum.UPDATE);
        userEventExchangeSender.sendToUserEventExchange(userEventDto);
        return userFind;
    }

    private void associateUserWithRoleTypeStudent(User user) {
        Role role = roleService.findByRoleType(RoleTypeEnum.ROLE_STUDENT);
        user.getRoles().add(role);
    }

    private void encodePasswordUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
