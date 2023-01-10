package com.ead.course.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequest {

    @NotBlank(message = "{lesson-title-not-blank}")
    private String title;

    private String description;

    @NotBlank(message = "{lesson-video-url-not-blank}")
    private String videoUrl;


}
