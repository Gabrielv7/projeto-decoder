package com.ead.notification.controller;

import com.ead.notification.domain.Notification;
import com.ead.notification.domain.dto.request.NotificationUpdateRequest;
import com.ead.notification.domain.dto.response.NotificationResponse;
import com.ead.notification.mapper.NotificationMapper;
import com.ead.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/notifications")
public class UserNotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper mapper;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<NotificationResponse>> getAllNotificationsByUserId(@PathVariable(value = "userId") UUID userId,
                                                                                  @PageableDefault(page = 0, size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Notification> notifications = notificationService.findAllNotificationsByUserId(userId, pageable);
        return ResponseEntity.ok(mapper.convertToPageNotificationsResponse(notifications));
    }

    @PutMapping("/{notificationId}/users/{userId}")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable(value = "notificationId") UUID notificationId,
                                                                   @PathVariable(value = "userId") UUID userId,
                                                                   @RequestBody @Valid NotificationUpdateRequest notificationUpdateRequest) {

        Notification notificationUpdated = notificationService.updateStatusNotification(notificationId, userId, notificationUpdateRequest);
        return ResponseEntity.ok(mapper.toResponse(notificationUpdated));
    }

}