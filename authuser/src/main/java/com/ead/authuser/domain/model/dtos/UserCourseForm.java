package com.ead.authuser.domain.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UserCourseForm {

    private UUID userId;

    @NotNull
    private  UUID courseId;

}
