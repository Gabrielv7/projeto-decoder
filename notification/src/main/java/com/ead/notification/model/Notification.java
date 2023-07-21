package com.ead.notification.model;

import com.ead.notification.model.enums.NotificationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "TB_NOTIFICATION")
public class Notification implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID notificationId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatusEnum notificationStatus;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @PrePersist
    public void setStatusEnumCreated() {
        notificationStatus = NotificationStatusEnum.CREATED;
    }

}
