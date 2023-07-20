package com.ead.notification.service.impl;

import com.ead.notification.domain.Notification;
import com.ead.notification.domain.dto.rabbit.NotificationCommandRecordDto;
import com.ead.notification.domain.dto.request.NotificationRecordRequest;
import com.ead.notification.domain.enums.NotificationStatusEnum;
import com.ead.notification.exception.NotFoundException;
import com.ead.notification.repository.NotificationRepository;
import com.ead.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public Notification saveNotification(NotificationCommandRecordDto notificationCommandRecordDto) {
        Notification notification = new Notification();
        notification.setMessage(notificationCommandRecordDto.message());
        notification.setTitle(notificationCommandRecordDto.title());
        notification.setUserId( notificationCommandRecordDto.userId());
        return repository.save(notification);
    }

    @Override
    public Page<Notification> findAllNotificationsByUserId(UUID userId, Pageable pageable) {
       return repository.findAllByUserIdAndNotificationStatus(userId, NotificationStatusEnum.CREATED, pageable);
    }

    @Override
    @Transactional
    public Notification updateStatusNotification(UUID notificationId, UUID userId, NotificationRecordRequest notificationRecordRequest) {
        Notification notification = alreadyExistsNotification(notificationId, userId);
        notification.setNotificationStatus(notificationRecordRequest.notificationStatus());
        return notification;
    }

    private Notification alreadyExistsNotification(UUID noticationId, UUID userId) {
        return repository.findByNotificationIdAndUserId(noticationId, userId)
                .orElseThrow(()-> new NotFoundException(
                        messageSource.getMessage("notification.not.found", null, LocaleContextHolder.getLocale())
                ));
    }

}
