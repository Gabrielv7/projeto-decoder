package com.ead.course.api.controllers;

import com.ead.course.domain.models.dtos.ModuleDto;
import com.ead.course.domain.models.forms.ModuleForm;
import com.ead.course.domain.services.ModuleService;
import com.ead.course.domain.mapper.ModuleMapper;
import com.ead.course.domain.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    ModuleMapper mapper;


    @PostMapping("/courses/{courseId}/modules")
    @ResponseStatus(HttpStatus.CREATED)
    public ModuleDto saveModule(@PathVariable(value = "courseId") UUID courseId,
                                @RequestBody @Valid ModuleForm moduleForm){

        log.debug("POST saveModule received {} ", moduleForm);

        var moduleModel = mapper.ToEntity(moduleForm);

        moduleService.save(moduleModel, courseId);

        log.debug("POST saveModule save moduleId {} ", moduleModel.getModuleId());
        log.info("Module save sucessfully moduleId {} ", moduleModel.getModuleId());

        return mapper.toDto(moduleModel);


    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable(value = "courseId") UUID courseId,
                             @PathVariable(value = "moduleId") UUID moduleId){

        log.debug("DELETE deleteModule received {} ", moduleId);

        var moduleModel = moduleService.findModuleIntoCourse(moduleId, courseId);

        moduleService.delete(moduleModel.getModuleId());

    }


    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public ModuleDto updateModule(@PathVariable(value = "courseId") UUID courseId,
                                  @PathVariable(value = "moduleId") UUID moduleId,
                                  @RequestBody @Valid ModuleForm moduleForm){

        log.debug("PUT updateModule received {} ", moduleForm);

        var moduleModel = moduleService.findModuleIntoCourse(moduleId, courseId);

        moduleService.updateCourse(moduleForm, moduleModel);

        log.debug("PUT updateModule update moduleId {} ", moduleModel.getModuleId());
        log.info("Module update sucessfully moduleId {} ", moduleModel.getModuleId());

        return mapper.toDto(moduleModel);


    }

    @GetMapping("/courses/{courseId}/modules")
    @ResponseStatus(HttpStatus.OK)
    public Page<ModuleDto> getAllModules(SpecificationTemplate.ModuleSpec spec,
                                         @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable,
                                         @PathVariable(value = "courseId") UUID courseId){

        return moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable)
                .map(mapper::toDto);

    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public ModuleDto getOneModule(@PathVariable(value = "courseId") UUID courseId,
                                  @PathVariable(value = "moduleId") UUID moduleId){

        return mapper.toDto(moduleService.findModuleIntoCourse(moduleId,courseId));

    }



}
