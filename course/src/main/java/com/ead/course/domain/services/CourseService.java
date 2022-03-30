package com.ead.course.domain.services;

import com.ead.course.domain.forms.CourseForm;
import com.ead.course.domain.forms.CourseUpdateForm;
import com.ead.course.domain.models.CourseModel;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    void delete (UUID courseId);

    CourseModel save(CourseModel courseModel);

    CourseModel findById(UUID courseId);

    List<CourseModel> findAll();

    CourseModel updateCourse(UUID courseId, CourseUpdateForm courseUpdateForm);
}
