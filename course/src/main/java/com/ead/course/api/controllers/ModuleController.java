package com.ead.course.api.controllers;

import com.ead.course.domain.dtos.ModuleDto;
import com.ead.course.domain.forms.ModuleForm;
import com.ead.course.domain.services.ModuleService;
import com.ead.course.mapper.ModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.UUID;

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

        var moduleModel = mapper.ToEntity(moduleForm);

        return mapper.toDto(moduleService.salvar(moduleModel, courseId));


    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable(value = "courseId") UUID courseId,
                             @PathVariable(value = "moduleId") UUID moduleId){

        var module = moduleService.findModuleIntoCourse(moduleId, courseId);

        moduleService.delete(module.getModuleId());

    }


    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public ModuleDto updateModule(@PathVariable(value = "courseId") UUID courseId,
                                  @PathVariable(value = "moduleId") UUID moduleId,
                                  @RequestBody @Valid ModuleForm moduleForm){

        var module = moduleService.findModuleIntoCourse(moduleId, courseId);

        return mapper.toDto(moduleService.updateCourse(moduleForm, module));


    }

    @GetMapping("/courses/{courseId}/modules")
    @ResponseStatus(HttpStatus.OK)
    public List<ModuleDto> getAllModules(@PathVariable(value = "courseId") UUID courseId){

        return mapper.toCollectionDto(moduleService.findAllByCourse(courseId));

    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public ModuleDto getOneModule(@PathVariable(value = "courseId") UUID courseId,
                                  @PathVariable(value = "moduleId") UUID moduleId){

        return mapper.toDto(moduleService.findModuleIntoCourse(moduleId,courseId));

    }



}
