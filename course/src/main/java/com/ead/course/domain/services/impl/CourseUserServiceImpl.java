package com.ead.course.domain.services.impl;

import com.ead.course.domain.repositories.CourseUserRepository;
import com.ead.course.domain.services.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseUserRepository courseUserRepository;

}
