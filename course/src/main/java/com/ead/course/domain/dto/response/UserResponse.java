package com.ead.course.domain.dto.response;

import com.ead.course.domain.enums.UserStatus;
import com.ead.course.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private UUID userId;

    private String username;

    private String email;

    private String fullName;

    private UserStatus userStatus;

    private UserType userType;

    private String phoneNumber;

    private String cpf;

    private String imageUrl;

}
