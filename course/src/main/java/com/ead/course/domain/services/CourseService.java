package com.ead.course.domain.services;

import com.ead.course.domain.forms.CourseUpdateForm;
import com.ead.course.domain.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface CourseService {

    void delete (UUID courseId);

    CourseModel save(CourseModel courseModel);

    CourseModel findById(UUID courseId);

    Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);

    CourseModel updateCourse(UUID courseId, CourseUpdateForm courseUpdateForm);
}
