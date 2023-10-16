package com.ead.course.messaging.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class NotificationCommandDto {

    private String title;
    private String message;
    private UUID userId;

}
