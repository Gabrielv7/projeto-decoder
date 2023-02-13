package com.ead.course.controller;

import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.ModuleRequest;
import com.ead.course.domain.dto.response.ModuleResponse;
import com.ead.course.mapper.ModuleMapper;
import com.ead.course.service.ModuleService;
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
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    private ModuleService service;

    @Autowired
    private ModuleMapper mapper;

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<ModuleResponse> saveModule(@PathVariable(value = "courseId") UUID courseId,
                                                     @RequestBody @Valid ModuleRequest moduleRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID + ConstantsLog.LOG_ENTITY,
                "saveModule", "POST", "Saving module", courseId, moduleRequest);

        Module moduleSaved = service.save(mapper.toEntity(moduleRequest), courseId);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MODULE_ID,
                "saveModule", ConstantsLog.LOG_EVENT_INFO, "Module saved", ConstantsLog.LOG_HTTP_CODE_CREATED, moduleSaved.getModuleId());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(moduleSaved));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable(value = "courseId") UUID courseId,
                             @PathVariable(value = "moduleId") UUID moduleId){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID + ConstantsLog.LOG_MODULE_ID,
                "deleteModule", "DELETE", "Deleting module", courseId, moduleId);

        service.validateAndDelete(courseId, moduleId);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MODULE_ID,
                "deleteModule", ConstantsLog.LOG_EVENT_INFO, "Module deleted", ConstantsLog.LOG_HTTP_CODE_NO_CONTENT, moduleId);
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable(value = "courseId") UUID courseId,
                                                       @PathVariable(value = "moduleId") UUID moduleId,
                                                       @RequestBody @Valid ModuleRequest moduleRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID + ConstantsLog.LOG_MODULE_ID + ConstantsLog.LOG_ENTITY,
                "updateModule", "PUT", "Updating module", courseId, moduleId, moduleRequest);

        Module moduleUpdated = service.updateModule(courseId, moduleId, moduleRequest);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_MODULE_ID,
                "updateModule", ConstantsLog.LOG_EVENT_INFO, "Module updated", ConstantsLog.LOG_HTTP_CODE_OK, moduleUpdated.getModuleId());

        return ResponseEntity.ok(mapper.toResponse(moduleUpdated));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<ModuleResponse>> getAllModulesByCourseId(@PathVariable(value = "courseId") UUID courseId,
                                                                        SpecificationTemplate.ModuleSpec spec,
                                                                        @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID,
                "getAllModulesByCourseId", "GET", "Searching a list of modules", courseId);

        Page<ModuleResponse> modulesResponse = service.findModulesByCourseId(SpecificationTemplate.findModulesByCourseId(courseId).and(spec), pageable)
                .map(module -> mapper.toResponse(module));

        return ResponseEntity.ok(modulesResponse);
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<ModuleResponse> getOneModule(@PathVariable(value = "courseId") UUID courseId,
                                                       @PathVariable(value = "moduleId") UUID moduleId){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID + ConstantsLog.LOG_MODULE_ID,
                "getOneModule", "GET", "Searching one module", courseId, moduleId);

        Module module = service.findModuleIntoCourse(courseId, moduleId);
        return ResponseEntity.ok(mapper.toResponse(module));
    }

}
