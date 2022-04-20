package com.ead.course.infrastructure.models.form;

import lombok.Data;

import java.util.UUID;

@Data
public class CourseUserForm {

    private UUID courseId;

    private UUID userId;
}
