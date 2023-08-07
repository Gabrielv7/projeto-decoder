package com.ead.notification.consumer;

import com.ead.notification.dto.rabbit.NotificationCommandDto;
import com.ead.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationCommandQueueConsumer {

    private final NotificationService notificationService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${ead.broker.queue.notificationCommandQueue}",
                    durable = "true"
            ),
            exchange = @Exchange(
                value = "${ead.broker.exchange.notificationCommandExchange}",
                type = ExchangeTypes.TOPIC,
                ignoreDeclarationExceptions = "true"),
            key = "${ead.broker.key.notificationCommandKey}"
    ))
    public void receiveNotificationCommandMessage(@Payload NotificationCommandDto notificationCommandDto) {
        this.doProcess(notificationCommandDto);
    }

    public void doProcess(NotificationCommandDto notificationCommandDto) {
        notificationService.saveNotification(notificationCommandDto);
    }

}
