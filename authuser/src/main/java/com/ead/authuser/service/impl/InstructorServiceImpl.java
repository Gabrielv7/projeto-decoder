package com.ead.authuser.service.impl;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.assembler.UserAssembler;
import com.ead.authuser.domain.dto.rabbit.UserEventDto;
import com.ead.authuser.domain.enums.ActionTypeEnum;
import com.ead.authuser.domain.enums.UserTypeEnum;
import com.ead.authuser.service.InstructorService;
import com.ead.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssembler userAssembler;

    @Transactional
    @Override
    public User updateToUserTypeInstructor(UUID userId) {
        User user = userService.findById(userId);
        user.setUserType(UserTypeEnum.INSTRUCTOR);
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(user, ActionTypeEnum.UPDATE);
        userService.sendToUserEventExchange(userEventDto);
        return user;
    }
}
