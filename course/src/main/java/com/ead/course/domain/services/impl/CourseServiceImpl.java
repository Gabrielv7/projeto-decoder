package com.ead.course.domain.services.impl;

import com.ead.course.common.exceptions.CourseNotFoundException;
import com.ead.course.domain.models.CourseModel;
import com.ead.course.domain.models.ModuleModel;
import com.ead.course.domain.models.forms.CourseUpdateForm;
import com.ead.course.domain.repositories.CourseRepository;
import com.ead.course.domain.repositories.CourseUserRepository;
import com.ead.course.domain.repositories.LessonRepository;
import com.ead.course.domain.repositories.ModuleRepository;
import com.ead.course.domain.services.CourseService;
import com.ead.course.infrastructure.clients.MsAuthUser;
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

    @Autowired
    CourseUserRepository courseUserRepository;

    @Autowired
    MsAuthUser msAuthUser;

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

        // verifica se tem modulos relacionados a um curso
        var moduleModelList = moduleRepository.findAllModulesIntoCourse(courseId);

        // se a lista não estiver vazia
        if(!moduleModelList.isEmpty()) {

            for (ModuleModel moduleModel : moduleModelList) {

                // verifica se tem lessons relacionadas a um modulo
                var lessonModelList = lessonRepository.findAllLessonIntoModule(moduleModel.getModuleId());

                // se a lista não estiver vazia
                if (!lessonModelList.isEmpty()) {

                    // deleta as lessons vinculadas a um modulo
                    lessonRepository.deleteAll(lessonModelList);

                }
            }

            // deleta os modulos vinculados a um curso
            moduleRepository.deleteAll(moduleModelList);
        }

        // verifica se tem CourseUser vinculados a um curso
        var courseUserModelList = courseUserRepository.findAllCourseUserIntoCourse(courseId);

        // variavel de controle para definir se precisa deletar essa relação no ms authuser
        boolean deleteCourseUserInAuthUser = false;

        // se a lista não estiver vazia
        if(!courseUserModelList.isEmpty()){

            // deleta os CourseUsers vinculados a um curso
            courseUserRepository.deleteAll(courseUserModelList);

            // necessário deletar essa relação no ms authuser
            deleteCourseUserInAuthUser = true;
        }

        // deleta o curso
        courseRepository.deleteById(courseId);

        if(deleteCourseUserInAuthUser){

            // deleta a relação no lado do ms authuser
            msAuthUser.deleteCourseInAuthUser(courseId);

        }

    }
}
