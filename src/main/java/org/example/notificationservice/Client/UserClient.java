package org.example.notificationservice.Client;



import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.notificationservice.config.FeignClientConfig;
import org.example.notificationservice.model.dto.ApiResponse;
import org.example.notificationservice.model.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@FeignClient(name = "user-service",url = "http://localhost:8081",configuration = FeignClientConfig.class)
public interface UserClient {
    @CircuitBreaker(name = "userClient", fallbackMethod = "userFallback")
    @GetMapping("/api/v1/users/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable UUID userId);
    default ApiResponse<UserResponse> userFallback(UUID userId, Throwable throwable) {
        return ApiResponse.<UserResponse>builder()
                .message("Service unavailable, fallback response.")
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .payload(new UserResponse(
                        "unavailable",
                        "unavailable",
                        "unavailable",
                        "unavailable",
                        LocalDate.now(),
                        "unavailable",
                        "unavailable",
                        LocalDateTime.now(),
                        LocalDateTime.now()))
                .timestamp(LocalDateTime.now())
                .build();
    }
}
