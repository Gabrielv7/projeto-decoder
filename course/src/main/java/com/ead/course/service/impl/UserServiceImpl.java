package com.ead.course.service.impl;

import com.ead.course.domain.User;
import com.ead.course.exception.NotFoundException;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.UserRepository;
import com.ead.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<User> findAllUsersByCourseId(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteById(UUID userId) {
        courseRepository.deleteCourseUserByUserId(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException(
                        messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale())
                ));
    }

}
