package com.ead.course.service;

import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.ModuleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ModuleService {

    void delete(UUID courseId, UUID moduleId);

    Module save(Module module, UUID courseId);

    Module updateModule(UUID courseId, UUID moduleId, ModuleRequest moduleRequest);

    Page<Module> findModulesByCourseId(Specification<Module> spec, Pageable pageable);

    Module findModuleIntoCourse(UUID courseId, UUID moduleId);

    Module findById(UUID moduleId);

}
