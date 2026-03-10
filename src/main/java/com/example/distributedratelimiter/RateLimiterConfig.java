package com.example.distributedratelimiter;

import TockenBucket.RedisTokenBucketRateLimiter;
import com.example.distributedratelimiter.config.RateLimiterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RateLimiterConfig {
    RateLimiterProperties properties;
    RateLimiterConfig(RateLimiterProperties properties){
        this.properties =properties;
    }
    @Bean
    public RedisTokenBucketRateLimiter rateLimiter(RedisTemplate<String,String>redisTemplate){
        return new RedisTokenBucketRateLimiter(
                properties.getCapacity(),
                properties.getRefillRate(),
                redisTemplate
        );
    }
}
