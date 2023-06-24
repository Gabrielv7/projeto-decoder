package com.ead.notification.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ErrorObject {

    private  String message;

    private  String field;

}
