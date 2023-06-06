package com.ead.notification.service.impl;

import com.ead.notification.repository.NotificationRepository;
import com.ead.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

}
