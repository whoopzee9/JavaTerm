package com.course.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private final String secretKey = UUID.randomUUID().toString();
    private long validityInMs = 180000;

    public String getSecretKey() {
        return secretKey;
    }

    public long getValidityInMs() {
        return validityInMs;
    }

    public void setValidityInMs(long validityInMs) {
        this.validityInMs = validityInMs;
    }
}
