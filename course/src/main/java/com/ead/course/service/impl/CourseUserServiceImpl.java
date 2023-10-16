package com.ead.course.service.impl;

import com.ead.course.domain.Course;
import com.ead.course.domain.User;
import com.ead.course.domain.assembler.NotificationAssembler;
import com.ead.course.messaging.dto.NotificationCommandDto;
import com.ead.course.messaging.publisher.NotificationCommandExchangeSender;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
import com.ead.course.service.UserService;
import com.ead.course.validator.CourseUserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseService courseService;
    private final CourseUserValidator courseUserValidator;
    private final CourseUserRepository courseUserRepository;
    private final UserService userService;
    private final NotificationAssembler notificationAssembler;
    private final NotificationCommandExchangeSender notificationCommandExchangeSender;

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        Course course = courseService.findById(courseId);
        User user = userService.findById(userId);
        courseUserValidator.validateSubscription(course, user);
        courseUserRepository.saveCourseUser(course.getCourseId(), user.getUserId());
        try {
            NotificationCommandDto notificationCommandDto = notificationAssembler.assemblerNotificationCommandDto(course, user);
            notificationCommandExchangeSender.sendNotificationCommandExchange(notificationCommandDto);
        } catch (Exception ex) {
            log.warn("Error sending Exchange");
        }

    }
}
