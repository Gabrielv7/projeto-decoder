package com.ead.notification.messaging.dto;

import java.util.UUID;

public record NotificationCommandDto(String title, String message, UUID userId) {

}
