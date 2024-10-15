package org.example.notificationservice.service.serviceimpl;


import lombok.RequiredArgsConstructor;
import org.example.notificationservice.config.RabbitMQConfig;
import org.example.notificationservice.model.entity.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationReceiver {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(Notification notification) {
        // Forward this message to the specific web client via WebSocket
        simpMessagingTemplate.convertAndSend("/topic/notifications." + notification.getReceiverId(), notification);
    }

}
