package com.ead.course.domain.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LessonForm {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String videoUrl;

}
