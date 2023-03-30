package com.ead.course.specification;

import com.ead.course.domain.Course;
import com.ead.course.domain.Lesson;
import com.ead.course.domain.Module;
import com.ead.course.domain.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpec extends Specification<Course> {}

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<Module> {}

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpec extends Specification<Lesson> {}

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<User> {}

    public static Specification<Module> findModulesByCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Module> module = root;
            Root<Course> course = query.from(Course.class);
            Expression<Collection<Module>> courseModules = course.get("modules");
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, courseModules));
        };
    }

    public static Specification<Lesson> findLessonsByModuleId(final UUID moduleId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Lesson> lesson = root;
            Root<Module> module = query.from(Module.class);
            Expression<Collection<Lesson>> lessonModules = module.get("lessons");
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, lessonModules));
        };
    }

    public static Specification<User> findUsersByCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<User> user = root;
            Root<Course> course = query.from(Course.class);
            Expression<Collection<User>> courseUsers = course.get("users");
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(user, courseUsers));
        };
    }

    public static Specification<Course> findCoursesByUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Course> course = root;
            Root<User> user = query.from(User.class);
            Expression<Collection<Course>> usersCourse = user.get("courses");
            return cb.and(cb.equal(user.get("userId"), userId), cb.isMember(course, usersCourse));
        };
    }

}
