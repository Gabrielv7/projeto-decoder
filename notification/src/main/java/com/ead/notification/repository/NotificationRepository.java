package com.ead.notification.repository;

import com.ead.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
