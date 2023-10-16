package com.ead.course.messaging.dto;

import com.ead.course.domain.enums.ActionTypeEnum;
import com.ead.course.domain.enums.UserStatusEnum;
import com.ead.course.domain.enums.UserTypeEnum;
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
