package com.ead.authuser.infrastructure.clients;

import com.ead.authuser.infrastructure.domain.model.dtos.CourseDto;
import com.ead.authuser.infrastructure.domain.model.dtos.ResponsePageDto;
import com.ead.authuser.infrastructure.service.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class MsCourse {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.course}")
    String REQUEST_URL_MS_COURSE;

    public Page<CourseDto> getAllCoursesByUser(Pageable pageable,
                                               UUID userId){

        List<CourseDto> searchResult = null;

        String url = REQUEST_URL_MS_COURSE + utilsService.createUrl(userId, pageable);

        log.debug("Request ms-course URL: {} ", url);
        log.info("Request ms-course URL: {} ", url);

        try {

            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
            ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

            log.info("Response ms-course {} ", result.getStatusCode());

            log.debug("Response ms-course Number of Elements: {} ", searchResult.size());

        }catch (HttpStatusCodeException e){

            log.error("Error request /ms-course {} ", e);

        }

        log.info("Ending request/ ms-course {} ", userId);

        return new PageImpl<>(searchResult);

    }


}
