package com.ead.course.domain.services.impl;

import com.ead.course.domain.models.UserModel;
import com.ead.course.domain.repositories.UserRepository;
import com.ead.course.domain.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {

        return userRepository.findAll(spec, pageable);

    }
}
