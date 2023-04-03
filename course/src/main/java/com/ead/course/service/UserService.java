package com.ead.course.service;

import com.ead.course.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    Page<User> findAllUsersByCourseId(Specification<User> spec, Pageable pageable);

    User save(User user);
}
