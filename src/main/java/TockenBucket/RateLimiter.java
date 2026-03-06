package TockenBucket;

public interface RateLimiter {
    boolean allowRequest(int token);
}
