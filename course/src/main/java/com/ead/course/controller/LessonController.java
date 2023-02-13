package com.ead.course.controller;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.dto.request.LessonRequest;
import com.ead.course.domain.dto.response.LessonResponse;
import com.ead.course.mapper.LessonMapper;
import com.ead.course.service.LessonService;
import com.ead.course.specification.SpecificationTemplate;
import com.ead.course.util.ConstantsLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(value = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    private LessonService service;

    @Autowired
    private LessonMapper mapper;

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<LessonResponse> saveLesson(@PathVariable(value = "moduleId")UUID moduleId,
                                                     @RequestBody @Valid LessonRequest lessonRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_MODULE_ID + ConstantsLog.LOG_ENTITY,
                "saveLesson", "POST", "Saving lesson", moduleId, lessonRequest);

        Lesson lessonSaved = service.save(moduleId, mapper.toEntity(lessonRequest));

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_LESSON_ID,
                "saveLesson", ConstantsLog.LOG_EVENT_INFO, "Lesson saved", ConstantsLog.LOG_HTTP_CODE_CREATED, lessonSaved.getLessonId());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(lessonSaved));
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable(value = "moduleId")UUID moduleId,
                             @PathVariable(value = "lessonId")UUID lessonId){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_MODULE_ID + ConstantsLog.LOG_LESSON_ID,
                "deleteLesson", "DELETE", "Deleting lesson", moduleId, lessonId);

        service.validatedAndDelete(moduleId, lessonId);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_LESSON_ID,
                "deleteLesson", ConstantsLog.LOG_EVENT_INFO, "Lesson deleted", ConstantsLog.LOG_HTTP_CODE_NO_CONTENT, lessonId);
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<LessonResponse> updateLesson(@PathVariable(value = "moduleId")UUID moduleId,
                                                       @PathVariable(value = "lessonId")UUID lessonId,
                                                       @RequestBody @Valid LessonRequest lessonRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_MODULE_ID + ConstantsLog.LOG_LESSON_ID + ConstantsLog.LOG_ENTITY,
                "updateLesson", "PUT", "Updating lesson", moduleId, lessonId, lessonRequest);

        Lesson lessonUpdated = service.update(moduleId, lessonId, lessonRequest);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_LESSON_ID,
                "updateLesson", ConstantsLog.LOG_EVENT_INFO, "Lesson updated", ConstantsLog.LOG_HTTP_CODE_OK, lessonUpdated.getLessonId());

        return ResponseEntity.ok(mapper.toResponse(lessonUpdated));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Page<LessonResponse>> getAllLessonsByModuleId(@PathVariable("moduleId") UUID moduleId,
                                                                        SpecificationTemplate.LessonSpec spec,
                                                                        @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_MODULE_ID,
                "getAllLessonsByModuleId", "GET", "Searching a list of lessons", moduleId);

        Page<LessonResponse> lessonsResponse = service.findLessonsByModuleId(SpecificationTemplate.findLessonsByModuleId(moduleId).and(spec), pageable)
                .map(l -> mapper.toResponse(l));

        return ResponseEntity.ok(lessonsResponse);
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<LessonResponse> getOneLesson(@PathVariable(value = "moduleId")UUID moduleId,
                                                       @PathVariable(value = "lessonId")UUID lessonId){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_MODULE_ID + ConstantsLog.LOG_LESSON_ID,
                "getOneLesson", "GET", "Searching one lesson", moduleId, lessonId);

        Lesson lesson = service.findLessonIntoModule(moduleId, lessonId);
        return ResponseEntity.ok(mapper.toResponse(lesson));
    }

}
