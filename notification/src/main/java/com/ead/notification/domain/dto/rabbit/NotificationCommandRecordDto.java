package com.ead.notification.domain.dto.rabbit;

import java.util.UUID;

public record NotificationCommandRecordDto(String title, String message, UUID userId) {

}
