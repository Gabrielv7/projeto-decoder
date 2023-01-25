package com.ead.authuser.specification;

import com.ead.authuser.domain.User;
import com.ead.authuser.domain.UserCourse;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import java.util.UUID;

@Component
public class SpecificationTemplate {

    @And({
       @Spec(path = "userType", spec = Equal.class),
       @Spec(path = "email", spec = Like.class),
       @Spec(path = "userStatus", spec = Equal.class),
       @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<User> {}

    public static Specification<User> findUsersByCourseId(final UUID courseId){
        return ((root, criteriaQuery, criteriaBuilder) -> {
           criteriaQuery.distinct(true);
           Join<User, UserCourse> userProd = root.join("usersCourses");
           return criteriaBuilder.equal(userProd.get("courseId"), courseId);
        });
    }

}
