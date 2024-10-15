package org.example.notificationservice.service;


import org.example.notificationservice.model.dto.NotificationResponse;
import org.example.notificationservice.model.request.NotificationRequest;
import java.util.List;

public interface NotificationService {
    NotificationResponse sendNotificationToUser(NotificationRequest notificationRequest);
    List<NotificationResponse> getNotificationByReceiverId();
    void notificationIsRead(String id);
}
