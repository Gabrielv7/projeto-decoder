package com.ead.authuser.domain.services.impl;

import com.ead.authuser.common.exception.EmailExistsException;
import com.ead.authuser.common.exception.PasswordInvalidException;
import com.ead.authuser.common.exception.UserNameExistsException;
import com.ead.authuser.common.exception.UserNotFoundException;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.model.enums.ActionType;
import com.ead.authuser.domain.model.enums.UserStatus;
import com.ead.authuser.domain.model.enums.UserType;
import com.ead.authuser.domain.repositories.UserRepository;
import com.ead.authuser.domain.services.UserService;
import com.ead.authuser.infrastructure.clients.MsCourse;
import com.ead.authuser.publishers.UserEventPublisher;
import com.google.common.base.MoreObjects;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MsCourse msCourse;

    @Autowired
    UserEventPublisher userEventPublisher;

    @Override
    public UserModel findById(UUID userId) {

        return userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Transactional
    @Override
    public void delete(UUID userId) {

        var user = this.findById(userId);

        userRepository.deleteById(user.getUserId());

    }

    @Transactional
    @Override
    public UserModel save(UserModel userModel) {

        // verifica se o user name existe
        if (userRepository.existsByUserName(userModel.getUserName())) {

            log.warn("Username {} is Already Taken", userModel.getUserName());

            throw new UserNameExistsException("Error username is Already Taken!");

        }

        // verifica se o email existe
        if (userRepository.existsByEmail(userModel.getEmail())) {

            log.warn("Email {} is Already Taken", userModel.getEmail());

            throw new EmailExistsException("Error email is Already Taken!");

        }

        //define o status ativo
        userModel.setUserStatus(UserStatus.ACTIVE);

        //define o tipo de usuário estudante
        userModel.setUserType(UserType.STUDENT);

        return userRepository.save(userModel);

    }

    @Transactional
    @Override
    public UserModel update(UUID userId, UserModel userModel) {

        var oldUser = this.findById(userId);

        oldUser.setUserId(userId);
        oldUser.setUserName(MoreObjects.firstNonNull(userModel.getUserName(), oldUser.getUserName()));
        oldUser.setEmail(MoreObjects.firstNonNull(userModel.getEmail(), oldUser.getEmail()));
        oldUser.setPassword(MoreObjects.firstNonNull(userModel.getPassword(), oldUser.getPassword()));
        oldUser.setFullName(MoreObjects.firstNonNull(userModel.getFullName(), oldUser.getFullName()));
        oldUser.setUserStatus(MoreObjects.firstNonNull(userModel.getUserStatus(), oldUser.getUserStatus()));
        oldUser.setUserType(MoreObjects.firstNonNull(userModel.getUserType(), oldUser.getUserType()));
        oldUser.setPhoneNumber(MoreObjects.firstNonNull(userModel.getPhoneNumber(), oldUser.getPhoneNumber()));
        oldUser.setCpf(MoreObjects.firstNonNull(userModel.getCpf(), oldUser.getCpf()));
        oldUser.setImageUrl(MoreObjects.firstNonNull(userModel.getImageUrl(), oldUser.getImageUrl()));

        return oldUser;

    }

    @Transactional
    @Override
    public UserModel updatePassword(UUID userId, String oldPassword, String password) {

        var oldUser = this.findById(userId);

        if (!oldPassword.equals(oldUser.getPassword())) {

            log.warn("Mismatched old password {} ", oldUser.getPassword());

            throw new PasswordInvalidException("Mismatched old password!");

        }

        oldUser.setUserId(userId);
        oldUser.setPassword(password);

        return oldUser;

    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public UserModel saveSubscriptionInstructor(UserModel userModel) {

        userModel.setUserType(UserType.INSTRUCTOR);

        return this.updateUser(userModel.getUserId(), userModel);

    }

    @Transactional
    @Override
    public UserModel saveUser(UserModel userModel) {

       var user = this.save(userModel);

       userEventPublisher.publishUserEvent(user.converterToUserEventDto(), ActionType.CREATED);

       return user;


    }

    @Transactional
    @Override
    public void deleteUser(UserModel userModel) {

        this.delete(userModel.getUserId());

        userEventPublisher.publishUserEvent(userModel.converterToUserEventDto(), ActionType.DELETE);
    }

    @Transactional
    @Override
    public UserModel updateUser(UUID userId, UserModel userModel) {

        var userUpdate = update(userId, userModel);

        userEventPublisher.publishUserEvent(userUpdate.converterToUserEventDto(), ActionType.UPDATE);

        return userUpdate;

    }


}
