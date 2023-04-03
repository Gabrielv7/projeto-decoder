package com.ead.course.domain.dto.request;

import com.ead.course.domain.enums.CourseLevelEnum;
import com.ead.course.domain.enums.CourseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

    @NotBlank(message = "{course-name-not-blank}")
    private String name;

    @NotBlank(message = "{course-description-not-blank}")
    private String description;

    private String imageUrl;

    @NotNull(message = "{course-status-not-null}")
    private CourseStatusEnum courseStatus;

    @NotNull(message = "{course-level-not-null}")
    private CourseLevelEnum courseLevel;

    @NotNull(message = "{course-userInstructor-not-null}")
    private UUID userInstructor;

}
