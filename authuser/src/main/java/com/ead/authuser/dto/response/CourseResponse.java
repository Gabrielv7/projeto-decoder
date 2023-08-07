package com.ead.authuser.dto.response;

import com.ead.authuser.model.enums.CourseLevelEnum;
import com.ead.authuser.model.enums.CourseStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponse {

    private UUID courseId;

    private String name;

    private String description;

    private String imageUrl;

    private CourseStatusEnum courseStatus;

    private CourseLevelEnum courseLevel;

    private UUID userInstructor;

}
