package com.ead.course.api.controllers;

import com.ead.course.domain.dtos.CourseDto;
import com.ead.course.domain.forms.CourseForm;
import com.ead.course.domain.services.CourseService;
import com.ead.course.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDto> getAllCourses(){

        return mapper.toCollectionDto(courseService.findAll());

    }

    @GetMapping("/{courseID}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto getOneCourse(@PathVariable(value = "courseID") UUID courseID){

        return mapper.toDto(courseService.findById(courseID));


    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto saveCourse(@RequestBody @Valid CourseForm courseForm){

        var courseModel = mapper.toEntity(courseForm);

        return mapper.toDto(courseService.save(courseModel));

    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value="courseId") UUID courseId){

        var course = courseService.findById(courseId);

        courseService.delete(course.getCourseId());

    }


    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto updateCourse(@PathVariable(value="courseId") UUID courseId,
                                  @RequestBody @Valid CourseForm courseForm){

        //Todo trocar a classe recebida no parametro para CourseUpdateForm sem o atributo userInstrutor
      return mapper.toDto(courseService.updateCourse(courseId, courseForm));


    }


}
