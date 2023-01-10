package com.ead.course.domain.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LessonRequest {

    @NotBlank(message = "{lesson-title-not-blank}")
    private String title;

    private String description;

    @NotBlank(message = "{lesson-video-url-not-blank}")
    private String videoUrl;


}
