package org.example.notificationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.notificationservice.model.entity.Notification;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private String id;
    private String title;
    private String message;
    private boolean isRead;
    private UserResponse sender;
    private UserResponse receiver;
    private LocalDateTime createdAt;
    public NotificationResponse(Notification notification, UserResponse sender, UserResponse receiver) {
        this.id = notification.getId().toString();
        this.title = notification.getTitle();
        this.message = notification.getMessage();
        this.isRead = notification.isRead();
        this.sender = sender;
        this.receiver = receiver;
    }
}
