package com.ead.authuser.service.impl;

import com.ead.authuser.model.Role;
import com.ead.authuser.model.User;
import com.ead.authuser.assembler.UserAssembler;
import com.ead.authuser.dto.rabbit.UserEventDto;
import com.ead.authuser.model.enums.ActionTypeEnum;
import com.ead.authuser.model.enums.RoleTypeEnum;
import com.ead.authuser.model.enums.UserTypeEnum;
import com.ead.authuser.publisher.UserEventExchangeSender;
import com.ead.authuser.service.InstructorService;
import com.ead.authuser.service.RoleService;
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
    private final RoleService roleService;

    @Transactional
    @Override
    public User updateUserForTypeInstructor(UUID userId) {
        User user = userService.findById(userId);
        associateUserWithRoleTypeInstructor(user);
        user.setUserType(UserTypeEnum.INSTRUCTOR);
        UserEventDto userEventDto = userAssembler.assemblerUserEventDto(user, ActionTypeEnum.UPDATE);
        userEventExchangeSender.sendToUserEventExchange(userEventDto);
        return user;
    }

    private void associateUserWithRoleTypeInstructor(User user) {
        Role role = roleService.findByRoleType(RoleTypeEnum.ROLE_INSTRUCTOR);
        user.getRoles().add(role);
    }

}
