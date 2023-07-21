package com.ead.notification.dto.rabbit;

import java.util.UUID;

public record NotificationCommandRecordDto(String title, String message, UUID userId) {

}
