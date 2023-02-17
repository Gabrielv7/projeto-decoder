package com.ead.authuser.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UserCourseRequest {
    @NotNull
    private UUID courseId;

}
