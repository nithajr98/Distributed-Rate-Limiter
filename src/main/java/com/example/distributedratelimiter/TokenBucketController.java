package com.example.distributedratelimiter;

import TockenBucket.RedisTokenBucketRateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/oneusertest")
    public ResponseEntity<String> testOneUser(@RequestHeader("X-USER-ID") String userId){
        boolean allowed = rateLimiter.allowRequest(userId,1);

        if(!allowed){
            return ResponseEntity
                    .status(429)
                    .body("Rate limit exceeded");
        }

        return ResponseEntity.ok("Request allowed");
    }


}
