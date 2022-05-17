package com.ead.course.consumers;

import com.ead.course.domain.models.dtos.UserEventDto;
import com.ead.course.domain.models.enums.ActionType;
import com.ead.course.domain.services.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @Autowired
    UserService userService;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEventeExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void listenUserEvente(@Payload UserEventDto userEventDto){

        var userModel = userEventDto.convertToUserModel();

        switch (ActionType.valueOf(userEventDto.getActionType())){

            case CREATED:
                userService.save(userModel);
                break;

            case UPDATE:
                userService.save(userModel);
                break;

            case DELETE:
                userService.delete(userModel.getUserId());
                break;

        }


    }

}
