package com.ead.authuser.domain.services.impl;

import com.ead.authuser.common.exception.EmailExistsException;
import com.ead.authuser.common.exception.PasswordInvalidException;
import com.ead.authuser.common.exception.UserNameExistsException;
import com.ead.authuser.common.exception.UserNotFoundException;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.model.enums.UserStatus;
import com.ead.authuser.domain.model.enums.UserType;
import com.ead.authuser.domain.model.forms.UserUpdateForm;
import com.ead.authuser.domain.model.forms.UserUpdateImageForm;
import com.ead.authuser.domain.repositories.UserCourseRepository;
import com.ead.authuser.domain.repositories.UserRepository;
import com.ead.authuser.domain.services.UserService;
import com.ead.authuser.infrastructure.clients.MsCourse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCourseRepository userCourseRepository;

    @Autowired
    MsCourse msCourse;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel findById(UUID userId) {

        return userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Transactional
    @Override
    public void delete(UUID userId) {

        // verifica se o usuário existe
        var user = this.findById(userId);

        // verifica se tem UserCourse vinculados a um user
        var userCourseModelList = userCourseRepository.findAllUserCourseIntoUser(userId);

        // variavel de controle para definir se precisa deletar essa relação no ms course
        boolean deleteUserCourseInCourse = false;

        // se a lista não estiver vazia
        if(!userCourseModelList.isEmpty()){

            // deleta os UsersCourse vinculados a um user
            userCourseRepository.deleteAll(userCourseModelList);

            // necessário deletar essa relação no ms course
            deleteUserCourseInCourse = true;

        }

        // deleta o user
        userRepository.deleteById(user.getUserId());

        if(deleteUserCourseInCourse){
            // deleta a relação no lado de ms course
            msCourse.deleteUserInCourse(userId);

        }

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
    public UserModel updateUser(UserUpdateForm userUpdateForm, UUID userId) {

        var userModel = this.findById(userId);

        userModel.setFullName(userUpdateForm.getFullName());
        userModel.setPhoneNumber(userUpdateForm.getPhoneNumber());

        return userRepository.save(userModel);

    }

    @Transactional
    @Override
    public UserModel updateImageUser(UserUpdateImageForm userUpdateImageForm, UUID userId) {

        var userModel = this.findById(userId);

        userModel.setImageUrl(userUpdateImageForm.getImageUrl());

        return userRepository.save(userModel);
    }

    @Transactional
    @Override
    public UserModel updatePassword(UUID userId, String oldPassword, String password) {

        var userModel = this.findById(userId);

        if (!oldPassword.equals(userModel.getPassword())) {

            log.warn("Mismatched old password {} ", userModel.getPassword());

            throw new PasswordInvalidException("Mismatched old password!");

        }

        userModel.setPassword(password);

        return userRepository.save(userModel);

    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public UserModel saveSubscriptionInstructor(UserModel userModel) {

        userModel.setUserType(UserType.INSTRUCTOR);

        return userRepository.save(userModel);

    }

}
