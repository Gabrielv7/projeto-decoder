package com.ead.authuser.service.impl;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.enums.UserType;
import com.ead.authuser.service.InstructorService;
import com.ead.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public User saveUserTypeInstructor(UUID userId) {
        User user = userService.findById(userId);
        user.setUserType(UserType.INSTRUCTOR);
        return userService.save(user);
    }
}
