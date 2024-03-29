package com.ead.course.domain;

import com.ead.course.domain.enums.UserStatusEnum;
import com.ead.course.domain.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "TB_USER")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID userId;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum userStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;

    @Column(length = 20)
    private String cpf;

    @Column
    private String imageUrl;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Course> courses;

}
