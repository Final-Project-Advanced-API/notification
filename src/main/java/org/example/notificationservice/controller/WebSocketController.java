package org.example.notificationservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.notificationservice.model.entity.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/notify")
    public void sendNotification(Long receiverId, Notification notification) {
        messagingTemplate.convertAndSend("/topic/notifications." + receiverId, notification);
    }

}
