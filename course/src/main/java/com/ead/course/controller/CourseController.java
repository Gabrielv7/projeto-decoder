package com.ead.course.controller;

import com.ead.course.domain.Course;
import com.ead.course.domain.dto.request.CourseRequest;
import com.ead.course.domain.dto.response.CourseResponse;
import com.ead.course.mapper.CourseMapper;
import com.ead.course.service.CourseService;
import com.ead.course.specification.SpecificationTemplate;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    private CourseService service;

    @Autowired
    private CourseMapper mapper;

    @PostMapping
    public ResponseEntity<CourseResponse> saveCourse(@RequestBody @Valid CourseRequest courseRequest) {
        Course courseSaved = service.save(mapper.toEntity(courseRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(courseSaved));
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "courseId") UUID courseId) {
        service.delete(courseId);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                                       @RequestBody @Valid CourseRequest courseRequest){

        Course courseUpdated = service.update(courseId, courseRequest);
        return ResponseEntity.ok(mapper.toResponse(courseUpdated));
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                                              @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable){
        Page<CourseResponse> courses = service.findAll(spec, pageable).map(c -> mapper.toResponse(c));
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getOneCourse(@PathVariable(value = "courseId") UUID courseId){
        Course courseFind = service.findById(courseId);
        return ResponseEntity.ok(mapper.toResponse(courseFind));
    }

}
