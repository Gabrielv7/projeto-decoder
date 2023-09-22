package com.ead.notification.configuration.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    Logger log = LogManager.getLogger(AuthenticationEntryPointImpl.class.getName());

    private static final String LOG_UNAUTHORIZED_ERROR = "Unauthorized error: {}";
    private static final String MSG_UNAUTHORIZED_ERROR = "Unauthorized";

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        log.warn(LOG_UNAUTHORIZED_ERROR, e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, MSG_UNAUTHORIZED_ERROR);
    }

}
