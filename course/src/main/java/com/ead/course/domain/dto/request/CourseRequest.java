package com.ead.course.domain.dto.request;

import com.ead.course.domain.enums.CourseLevel;
import com.ead.course.domain.enums.CourseStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class CourseRequest {

    @NotBlank(message = "{course-name-not-blank}")
    private String name;

    @NotBlank(message = "{course-description-not-blank}")
    private String description;

    private String imageUrl;

    @NotNull(message = "{course-status-not-null}")
    private CourseStatus courseStatus;

    @NotNull(message = "{course-level-not-null}")
    private CourseLevel courseLevel;

    @NotNull(message = "{course-userInstructor-not-null}")
    private UUID userInstructor;

}
