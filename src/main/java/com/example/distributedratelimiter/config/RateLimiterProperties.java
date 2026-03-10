package com.example.distributedratelimiter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rate-limiter")
public class RateLimiterProperties {
    int capacity;
    int refillRate;

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRefillRate(int refillRate) {
        this.refillRate = refillRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRefillRate() {
        return refillRate;
    }
}
