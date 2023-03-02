package com.ead.authuser.repository;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

    boolean existsByUserAndCourseId(User user, UUID courseId);

    @Query(value = "select * from tb_user_course where user_id = :userId" ,nativeQuery = true)
    List<UserCourse> findAllUserIntoUserCourse(@Param("userId") UUID userId);

}
