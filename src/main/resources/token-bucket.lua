local key = KEYS[1]

local now = tonumber(ARGV[1])
local requested = tonumber(ARGV[2])
local capacity = tonumber(ARGV[3])
local refillRate = tonumber(ARGV[4])

if capacity <= 0 or refillRate < 0 or requested < 0 then
    return 0
end

local tokens = redis.call("HGET", key, "tokens")
local last_refill = redis.call("HGET", key, "last_refill")

if not tokens then
    tokens = capacity
else
    tokens = tonumber(tokens)
end

if not last_refill then
    last_refill = now
else
    last_refill = tonumber(last_refill)
end

local elapsed = now - last_refill
local refill = (elapsed/1000) * refillRate

tokens = math.min(capacity, tokens + refill)

if tokens < requested then
    return 0
end

tokens = tokens - requested

redis.call("HSET", key, "tokens", tokens)
redis.call("HSET", key, "last_refill", now)
redis.call("EXPIRE", key, 60)

return 1
