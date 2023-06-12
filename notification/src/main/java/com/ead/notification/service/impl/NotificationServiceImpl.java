package com.ead.notification.service.impl;

import com.ead.notification.domain.Notification;
import com.ead.notification.repository.NotificationRepository;
import com.ead.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    @Transactional
    public Notification saveNotification(Notification notification) {
        return repository.save(notification);
    }

}
