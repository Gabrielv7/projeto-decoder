package com.ead.authuser.domain.services.impl;

import com.ead.authuser.common.exception.SubscriptionExistsException;
import com.ead.authuser.common.exception.UserCourseNotFoundException;
import com.ead.authuser.domain.model.UserCourseModel;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.repositories.UserCourseRepository;
import com.ead.authuser.domain.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    UserCourseRepository userCourseRepository;

    @Override
    public void existsByUserAndCourseId(UserModel userModel, UUID courseId) {

        var verify =  userCourseRepository.existsByUserAndCourseId(userModel, courseId);

        if(verify) {

            throw new SubscriptionExistsException("Subscription already exists!");

        }

    }

    @Override
    public UserCourseModel save(UserCourseModel userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }

    @Override
    public void existsByCourseId(UUID courseId) {

        if(!userCourseRepository.existsByCourseId(courseId)){

            throw new UserCourseNotFoundException("UserCourse not found.");

        }

    }

    @Transactional
    @Override
    public void deleteUserCourseByCourse(UUID courseId) {

        userCourseRepository.deleteAllByCourseId(courseId);

    }
}
