package com.ead.course.validation;

import com.ead.course.domain.models.UserModel;
import com.ead.course.domain.models.forms.CourseForm;
import com.ead.course.domain.services.UserService;
import com.ead.course.infrastructure.models.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

        CourseForm courseForm = (CourseForm) o;

        validator.validate(courseForm, errors);

        if(!errors.hasErrors()){

            validateUserInstructor(courseForm.getUserInstructor(), errors);

        }

    }

    private void validateUserInstructor(UUID userInstructor, Errors errors){

        Optional<UserModel> userModelOptional = userService.findByIdOpt(userInstructor);

        if(!userModelOptional.isPresent()){

            errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found!");

        }

        userModelOptional.ifPresent( userModel -> {

            if(userModel.getUserType().equals(UserType.STUDENT.toString())){

                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN");

            }

        });

    }
}
