package com.ead.authuser.service;

import com.ead.authuser.model.User;

import java.util.UUID;

public interface InstructorService {

    User updateUserForTypeInstructor(UUID userId);

}
