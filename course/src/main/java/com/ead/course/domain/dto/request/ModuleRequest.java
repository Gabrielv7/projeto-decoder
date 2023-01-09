package com.ead.course.domain.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ModuleRequest {

    @NotBlank(message = "{module-title-not-blank}")
    private String title;

    @NotBlank(message = "{module-description-not-blank}")
    private String description;

}
