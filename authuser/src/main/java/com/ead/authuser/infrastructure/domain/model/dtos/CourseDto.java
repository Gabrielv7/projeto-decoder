package com.ead.authuser.infrastructure.domain.model.dtos;

import com.ead.authuser.infrastructure.domain.model.enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CourseDto {

    private UUID courseId;

    private String name;

    private String description;

    private String imageUrl;

    private CourseStatus courseStatus;

    private CourseLevel courseLevel;

    private UUID userInstructor;



}
