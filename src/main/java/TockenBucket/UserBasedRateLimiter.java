package TockenBucket;

import java.util.concurrent.ConcurrentHashMap;

public class UserBasedRateLimiter{
    private int capacity;
    private int refillRate;

    ConcurrentHashMap<String, TokenBucketRateLimiter>buckets;

    public UserBasedRateLimiter(int capacity,int refillRate){
        this.refillRate=refillRate;
        this.capacity=capacity;
        this.buckets=new ConcurrentHashMap<>();
    }

    public String allowRequest(String userId,int token) {

        TokenBucketRateLimiter bucket = buckets.computeIfAbsent(userId, k->new TokenBucketRateLimiter(capacity,refillRate));

        return userId +  String.valueOf(bucket.allowRequest(token));

    }
}
