package com.ead.course.api.controllers;

import com.ead.course.domain.mapper.CourseMapper;
import com.ead.course.domain.models.dtos.CourseDto;
import com.ead.course.domain.models.forms.CourseForm;
import com.ead.course.domain.models.forms.CourseUpdateForm;
import com.ead.course.domain.services.CourseService;
import com.ead.course.domain.specifications.SpecificationTemplate;
import com.ead.course.validation.CourseValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseMapper mapper;

    @Autowired
    CourseValidator courseValidator;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseDto> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                         @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                         @RequestParam(required = false) UUID userId) {

        if (Objects.nonNull(userId)) {

            return courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable).map(mapper::toDto);

        }

        if (Objects.isNull(userId)) {

        }
            return courseService.findAll(spec, pageable).map(mapper::toDto);

    }


    @GetMapping("/{courseID}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto getOneCourse(@PathVariable(value = "courseID") UUID courseID) {

        return mapper.toDto(courseService.findById(courseID));


    }

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody CourseForm courseForm, Errors errors) {

        log.debug("POST saveCourse received {} ", courseForm);

        courseValidator.validate(courseForm, errors);

        if(errors.hasErrors()){

            return ResponseEntity.badRequest().body(errors.getAllErrors());

        }

        var courseModel = mapper.toEntity(courseForm);

        courseService.save(courseModel);

        log.debug("POST saveCourse saved courseId {} ", courseModel.getCourseId());
        log.info("Course save sucessfully courseId {}", courseModel.getCourseId());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(courseModel));

    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable(value = "courseId") UUID courseId) {

        log.debug("DELETE deleteCourse received courseId {} ", courseId);

        var course = courseService.findById(courseId);

        courseService.delete(course.getCourseId());

    }


    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                  @RequestBody @Valid CourseUpdateForm courseUpdateForm) {

        log.debug("PUT updateCourse received {} ", courseUpdateForm);

        var courseModel = courseService.updateCourse(courseId, courseUpdateForm);

        log.debug("PUT updateCourse update courseId {} ", courseModel.getCourseId());
        log.info("Course update sucessfully courseId {}", courseModel.getCourseId());

        return mapper.toDto(courseModel);


    }


}
