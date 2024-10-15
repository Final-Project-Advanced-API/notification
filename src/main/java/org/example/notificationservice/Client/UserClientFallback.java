package org.example.notificationservice.Client;


import org.example.notificationservice.model.dto.ApiResponse;
import org.example.notificationservice.model.dto.UserResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserClientFallback implements UserClient {

    @Override
    public ApiResponse<UserResponse> getUserById(UUID userId) {
        return ApiResponse.<UserResponse>builder()
                .message("INTERNAL SERVER ERROR")
                .payload(new UserResponse(UUID.fromString("unknown"),"unknown","unknown","unknown", LocalDate.now(),"no","no",LocalDateTime.now(),LocalDateTime.now()))
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
