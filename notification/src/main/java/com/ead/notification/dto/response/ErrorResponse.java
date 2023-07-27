package com.ead.notification.dto.response;

import com.ead.notification.exception.ErrorObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int code;

    private String message;

    private List<ErrorObject> errors;

}
