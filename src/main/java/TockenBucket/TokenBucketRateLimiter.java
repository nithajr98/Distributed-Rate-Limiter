package TockenBucket;

import static java.lang.Math.min;

public class TokenBucketRateLimiter implements RateLimiter{


    private final int capacity;
    private final int refillRate;
    private int currentTokens;
    long lastRefillTimestamp;

    public TokenBucketRateLimiter(int capacity, int refillRate){
        this.capacity=capacity;
        this.refillRate = refillRate;
        this.lastRefillTimestamp = System.currentTimeMillis();
        this.currentTokens=capacity;
    }


    public synchronized boolean allowRequest(int tokens) {
        refill();

        if(currentTokens<0 || tokens>currentTokens){
            return false;
        }
        currentTokens-=tokens;
        return true;

    }
    private synchronized void refill(){
        long now = System.currentTimeMillis();

        long elapsed = (now - lastRefillTimestamp)/1000;

        int tokensToAdd = (int) (elapsed*refillRate);

        currentTokens = min(capacity,tokensToAdd+currentTokens);

        lastRefillTimestamp = now;
    }
}
