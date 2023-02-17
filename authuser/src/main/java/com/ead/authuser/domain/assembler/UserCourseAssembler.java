package com.ead.authuser.domain.assembler;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.UserCourse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserCourseAssembler {

    public UserCourse assemblerUserCourseBeforeSave(User user, UUID courseId){
    return UserCourse
                .builder()
                .user(user)
                .courseId(courseId)
                .build();
    }

}
