package com.ead.authuser.service.impl;

import com.ead.authuser.model.User;
import com.ead.authuser.assembler.UserAssembler;
import com.ead.authuser.dto.rabbit.UserEventDto;
import com.ead.authuser.model.enums.ActionTypeEnum;
import com.ead.authuser.model.enums.UserTypeEnum;
import com.ead.authuser.publisher.UserEventExchangeSender;
import com.ead.authuser.service.InstructorService;
import com.ead.authuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InstructorServiceImpl implements InstructorService {

    private final UserService userService;
    private final UserAssembler userAssembler;
    private final UserEventExchangeSender userEventExchangeSender;

    @Transactional
    @Override
    public User updateUserForTypeInstructor(UUID userId) {
        User user = userService.findById(userId);
        user.setUserType(UserTypeEnum.INSTRUCTOR);
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(user, ActionTypeEnum.UPDATE);
        userEventExchangeSender.sendToUserEventExchange(userEventDto);
        return user;
    }
}
