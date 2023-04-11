package com.ead.course.repository;

import com.ead.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {

    boolean existsByName(String name);

    boolean existsByDescription(String description);

    //Retorna true se existir mais de 1 registro com esses Ids
    @Query(value = "select case when count (tcu) > 0 THEN true ELSE false END FROM tb_courses_users tcu where tcu.course_id = :courseId and tcu.user_id = :userId", nativeQuery = true)
    boolean hasExistsSubscription(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(value = "insert into tb_courses_users values (:courseId, :userId)", nativeQuery = true)
    void saveCourseUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

}
