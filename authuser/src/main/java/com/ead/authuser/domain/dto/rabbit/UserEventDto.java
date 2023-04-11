package com.ead.authuser.domain.dto.rabbit;

import com.ead.authuser.domain.enums.ActionTypeEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class UserEventDto {

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private ActionTypeEnum actionType;

}