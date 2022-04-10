package com.ead.course.domain.services.impl;

import com.ead.course.common.exceptions.CourseNotFoundException;
import com.ead.course.domain.models.forms.CourseUpdateForm;
import com.ead.course.domain.models.CourseModel;
import com.ead.course.domain.models.ModuleModel;
import com.ead.course.domain.repositories.CourseRepository;
import com.ead.course.domain.repositories.LessonRepository;
import com.ead.course.domain.repositories.ModuleRepository;
import com.ead.course.domain.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public CourseModel save(CourseModel courseModel) {

        return courseRepository.save(courseModel);

    }

    @Override
    public CourseModel findById(UUID courseId) {

        return courseRepository.findById(courseId).orElseThrow(()-> new CourseNotFoundException("Course Not Found"));

    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {

        return courseRepository.findAll(spec, pageable);

    }
    
    @Transactional
    @Override
    public CourseModel updateCourse(UUID courseId, CourseUpdateForm courseUpdateForm) {
        
        var course = this.findById(courseId);

        course.setName(courseUpdateForm.getName());
        course.setDescription(courseUpdateForm.getDescription());
        course.setImageUrl(courseUpdateForm.getImageUrl());
        course.setCourseStatus(courseUpdateForm.getCourseStatus());
        course.setCourseLevel(courseUpdateForm.getCourseLevel());

        return courseRepository.save(course);
    }

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
