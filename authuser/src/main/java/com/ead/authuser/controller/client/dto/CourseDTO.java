package com.ead.authuser.controller.client.dto;

import com.ead.authuser.domain.model.enums.CourseLevelEnum;
import com.ead.authuser.domain.model.enums.CourseStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

    private UUID courseId;

    private String name;

    private String description;

    private String imageUrl;

    private CourseStatusEnum courseStatus;

    private CourseLevelEnum courseLevel;

    private UUID userInstructor;

}
