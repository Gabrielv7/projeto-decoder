package com.ead.course.domain.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserCourseRequest {

    private UUID courseId;

}
