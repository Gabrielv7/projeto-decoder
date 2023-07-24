package com.ead.notification.model;

import com.ead.notification.model.enums.NotificationStatusEnum;
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
import java.util.Objects;
import java.util.UUID;

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

    public Notification() {

    }

    public Notification(UUID notificationId, UUID userId, String title, String message, NotificationStatusEnum notificationStatus, LocalDateTime creationDate) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.notificationStatus = notificationStatus;
        this.creationDate = creationDate;
    }

    public Notification(String message, String title, UUID userId) {
        this.message = message;
        this.title = title;
        this.userId = userId;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationStatusEnum getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatusEnum notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", notificationStatus=" + notificationStatus +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(notificationId, that.notificationId) && Objects.equals(userId, that.userId) && Objects.equals(title, that.title) && Objects.equals(message, that.message) && notificationStatus == that.notificationStatus && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, userId, title, message, notificationStatus, creationDate);
    }
}
