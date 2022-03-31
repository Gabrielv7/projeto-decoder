package com.ead.course.domain.services.impl;

import com.ead.course.domain.exceptions.CourseOrModuleNotFoundException;
import com.ead.course.domain.forms.ModuleForm;
import com.ead.course.domain.models.ModuleModel;
import com.ead.course.domain.repositories.LessonRepository;
import com.ead.course.domain.repositories.ModuleRepository;
import com.ead.course.domain.services.CourseService;
import com.ead.course.domain.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    CourseService courseService;

    @Transactional
    @Override
    public void delete(UUID moduleId) {

        var listLesson = lessonRepository.findAllLessonIntoModule(moduleId);

        if(!listLesson.isEmpty()){

            lessonRepository.deleteAll(listLesson);

        }

        moduleRepository.deleteById(moduleId);

    }

    @Transactional
    @Override
    public ModuleModel salvar(ModuleModel moduleModel, UUID courseId) {

        var course = courseService.findById(courseId);

        moduleModel.setCourse(course);

        return moduleRepository.save(moduleModel);
    }

    @Override
    public ModuleModel findModuleIntoCourse(UUID moduleId, UUID courseId) {

       return moduleRepository.findModuleIntoCourse(moduleId, courseId)
               .orElseThrow(()-> new CourseOrModuleNotFoundException("Module not found for this course."));
    }

    @Transactional
    @Override
    public ModuleModel updateCourse(ModuleForm moduleForm, ModuleModel module) {


        module.setTitle(moduleForm.getTitle());
        module.setDescription(moduleForm.getDescription());

        return module;
    }

    @Override
    public List<ModuleModel> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }
}
