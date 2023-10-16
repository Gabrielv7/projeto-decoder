package com.ead.authuser.service;

import com.ead.authuser.domain.model.User;

import java.util.UUID;

public interface InstructorService {

    User updateUserForTypeInstructor(UUID userId);

}
