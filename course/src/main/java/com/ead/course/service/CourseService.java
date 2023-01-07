package com.ead.course.service;

import com.ead.course.domain.Course;
import com.ead.course.domain.dto.request.CourseRequest;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    void delete(UUID courseId);

    Course save(Course course);

    Course findById(UUID courseId);

    Course update(UUID courseId, CourseRequest courseRequest);

    List<Course> findAll();

}
