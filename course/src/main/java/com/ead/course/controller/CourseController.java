package com.ead.course.controller;

import com.ead.course.domain.Course;
import com.ead.course.domain.dto.request.CourseRequest;
import com.ead.course.domain.dto.response.CourseResponse;
import com.ead.course.mapper.CourseMapper;
import com.ead.course.service.CourseService;
import com.ead.course.specification.SpecificationTemplate;
import com.ead.course.util.ConstantsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;
    private final CourseMapper mapper;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<CourseResponse> saveCourse(@RequestBody @Valid CourseRequest courseRequest) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY,
                "saveCourse", "POST", "Saving course", courseRequest);

        Course courseSaved = service.save(mapper.toEntity(courseRequest));

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_COURSE_ID,
                "saveCourse", ConstantsLog.LOG_EVENT_INFO, "Course saved", ConstantsLog.LOG_HTTP_CODE_CREATED, courseSaved.getCourseId());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(courseSaved));
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable(value = "courseId") UUID courseId) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID,
                "deleteCourse", "DELETE", "Deleting course", courseId);

        service.deleteById(courseId);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_COURSE_ID,
                "deleteCourse", ConstantsLog.LOG_EVENT_INFO, "Course deleted", ConstantsLog.LOG_HTTP_CODE_NO_CONTENT, courseId);

    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                                       @RequestBody @Valid CourseRequest courseRequest) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID + ConstantsLog.LOG_ENTITY,
                "updateCourse", "PUT", "Updating course", courseId, courseRequest);

        Course courseUpdated = service.update(courseId, mapper.toEntity(courseRequest));

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_COURSE_ID,
                "updateCourse", ConstantsLog.LOG_EVENT_INFO, "Course updated", ConstantsLog.LOG_HTTP_CODE_OK, courseUpdated.getCourseId());

        return ResponseEntity.ok(mapper.toResponse(courseUpdated));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public ResponseEntity<Page<CourseResponse>> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                                              @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                              @RequestParam(required = false) UUID userId) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE,
                "getAllCourses", "GET", "Searching a list of courses");

        Page<Course> courses  = service.decideWhichSpecToCall(userId, spec, pageable);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_SIZE,
                "getAllCourses", ConstantsLog.LOG_EVENT_INFO, "Courses found", ConstantsLog.LOG_HTTP_CODE_OK, courses.getTotalElements());

        return ResponseEntity.ok(mapper.convertToPageCourseResponse(courses));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID,
                "getOneCourse", "GET", "Searching one course", courseId);

        Course courseFind = service.findById(courseId);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_COURSE_ID,
                "getOneCourse", ConstantsLog.LOG_EVENT_INFO, "Course found", ConstantsLog.LOG_HTTP_CODE_OK ,courseId);

        return ResponseEntity.ok(mapper.toResponse(courseFind));
    }

}
