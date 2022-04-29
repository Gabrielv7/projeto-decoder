package com.ead.course.infrastructure.clients;

import com.ead.course.infrastructure.models.dto.ResponsePageDto;
import com.ead.course.infrastructure.models.dto.UserDto;
import com.ead.course.infrastructure.models.form.CourseUserForm;
import com.ead.course.infrastructure.service.UtilsService;
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
public class MsAuthUser {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.authuser}")
    String REQUEST_URI_MS_AUTHUSER;

    public Page<UserDto> getAllUsersByCourse(Pageable pageable,
                                             UUID courseId){

        List<UserDto> searchResult = null;

        String url = REQUEST_URI_MS_AUTHUSER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable);

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

    public UserDto getOneUserById (UUID userId) {

        String url = REQUEST_URI_MS_AUTHUSER + "/users/" + userId;

        log.debug("Request ms-authuser URL: {} ", url);
        log.info("Request ms-authuser URL: {} ", url);

        var result = restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);

        log.info("Response ms-authuser {} ", result.getStatusCode().value());

        return result.getBody();

    }


    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {

        String url = REQUEST_URI_MS_AUTHUSER + "/users/" + userId + "/courses/subscription";

        log.debug("Request ms-authuser URL: {} ", url);
        log.info("Request ms-authuser URL: {} ", url);

        var courseUserForm = new CourseUserForm();
        courseUserForm.setCourseId(courseId);
        courseUserForm.setUserId(userId);

        restTemplate.postForObject(url, courseUserForm, String.class);

    }

    public void deleteCourseInAuthUser(UUID courseId) {

       String url = REQUEST_URI_MS_AUTHUSER + "/users/courses/" + courseId;

        log.debug("Request ms-authuser URL: {} ", url);
        log.info("Request ms-authuser URL: {} ", url);

       restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

    }
}
