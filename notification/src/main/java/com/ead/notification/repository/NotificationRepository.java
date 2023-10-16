package com.ead.notification.repository;

import com.ead.notification.domain.model.Notification;
import com.ead.notification.domain.model.enums.NotificationStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Page<Notification> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatusEnum statusEnum, Pageable pageable);

    @Query(value = "select * from tb_notification where notification_id = :notificationId and user_id = :userId", nativeQuery = true)
    Optional<Notification> findByNotificationIdAndUserId(@Param("notificationId") UUID notificationId, @Param("userId") UUID userId);

}
