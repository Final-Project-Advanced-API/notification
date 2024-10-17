package org.example.notificationservice.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.Client.UserClient;
import org.example.notificationservice.config.GetCurrentUser;
import org.example.notificationservice.config.RabbitMQConfig;
import org.example.notificationservice.exception.CustomNotfoundException;
import org.example.notificationservice.model.dto.NotificationResponse;
import org.example.notificationservice.model.dto.UserResponse;
import org.example.notificationservice.model.entity.Notification;
import org.example.notificationservice.model.request.NotificationRequest;
import org.example.notificationservice.repository.NotificationRepository;
import org.example.notificationservice.service.MailSenderService;
import org.example.notificationservice.service.NotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final RabbitTemplate rabbitTemplate;
    private final UserClient userClient;
    private final GetCurrentUser getCurrentUser;
    private final MailSenderService mailSenderService;

    //send notification
    @Override
    public NotificationResponse sendNotificationToUser(NotificationRequest notificationRequest) {
        String senderId= notificationRequest.getSenderId();
        UserResponse sender = userClient.getUserById(UUID.fromString(senderId)).getPayload();

        Notification notification = Notification.builder()
                .title(notificationRequest.getTitle())
                .message(notificationRequest.getMessage())
                .isRead(false)
                .senderId(senderId)
                .receiverId(notificationRequest.getReceiverId())
                .createdAt(LocalDateTime.now())
                .build();

        String routingKey = "user." + notification.getReceiverId();

        // Send notification to RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, notification);
        notificationRepository.save(notification);
        String receiverId= notification.getReceiverId();
        UserResponse receiver = userClient.getUserById(UUID.fromString(receiverId)).getPayload();
        mailSenderService.sendEmailNotification(receiver.getEmail(), receiver.getUsername(), notificationRequest.getTitle(), notificationRequest.getMessage());

        return new NotificationResponse(notification,sender,receiver);
    }

    //get all notification of a receiver
    @Override
    public List<NotificationResponse> getNotificationByReceiverId(int pageNumber, int pageSize) {
        String receiverId = getCurrentUser.getUser();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Notification> notifications = notificationRepository.findByReceiverId(receiverId, pageable); // Only get the content

        UserResponse receiver = userClient.getUserById(UUID.fromString(receiverId)).getPayload();

        return notifications.stream().map(notification -> {
            String senderId = notification.getSenderId();
            UserResponse sender = userClient.getUserById(UUID.fromString(senderId)).getPayload();
            return new NotificationResponse(notification, sender, receiver);
        }).collect(Collectors.toList());
    }

    //change notification to is read
    @Override
    public void notificationIsRead(String id) {
        Notification notification = notificationRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CustomNotfoundException("Notification not found."));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    //get notification by id
    @Override
    public NotificationResponse getNotificationById(String id) {
        Notification notification = notificationRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new CustomNotfoundException("Notification not found.")
        );
        String senderId = notification.getSenderId();
        UserResponse sender = userClient.getUserById(UUID.fromString(senderId)).getPayload();
        String receiverId = notification.getReceiverId();
        UserResponse receiver = userClient.getUserById(UUID.fromString(receiverId)).getPayload();
        return new NotificationResponse(notification, sender, receiver);
    }

    //delete notification by id
    @Override
    public void deleteNotification(String id) {
        Notification notification = notificationRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new CustomNotfoundException("Notification not found.")
        );
        notificationRepository.delete(notification);
    }

}
