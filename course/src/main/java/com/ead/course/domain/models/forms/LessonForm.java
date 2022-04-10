package com.ead.course.domain.models.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LessonForm {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String videoUrl;

}
