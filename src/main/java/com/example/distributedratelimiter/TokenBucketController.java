package com.example.distributedratelimiter;

import TockenBucket.RedisTokenBucketRateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tb")
public class TokenBucketController {
    private final RedisTokenBucketRateLimiter rateLimiter;

    TokenBucketController(RedisTokenBucketRateLimiter rateLimiter){
        this.rateLimiter = rateLimiter;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestParam String userId){
        boolean allowed = rateLimiter.allowRequest(userId,1);

        if(!allowed){
            return ResponseEntity
                    .status(429)
                    .body("Rate limit exceeded");
        }

        return ResponseEntity.ok("Request allowed");
    }


}
