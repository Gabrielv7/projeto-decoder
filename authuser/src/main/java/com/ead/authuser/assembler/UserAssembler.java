package com.ead.authuser.assembler;

import com.ead.authuser.model.User;
import com.ead.authuser.dto.rabbit.UserEventDto;
import com.ead.authuser.model.enums.ActionTypeEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAssembler {

    private final ModelMapper modelMapper;

    public UserEventDto assemblerUserEventDto(User user, ActionTypeEnum actionTypeEnum){
        UserEventDto userEventDto = modelMapper.map(user, UserEventDto.class);
        userEventDto.setActionType(actionTypeEnum);
        return userEventDto;
    }

}
