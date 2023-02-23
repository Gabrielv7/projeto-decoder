package com.ead.authuser.service;

import com.ead.authuser.domain.User;

import java.util.UUID;

public interface InstructorService {

    User saveUserTypeInstructor(UUID userId);

}
