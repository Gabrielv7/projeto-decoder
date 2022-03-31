package com.ead.course.api.controllers;

import com.ead.course.domain.dtos.LessonDto;
import com.ead.course.domain.forms.LessonForm;
import com.ead.course.domain.services.LessonService;
import com.ead.course.domain.services.ModuleService;
import com.ead.course.mapper.LessonMapper;
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

        var lessonModel = mapper.toEntity(lessonForm);

        return mapper.toDto(lessonService.save(lessonModel, moduleId));


    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
                             @PathVariable(value = "lessonId") UUID lessonId){

        var lessonModel = lessonService.findLessonIntoModule(moduleId, lessonId);

        lessonService.deleteById(lessonModel.getLessonId());

    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                  @PathVariable(value = "lessonId") UUID lessonId,
                                  @RequestBody @Valid LessonForm lessonForm){

        var lessonModel = lessonService.findLessonIntoModule(moduleId, lessonId);

        return mapper.toDto(lessonService.updateLesson(lessonForm, lessonModel));


    }


    @GetMapping("/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.OK)
    public List<LessonDto> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId){

        return mapper.toCollectionDto(lessonService.findAllByModule(moduleId));

    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto getOneLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                  @PathVariable(value = "lessonId") UUID lessonId){

        return mapper.toDto(lessonService.findLessonIntoModule(moduleId, lessonId));

    }


}
