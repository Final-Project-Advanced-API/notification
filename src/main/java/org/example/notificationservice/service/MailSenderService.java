package org.example.notificationservice.service;

public interface MailSenderService {
    void sendEmailNotification(String email, String receiverName, String title, String message);
}
