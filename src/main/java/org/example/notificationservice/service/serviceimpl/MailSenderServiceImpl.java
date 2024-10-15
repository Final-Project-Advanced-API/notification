package org.example.notificationservice.service.serviceimpl;

import jakarta.mail.internet.MimeMessage;
import org.example.notificationservice.service.MailSenderService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class MailSenderServiceImpl implements MailSenderService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    public MailSenderServiceImpl(TemplateEngine templateEngine, JavaMailSender mailSender) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailNotification(String email, String receiverName, String title, String message) {
        try {
            // Create the context for Thymeleaf template
            Context context = new Context();
            context.setVariable("receiverName", receiverName);
            context.setVariable("title", title);
            context.setVariable("message", message);

            // connect to thymeleaf
            String htmlContent = templateEngine.process("notification-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject("New Notification: " + title);
            helper.setText(htmlContent, true); // Set as HTML content

            // Send the email
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email notification", e);
        }
    }
}
