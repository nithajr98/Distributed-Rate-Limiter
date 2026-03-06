package com.example.distributedratelimiter;

import TockenBucket.UserBasedRateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final UserBasedRateLimiter rateLimiter =
            new UserBasedRateLimiter(5, 1);

    @GetMapping("/test")
    public String test() {

        Thread A = new Thread(()->{
        for(int i=0;i<10;i++){
            System.out.println(rateLimiter.allowRequest("userA",1));
        }}
        );
        Thread B = new Thread(()->{
            for(int i=0;i<10;i++){
                System.out.println(rateLimiter.allowRequest("userB",1));
            }}
        );
        Thread C = new Thread(()->{
            for(int i=0;i<10;i++){
                System.out.println(rateLimiter.allowRequest("userC",1));
            }}
        );
        A.start();;
        B.start();
        C.start();

        return "Over";
    }


}
