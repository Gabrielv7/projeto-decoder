package com.ead.course.infrastructure.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    String createUrl(UUID courseId, Pageable pageable);

}
