package org.example.notificationservice.repository;

import org.example.notificationservice.model.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByReceiverId(String receiverId, Pageable pageable);
}
