package com.ead.course.domain.assembler;

import com.ead.course.domain.dto.request.UserCourseRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserCourseAssembler {

    public UserCourseRequest assemblerUserCourseToRequestMsAuthUser(UUID courseId){
        return UserCourseRequest.builder()
                .courseId(courseId)
                .build();
    }

}
