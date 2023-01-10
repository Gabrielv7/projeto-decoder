package com.ead.course.service;

import com.ead.course.domain.Course;
import com.ead.course.domain.dto.request.CourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;


public interface CourseService {

    void delete(UUID courseId);

    Course save(Course course);

    Course findById(UUID courseId);

    Course update(UUID courseId, CourseRequest courseRequest);

    Page<Course> findAll(Specification<Course> spec, Pageable pageable);

}
