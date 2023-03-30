package com.ead.course.domain.dto.response;

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

    private String userStatus;

    private String userType;

    private String phoneNumber;

    private String cpf;

    private String imageUrl;

}
