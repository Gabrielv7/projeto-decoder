package com.ead.notification.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandadError {

    private String timestamp;
    private String path;
    private int status;
    private String error;
    private String message;

}
