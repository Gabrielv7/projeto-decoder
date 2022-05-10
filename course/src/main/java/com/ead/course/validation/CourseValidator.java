package com.ead.course.validation;

import com.ead.course.domain.models.forms.CourseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

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
    /*
        try {

            var responseUser = msAuthUser.getOneUserById(userInstructor);

            if(responseUser.getUserType().equals(UserType.STUDENT)){

                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN");

            }

        } catch (HttpStatusCodeException e) {

            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){

                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found!");

            }
        }
*/
    }
}
