package com.ead.authuser.sender;

import com.ead.authuser.domain.dto.rabbit.UserEventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventExchangeSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void sendToUserEventExchange(UserEventDto userEventDto){
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDto);
    }

}
