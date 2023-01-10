package com.ead.course.service;

import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.ModuleRequest;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    void delete(UUID moduleId);

    Module save(Module module, UUID courseId);

    void validateAndDelete(UUID courseId, UUID moduleId);

    Module updateModule(UUID courseId, UUID moduleId, ModuleRequest moduleRequest);

    List<Module> findAllByCourseId(UUID courseId);

    Module findModuleIntoCourse(UUID courseId, UUID moduleId);

    Module findById(UUID moduleId);

}
