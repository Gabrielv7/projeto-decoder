package com.ead.authuser.domain.dto.response;

import com.ead.authuser.domain.enums.CourseLevelEnum;
import com.ead.authuser.domain.enums.CourseStatusEnum;
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
