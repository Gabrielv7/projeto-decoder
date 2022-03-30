package com.ead.course.api.exceptionHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Problem {

    private String message;

    private Integer status;

    private LocalDateTime timesTamp;

}
