package com.ead.authuser.messaging.dto;

import com.ead.authuser.domain.model.enums.ActionTypeEnum;
import com.ead.authuser.domain.model.enums.UserStatusEnum;
import com.ead.authuser.domain.model.enums.UserTypeEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class UserEventDto {

    private UUID userId;
    private String email;
    private String fullName;
    private UserStatusEnum userStatus;
    private UserTypeEnum userType;
    private String cpf;
    private String imageUrl;
    private ActionTypeEnum actionType;

}
