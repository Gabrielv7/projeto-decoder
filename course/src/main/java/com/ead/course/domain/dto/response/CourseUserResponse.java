package com.ead.course.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseUserResponse {

    private UUID id;

    private UUID userId;

    private CourseResponse course;

}
