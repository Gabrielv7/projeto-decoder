package com.ead.course.validator;

import com.ead.course.domain.Lesson;
import com.ead.course.domain.dto.request.LessonRequest;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LessonValidator {

    @Autowired
    private LessonRepository repository;

    @Autowired
    private MessageSource messageSource;

    public void validTitleAndDescriptionAlreadyExists(Lesson lesson){
        if(repository.existsByTitle(lesson.getTitle()) && repository.existsByDescription(lesson.getDescription())){
            throw new BusinessException(messageSource.getMessage("lesson-already-exists", null, LocaleContextHolder.getLocale()));
        }
    }

    public void validTitleAndDescriptionAlreadyExists(LessonRequest lessonRequest){
        if(repository.existsByTitle(lessonRequest.getTitle()) && repository.existsByDescription(lessonRequest.getDescription())){
            throw new BusinessException(messageSource.getMessage("lesson-already-exists", null, LocaleContextHolder.getLocale()));
        }
    }

}
