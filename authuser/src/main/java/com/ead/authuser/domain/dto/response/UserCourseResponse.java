package com.ead.authuser.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCourseResponse {

    private UUID id;

    private UUID courseId;

    private UserResponse user;

}
