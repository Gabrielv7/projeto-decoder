package com.ead.notification.service.impl;

import com.ead.notification.model.Notification;
import com.ead.notification.dto.rabbit.NotificationCommandDto;
import com.ead.notification.dto.request.NotificationRequest;
import com.ead.notification.model.enums.NotificationStatusEnum;
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
    public Notification saveNotification(NotificationCommandDto notificationCommandDto) {
        Notification notification = new Notification(notificationCommandDto.message(), notificationCommandDto.title(),
                                                        notificationCommandDto.userId());
        return repository.save(notification);
    }

    @Override
    public Page<Notification> findAllNotificationsByUserId(UUID userId, Pageable pageable) {
       return repository.findAllByUserIdAndNotificationStatus(userId, NotificationStatusEnum.CREATED, pageable);
    }

    @Override
    @Transactional
    public Notification updateStatusNotification(UUID notificationId, UUID userId, NotificationRequest notificationRequest) {
        Notification notification = alreadyExistsNotification(notificationId, userId);
        notification.setNotificationStatus(NotificationStatusEnum.valueOf(notificationRequest.notificationStatus()));
        return notification;
    }

    private Notification alreadyExistsNotification(UUID noticationId, UUID userId) {
        return repository.findByNotificationIdAndUserId(noticationId, userId)
                .orElseThrow(()-> new NotFoundException(
                        messageSource.getMessage("notification.not.found", null, LocaleContextHolder.getLocale())
                ));
    }

}
