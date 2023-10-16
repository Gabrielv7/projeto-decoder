package com.ead.course.domain.assembler;

import com.ead.course.domain.Course;
import com.ead.course.domain.User;
import com.ead.course.messaging.dto.NotificationCommandDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationAssembler {

    private static final String MSG_BEM_VINDO = "Bem-Vindo(a) ao Curso: ";
    private static final String MSG_INSCRICAO_SUCESSO = " a sua inscrição foi realizada com sucesso!";

    public NotificationCommandDto assemblerNotificationCommandDto(Course course, User user) {

        return NotificationCommandDto.builder()
                .title(MSG_BEM_VINDO + course.getName())
                .message(user.getFullName() + MSG_INSCRICAO_SUCESSO)
                .userId(user.getUserId())
                .build();
    }

}
