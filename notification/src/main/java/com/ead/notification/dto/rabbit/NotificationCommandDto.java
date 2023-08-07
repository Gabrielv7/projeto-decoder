package com.ead.notification.dto.rabbit;

import java.util.UUID;

public record NotificationCommandDto(String title, String message, UUID userId) {

}
