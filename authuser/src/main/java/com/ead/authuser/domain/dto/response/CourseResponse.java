package com.ead.authuser.domain.dto.response;

import com.ead.authuser.domain.enums.CourseLevel;
import com.ead.authuser.domain.enums.CourseStatus;
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

    private CourseStatus courseStatus;

    private CourseLevel courseLevel;

    private UUID userInstructor;

}
