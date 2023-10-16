package com.ead.authuser.messaging.publisher;

import com.ead.authuser.messaging.dto.UserEventDto;
import com.ead.authuser.util.ConstantsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class UserEventExchangeSender {

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void sendToUserEventExchange(UserEventDto userEventDto){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_USER_ID,
                "sendToUserEventExchange", ConstantsLog.LOG_EVENT_INFO, "Sending user to exchange", userEventDto.getUserId());

        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDto);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_USER_ID,
                "sendToUserEventExchange", ConstantsLog.LOG_EVENT_INFO, "Send user successfully for exchange", userEventDto.getUserId());

    }

}
