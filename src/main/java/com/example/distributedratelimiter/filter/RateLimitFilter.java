package com.example.distributedratelimiter.filter;

import TockenBucket.RateLimiter;
import TockenBucket.RedisTokenBucketRateLimiter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
    private RedisTokenBucketRateLimiter rateLimiter;

    RateLimitFilter(RedisTokenBucketRateLimiter rateLimiter){
        this.rateLimiter=rateLimiter;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader("X-USER-ID");

        if(userId==null ||userId.isEmpty()){
            filterChain.doFilter(request,response);
            return;
        }
        boolean allowed = rateLimiter.allowRequest(userId,1);

        if(!allowed){
            response.setStatus(429);
            response.getWriter().write("Rate Limit reached");
            return;
        }

        filterChain.doFilter(request,response);
    }
}
