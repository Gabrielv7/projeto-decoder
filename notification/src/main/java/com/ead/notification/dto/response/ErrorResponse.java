package com.ead.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record ErrorResponse(int code,
                            String message) {

}
