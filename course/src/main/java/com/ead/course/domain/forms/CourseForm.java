package com.ead.course.domain.forms;

import com.ead.course.domain.enums.CourseLevel;
import com.ead.course.domain.enums.CourseStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseForm {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotNull
    private CourseStatus courseStatus;

    @NotNull
    private UUID userInstructor;

    @NotNull
    private CourseLevel courseLevel;


}
