package com.ead.course.domain.services.impl;

import com.ead.course.domain.repositories.CourseRepository;
import com.ead.course.domain.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired

    CourseRepository courseRepository;

}
