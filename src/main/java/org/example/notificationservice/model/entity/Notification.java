package org.example.notificationservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String message;
    private boolean isRead;
    @Column(nullable = false, length = 100)
    private String receiverId;
    @Column(nullable = false, length = 100)
    private String senderId;
    private LocalDateTime createdAt;
}
