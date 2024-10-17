package org.example.notificationservice.Client;



import org.example.notificationservice.config.FeignClientConfig;
import org.example.notificationservice.model.dto.ApiResponse;
import org.example.notificationservice.model.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "user-service" ,url = "http://localhost:8081",fallbackFactory = UserClientFallback.class,configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/api/v1/users/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable UUID userId);
}
