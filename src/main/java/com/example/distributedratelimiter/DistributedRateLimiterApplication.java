package com.example.distributedratelimiter;

import TockenBucket.UserBasedRateLimiter;
import com.example.distributedratelimiter.config.RateLimiterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RateLimiterProperties.class)
public class DistributedRateLimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedRateLimiterApplication.class, args);

//		UserBasedRateLimiter limiter = new UserBasedRateLimiter(5,1);
//
//		for(int i=0;i<10;i++){
//			System.out.println(limiter.allowRequest("userA",1));
//
//		}
	}

}
