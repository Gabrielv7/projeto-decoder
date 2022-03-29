package com.ead.course.domain.services.impl;

import com.ead.course.domain.models.ModuleModel;
import com.ead.course.domain.repositories.CourseRepository;
import com.ead.course.domain.repositories.LessonRepository;
import com.ead.course.domain.repositories.ModuleRepository;
import com.ead.course.domain.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(UUID courseId) {

        var moduleModelList = moduleRepository.findAllModulesIntoCourse(courseId);

        if(!moduleModelList.isEmpty()){

            for(ModuleModel moduleModel : moduleModelList){

                var lessonModelList = lessonRepository.findAllLessonIntoModule(moduleModel.getModuleId());

                if(!lessonModelList.isEmpty()){

                    lessonRepository.deleteAll(lessonModelList);

                }
            }

            moduleRepository.deleteAll(moduleModelList);

        }

        courseRepository.deleteById(courseId);

    }
}
