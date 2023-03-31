package com.ead.authuser.domain.assembler;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.dto.rabbit.UserEventDto;
import com.ead.authuser.domain.enums.ActionTypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UserEventDto assemblerUserEventDto(User user, ActionTypeEnum actionTypeEnum){
        UserEventDto userEventDto = modelMapper.map(user, UserEventDto.class);
        userEventDto.setActionType(actionTypeEnum);
        return userEventDto;
    }

}
