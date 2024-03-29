package com.ead.course.messaging.publisher;

import com.ead.course.messaging.dto.NotificationCommandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationCommandExchangeSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${ead.broker.exchange.notificationCommandExchange}")
    private String notificationCommandExchange;

    @Value("${ead.broker.key.notificationCommandKey}")
    private String notificationCommandKey;

    public void sendNotificationCommandExchange(NotificationCommandDto notificationCommandDto){
        rabbitTemplate.convertAndSend(notificationCommandExchange, notificationCommandKey, notificationCommandDto);
    }

}
