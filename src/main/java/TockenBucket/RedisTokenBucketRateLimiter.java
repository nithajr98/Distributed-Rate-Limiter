package TockenBucket;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;

public class RedisTokenBucketRateLimiter {

    private int capacity;
    private int refillRate;


//    long lastRefillTimeStamp;
//    long currentToken;

    private final RedisTemplate<String, String>redisTemplate;
    private final RedisScript<Long>tokenBucketScript;

    public RedisTokenBucketRateLimiter(int capacity, int refillRate, RedisTemplate<String, String> redisTemplate){
        this.capacity = capacity;
        this.refillRate =refillRate;
        this.redisTemplate = redisTemplate;

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("token-bucket.lua"));
        script.setResultType(Long.class);
        this.tokenBucketScript=script;
    }

    public boolean allowRequest(String userId,int token){
        if(token<=0){
            return false;
        }

        Long now = System.currentTimeMillis();
        List<String> keys = List.of(redisKey(userId));

        Long result = redisTemplate.execute(
                tokenBucketScript,
                keys,
                String.valueOf(now),
                String.valueOf(token),
                String.valueOf(capacity),
                String.valueOf(refillRate)
        );

        return result!=null && result==1;

    }
    String redisKey(String userId){
        return "rate_limit:"+userId;
    }
}
