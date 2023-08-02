package com.ead.notification.dto.response;

import com.ead.notification.exception.FieldError;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record ErrorResponseCustomized(int code,
                                      String message,
                                      List<FieldError> erros) {
}
