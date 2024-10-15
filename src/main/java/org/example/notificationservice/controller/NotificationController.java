package org.example.notificationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.notificationservice.model.dto.ApiResponse;
import org.example.notificationservice.model.dto.NotificationResponse;
import org.example.notificationservice.model.entity.Notification;
import org.example.notificationservice.model.request.NotificationRequest;
import org.example.notificationservice.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "stack-notes")
public class NotificationController {

    private final NotificationService notificationService;
    @Operation(summary = "send notification")
    @PostMapping
    public ResponseEntity<ApiResponse> sendNotificationToUser(@RequestBody NotificationRequest notificationRequest) {
        NotificationResponse savedNotification = notificationService.sendNotificationToUser(notificationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Notification sent")
                .status(HttpStatus.CREATED)
                .code(201)
                .payload(savedNotification)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @Operation(summary = "Get all notifications of a user")
    @GetMapping("/{receiverId}")
    public ResponseEntity<ApiResponse> getNotificationByReceiverId() {
        List<NotificationResponse> notificationResponses = notificationService.getNotificationByReceiverId();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Notification of a user has founded.")
                .status(HttpStatus.OK)
                .code(200)
                .payload(notificationResponses)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }
    @Operation(summary = "change status to already read")
    @PutMapping("/isRead/{id}")
    public ResponseEntity<ApiResponse> isNotificationRead(@PathVariable String id) {
        notificationService.notificationIsRead(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Notification has updated to is read.")
                .status(HttpStatus.OK)
                .code(200)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }
}
