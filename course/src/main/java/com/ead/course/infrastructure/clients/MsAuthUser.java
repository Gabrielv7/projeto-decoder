package com.ead.course.infrastructure.clients;

import com.ead.course.infrastructure.models.dto.ResponsePageDto;
import com.ead.course.infrastructure.models.dto.UserDto;
import com.ead.course.infrastructure.service.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class MsAuthUser {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    String REQUEST_URI = "http://localhost:8087";

    public Page<UserDto> getAllUsersByCourse(Pageable pageable,
                                             UUID courseId){

        List<UserDto> searchResult = null;

        String url = utilsService.createUrl(courseId, pageable);

        log.debug("Request ms-authuser URL: {} ", url);
        log.info("Request ms-authuser URL: {} ", url);

        try {

            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {};
            ResponseEntity<ResponsePageDto<UserDto>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

            log.info("Response ms-authuser {} ", result.getStatusCode());

            log.debug("Response ms-authuser Number of Elements: {} ", searchResult.size());

        }catch (HttpStatusCodeException e){

            log.error("Error request /ms-authuser {} ", e);

        }

        log.info("Ending request/ ms-authuser courseId {} ", courseId);

        return new PageImpl<>(searchResult);

    }



}
