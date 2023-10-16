package com.ead.authuser.domain.dto.response;

import com.ead.authuser.domain.model.enums.UserStatusEnum;
import com.ead.authuser.domain.model.enums.UserTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private UUID userId;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private String fullName;

    private UserStatusEnum userStatus;

    private UserTypeEnum userType;

    private String phoneNumber;

    private String cpf;

    private String imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

}
