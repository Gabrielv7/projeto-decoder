package com.ead.course.service.impl;

import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;

}
