package com.ead.course.controller;

import com.ead.course.domain.dto.request.SubscriptionRequest;
import com.ead.course.util.ConstantsLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(@PathVariable(value = "courseId") UUID courseId,
                                                                  @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID,
                "getAllUsersByCourse", "GET", "Searching a list of users by course", courseId);
        //todo: voltar para comtemplar state transfer param
     //   Page<UserResponse> usersResponse = authUserClientService.getAllUsersByCourse(courseId, pageable);
        return ResponseEntity.ok("");
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                                             @RequestBody @Valid SubscriptionRequest subscriptionRequest){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_COURSE_ID + ConstantsLog.LOG_ENTITY,
                "createSubscriptionUserInCourse", "POST", "Saving subscription user in course", courseId, subscriptionRequest);
        //todo: voltar para comtemplar state transfer param
        //User userSaved = courseUserService.saveSubscriptionUserInCourse(courseId, subscriptionRequest.getUserId());
//
//        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_ENTITY_ID,
//                "saveSubscriptionUserInCourse", ConstantsLog.LOG_EVENT_INFO, "Saved subscription user in course", ConstantsLog.LOG_HTTP_CODE_CREATED, userSaved.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

}
