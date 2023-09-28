package com.ead.authuser.configuration.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationCurrentUserService {

    public UserDetailsImpl getCurrentUser() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void validCurrentUserIdIsEqualsPathUserId(UUID userId, UUID currentUserId) {
        if (!currentUserId.equals(userId)) {
            String forbidden = "Forbidden";
            throw new AccessDeniedException(forbidden);

        }
    }

}
