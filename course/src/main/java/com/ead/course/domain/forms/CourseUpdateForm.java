package com.ead.course.domain.forms;

import com.ead.course.domain.enums.CourseLevel;
import com.ead.course.domain.enums.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CourseUpdateForm {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotNull
    private CourseStatus courseStatus;

    @NotNull
    private CourseLevel courseLevel;


}
