package com.ead.course.repository;

import com.ead.course.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID>, JpaSpecificationExecutor<Lesson> {

    @Query(value = "select * from tb_lesson where module_id = :moduleId", nativeQuery = true)
    List<Lesson> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);

    @Query(value = "select * from tb_lesson where module_id = :moduleId and lesson_id = :lessonId", nativeQuery = true)
    Optional<Lesson> findLessonIntoModule(@Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);

    boolean existsByTitle(String title);

    boolean existsByDescription(String description);

}
