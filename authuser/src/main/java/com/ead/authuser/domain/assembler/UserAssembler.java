package com.ead.authuser.domain.assembler;

import com.ead.authuser.domain.model.User;
import com.ead.authuser.messaging.dto.UserEventDto;
import com.ead.authuser.domain.model.enums.ActionTypeEnum;
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
