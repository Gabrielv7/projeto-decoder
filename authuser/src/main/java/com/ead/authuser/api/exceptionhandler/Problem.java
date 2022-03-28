package com.ead.authuser.api.exceptionhandler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Problem {

    private String message;

    private Integer status;

}
