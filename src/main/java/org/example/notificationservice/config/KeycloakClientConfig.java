package org.example.notificationservice.config;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Configuration
public class KeycloakClientConfig {
    @Value("${keycloak.credentials.secret}")
    private String secretKey;
    @Value("${keycloak.credentials.client-id}")
    private String clientId;
    @Value("${keycloak.auth-server-url}")
    private String authUrl;
    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .serverUrl(authUrl)
                .grantType(CLIENT_CREDENTIALS)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(secretKey)
                .build();
    }
}
