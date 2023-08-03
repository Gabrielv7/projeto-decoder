package com.ead.authuser.controller;

import com.ead.authuser.configuration.security.JwtProvider;
import com.ead.authuser.domain.User;
import com.ead.authuser.domain.dto.request.LoginRequest;
import com.ead.authuser.domain.dto.request.UserRequest;
import com.ead.authuser.domain.dto.response.JwtResponse;
import com.ead.authuser.domain.dto.response.UserResponse;
import com.ead.authuser.mapper.UserMapper;
import com.ead.authuser.service.UserService;
import com.ead.authuser.util.ConstantsLog;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final String BEARER = "Bearer";

    private final UserService service;
    private final UserMapper mapper;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Validated(UserRequest.UserView.RegistrationPost.class)
                                                     @JsonView(UserRequest.UserView.RegistrationPost.class) UserRequest userRequest) {

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_ENTITY,
                "registerUser", "POST", "Saving user", userRequest);

        User userSaved = service.save(userRequest);

        log.info(ConstantsLog.LOG_METHOD + ConstantsLog.LOG_EVENT + ConstantsLog.LOG_MESSAGE + ConstantsLog.LOG_HTTP_CODE + ConstantsLog.LOG_ENTITY_ID,
                "registerUser", ConstantsLog.LOG_EVENT_INFO, "User saved", ConstantsLog.LOG_HTTP_CODE_CREATED, userSaved.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(userSaved));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticationUser(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwt(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt, BEARER));
    }

}
