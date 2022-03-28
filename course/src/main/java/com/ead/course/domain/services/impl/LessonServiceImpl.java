package com.ead.course.domain.services.impl;

import com.ead.course.domain.repositories.LessonRepository;
import com.ead.course.domain.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;

}
