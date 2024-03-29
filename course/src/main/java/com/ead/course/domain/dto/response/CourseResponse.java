package com.ead.course.domain.dto.response;

import com.ead.course.domain.enums.CourseLevelEnum;
import com.ead.course.domain.enums.CourseStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

}
