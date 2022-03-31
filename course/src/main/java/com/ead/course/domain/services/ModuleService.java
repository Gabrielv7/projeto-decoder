package com.ead.course.domain.services;

import com.ead.course.domain.forms.ModuleForm;
import com.ead.course.domain.models.ModuleModel;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    void delete(UUID moduleId);

    ModuleModel save(ModuleModel moduleModel, UUID courseId);

    ModuleModel findModuleIntoCourse(UUID moduleId, UUID courseId);

    ModuleModel updateCourse(ModuleForm moduleForm, ModuleModel module);

    List<ModuleModel> findAllByCourse(UUID courseId);

    ModuleModel findById(UUID moduleId);
}
