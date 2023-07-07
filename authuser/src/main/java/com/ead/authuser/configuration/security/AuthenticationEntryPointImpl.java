package com.ead.authuser.configuration.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private static final String LOG_UNAUTHORIZED_ERROR = "Unauthorized error: {}";
    private static final String MSG_UNAUTHORIZED_ERROR = "Unauthorized";

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        log.warn(LOG_UNAUTHORIZED_ERROR, e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, MSG_UNAUTHORIZED_ERROR);
    }

}
