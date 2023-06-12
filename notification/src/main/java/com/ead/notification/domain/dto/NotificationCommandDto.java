package com.ead.notification.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NotificationCommandDto {

    private String title;
    private String message;
    private UUID userId;

}
