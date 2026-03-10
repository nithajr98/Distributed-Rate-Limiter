package com.example.distributedratelimiter;

import TockenBucket.RedisTokenBucketRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RateLimiterConfig {
    @Bean
    public RedisTokenBucketRateLimiter rateLimiter(RedisTemplate<String,String>redisTemplate){
        return new RedisTokenBucketRateLimiter(
                3,
                1,
                redisTemplate
        );
    }
}
