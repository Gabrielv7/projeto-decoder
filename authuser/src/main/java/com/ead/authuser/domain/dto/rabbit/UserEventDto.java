package com.ead.authuser.domain.dto.rabbit;

import com.ead.authuser.domain.enums.ActionTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
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
