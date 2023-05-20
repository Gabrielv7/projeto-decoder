package com.ead.course.consumer;

import com.ead.course.domain.User;
import com.ead.course.domain.dto.rabbit.UserEventDto;
import com.ead.course.domain.enums.ActionTypeEnum;
import com.ead.course.exception.BusinessException;
import com.ead.course.mapper.UserMapper;
import com.ead.course.service.UserService;
import com.ead.course.util.ConstantsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class UserEventQueueConsumer {

    private final UserService userService;

    private final UserMapper mapper;

    private final MessageSource messageSource;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEvent}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEvent}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void receiveUserEventMessage(@Payload UserEventDto userEventDto){

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY,
                "receiveUserEventMessage", ConstantsLog.LOG_EVENT_INFO, "Receiving message", userEventDto);

        this.doProcess(userEventDto, ActionTypeEnum.valueOf(userEventDto.getActionType()));

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE,
                "receiveUserEventMessage", ConstantsLog.LOG_EVENT_INFO, "successfully processed message");
    }

    public void doProcess(UserEventDto userEventDto, ActionTypeEnum actionTypeEnum){

        User user = mapper.toEntity(userEventDto);

        switch (actionTypeEnum) {
          case CREATE, UPDATE -> userService.save(user);
          case DELETE -> userService.deleteById(user.getUserId());
          default -> throw new BusinessException(messageSource.getMessage("event-not-found", null, LocaleContextHolder.getLocale()));
        }
    }

}
