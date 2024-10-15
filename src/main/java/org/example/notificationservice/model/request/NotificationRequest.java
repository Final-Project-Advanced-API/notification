package org.example.notificationservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    @NotNull(message = "Title must not be null.")
    @NotBlank(message = "Title must not be blank.")
    private String title;
    @NotNull(message = "Message must not be null.")
    @NotBlank(message = "Message must not be blank.")
    private String message;
    @NotNull(message = "Receiver Id must not be null.")
    @NotBlank(message = "Receiver Id must not be blank.")
    private String receiverId;
}
