package com.ead.course.repository;

import com.ead.course.domain.Course;
import com.ead.course.domain.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    boolean existsByCourseAndUserId(Course course, UUID userId);

    @Query(value ="select * from tb_course_user where course_id = :courseId" , nativeQuery = true)
    List<CourseUser> findAllCourseIntoCourseUser(@Param("courseId") UUID courseId);

    boolean existsByUserId(UUID userId);

    void deleteAllByUserId(UUID userId);

}
