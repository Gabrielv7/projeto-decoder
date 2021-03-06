package com.ead.course.api.controllers;

import com.ead.course.domain.models.dtos.LessonDto;
import com.ead.course.domain.models.forms.LessonForm;
import com.ead.course.domain.services.LessonService;
import com.ead.course.domain.services.ModuleService;
import com.ead.course.domain.mapper.LessonMapper;
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
public class LessonController {

    @Autowired
    LessonService lessonService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    LessonMapper mapper;


    @PostMapping("/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                @RequestBody @Valid LessonForm lessonForm){

        log.debug("POST saveLeson received {} ", lessonForm);

        var lessonModel = mapper.toEntity(lessonForm);

        lessonService.save(lessonModel, moduleId);

        log.debug("POST saveLesson save lessonId {} ", lessonModel.getLessonId());
        log.info("Lesson save sucessfully lessonId {} ", lessonModel.getLessonId());

        return mapper.toDto(lessonModel);


    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
                             @PathVariable(value = "lessonId") UUID lessonId){

        log.debug("DELETE deleteLesson received lessonId {} ", lessonId);

        var lessonModel = lessonService.findLessonIntoModule(moduleId, lessonId);

        lessonService.deleteById(lessonModel.getLessonId());

    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                  @PathVariable(value = "lessonId") UUID lessonId,
                                  @RequestBody @Valid LessonForm lessonForm){

        log.debug("PUT updateLesson received {} ", lessonForm);

        var lessonModel = lessonService.findLessonIntoModule(moduleId, lessonId);

        log.debug("PUT updateLesson update lessonId {} ", lessonModel.getLessonId());
        log.info("Lesson update sucessfully lessonId {} ", lessonModel.getLessonId());

        return mapper.toDto(lessonService.updateLesson(lessonForm, lessonModel));


    }


    @GetMapping("/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.OK)
    public Page<LessonDto> getAllLessons(SpecificationTemplate.LessonSpec spec,
                                         @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable,
                                         @PathVariable(value = "moduleId") UUID moduleId){

        return lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec),pageable).map(mapper::toDto);

    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto getOneLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                  @PathVariable(value = "lessonId") UUID lessonId){

        return mapper.toDto(lessonService.findLessonIntoModule(moduleId, lessonId));

    }


}
