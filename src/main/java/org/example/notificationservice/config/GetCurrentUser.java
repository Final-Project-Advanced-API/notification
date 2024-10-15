package org.example.notificationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetCurrentUser {
    public String getUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
