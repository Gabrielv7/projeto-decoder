package com.ead.course.domain.services.impl;

import com.ead.course.common.exceptions.UserIsBlockedException;
import com.ead.course.common.exceptions.UserNotFoundException;
import com.ead.course.domain.models.UserModel;
import com.ead.course.domain.repositories.CourseRepository;
import com.ead.course.domain.repositories.UserRepository;
import com.ead.course.domain.services.UserService;
import com.ead.course.infrastructure.models.enums.UserStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {

        return userRepository.findAll(spec, pageable);

    }

    @Transactional
    @Override
    public UserModel save(UserModel userModel) {

        return userRepository.save(userModel);

    }

    @Transactional
    @Override
    public void delete(UUID userId) {

        // deleta a relação de um usuário com um curso
        courseRepository.deleteCourseUserByUser(userId);

        // deleta o usuário
        userRepository.deleteById(userId);

    }

    @Override
    public UserModel findById(UUID userInstructor) {

        return userRepository.findById(userInstructor)
                .orElseThrow(()-> new UserNotFoundException("User not found."));

    }

    @Override
    public Optional<UserModel> findByIdOpt(UUID userInstructor) {

        return userRepository.findById(userInstructor);

    }

    @Override
    public UserModel verifyUserIsBlocked(UserModel userModel) {

        if(userModel.getUserType().equals(UserStatus.BLOCKED.toString())){

            throw new UserIsBlockedException("User is blocked.");

        }

        return userModel;

    }

}
