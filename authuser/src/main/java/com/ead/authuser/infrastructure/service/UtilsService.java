package com.ead.authuser.infrastructure.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

   String createUrl(UUID userId, Pageable pageable);

}
