package com.ead.authuser.domain.dto.rabbit;

import com.ead.authuser.domain.enums.ActionTypeEnum;
import com.ead.authuser.domain.enums.UserStatusEnum;
import com.ead.authuser.domain.enums.UserTypeEnum;
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
