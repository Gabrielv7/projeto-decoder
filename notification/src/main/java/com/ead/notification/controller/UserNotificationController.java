package com.ead.notification.controller;

import com.ead.notification.mapper.NotificationMapper;
import com.ead.notification.domain.model.Notification;
import com.ead.notification.domain.dto.request.NotificationRequest;
import com.ead.notification.domain.dto.response.NotificationResponse;
import com.ead.notification.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/notifications")
public class UserNotificationController {

    Logger log = LogManager.getLogger(UserNotificationController.class.getName());

    private final NotificationService notificationService;
    private final NotificationMapper mapper;

    public UserNotificationController(NotificationService notificationService, NotificationMapper mapper) {
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/{userId}")
    public ResponseEntity<Page<NotificationResponse>> getAllNotificationsByUserId(@PathVariable(value = "userId") UUID userId,
                                                                                  @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Notification> notifications = notificationService.findAllNotificationsByUserId(userId, pageable);
        return ResponseEntity.ok(notifications.map(mapper::toResponse));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/{notificationId}/users/{userId}")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable(value = "notificationId") UUID notificationId,
                                                                   @PathVariable(value = "userId") UUID userId,
                                                                   @RequestBody @Valid NotificationRequest notificationRequest) {

        Notification notificationUpdated = notificationService.updateStatusNotification(notificationId, userId, notificationRequest);
        return ResponseEntity.ok(mapper.toResponse(notificationUpdated));
    }

}
