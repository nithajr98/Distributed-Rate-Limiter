# Distributed Rate Limiter

A **distributed API rate limiter** implemented using **Spring Boot, Redis, and Lua scripting**.
The system enforces **per-user request limits** using the **Token Bucket algorithm** and ensures **atomic updates across multiple service instances** using Redis Lua scripts.

---

# Overview

This project demonstrates how production systems implement **distributed rate limiting** to protect APIs from excessive traffic or abuse.

Instead of storing rate limit state in application memory, the limiter stores bucket state in **Redis**, allowing **multiple application instances** to share the same rate limits.

A **Spring middleware filter** intercepts incoming requests and applies rate limiting before the request reaches the controller.

---

# Key Features

* Distributed **Token Bucket rate limiting**
* **Atomic Redis operations** using Lua scripts
* **Spring Boot middleware filter** for automatic enforcement
* **Configurable limits via `application.yml`**
* **Per-user rate limiting**
* **Redis hash storage with TTL eviction**
* Designed to work across **multiple application instances**

---

# Architecture

```
Client
   ↓
RateLimitFilter (Spring Middleware)
   ↓
RedisTokenBucketRateLimiter
   ↓
Lua Script (Atomic Execution)
   ↓
Redis
```

In a distributed deployment:

```
              +--------------+
              | LoadBalancer |
              +------+-------+
                     |
        +------------+------------+
        |                         |
   +----v----+               +----v----+
   | Node A  |               | Node B  |
   +----+----+               +----+----+
        |                         |
        +------------+------------+
                     |
                   Redis
```

All instances share the **same rate limit state** stored in Redis.

---

# Algorithm

This project uses the **Token Bucket algorithm**.

Concept:

1. Each user has a bucket containing tokens.
2. Every request consumes one token.
3. Tokens refill over time at a configured rate.
4. If the bucket has no tokens, the request is rejected.

Example configuration:

```
capacity = 10 tokens
refillRate = 5 tokens/second
```

Meaning:

* Burst capacity: **10 requests**
* Sustained throughput: **5 requests/sec**

---

# Redis Data Model

Each user has a Redis hash:

```
rate_limit:{userId}
```

Example:

```
rate_limit:user123
    tokens
    last_refill
```

TTL is applied so **inactive users automatically expire**.

---

# Lua Script

The rate limiter uses a **Redis Lua script** to ensure atomic operations.

The script performs:

1. Read current tokens
2. Calculate elapsed time
3. Refill tokens
4. Check request allowance
5. Update token count
6. Store updated state

This prevents **race conditions when multiple servers process requests simultaneously**.

---

# Configuration

Rate limits are configurable via `application.yml`.

```
rate-limiter:
  capacity: 10
  refill-rate: 5
```

This allows changing rate limits **without modifying code**.

---

# Running the Project

## 1. Start Redis

```
redis-server
```

Verify:

```
redis-cli ping
```

Expected response:

```
PONG
```

---

## 2. Run Spring Boot Application

```
mvn spring-boot:run
```

---

## 3. Send Requests

Example request:

```
curl "http://localhost:8080/api/test?userId=user1"
```

After exceeding the limit:

```
HTTP 429
Rate limit exceeded
```

---

# Inspect Redis State

Open Redis CLI:

```
redis-cli
```

Check a user bucket:

```
HGETALL rate_limit:user1
```

Example output:

```
tokens
3
last_refill
1710000000
```

---

# Technologies Used

* Java
* Spring Boot
* Redis
* Lua scripting

---

# Future Improvements

Potential enhancements:

* Sliding Window rate limiting algorithm
* Dynamic limits per user tier (free vs premium)
* Prometheus metrics for monitoring
* API response headers:

  * `X-RateLimit-Limit`
  * `X-RateLimit-Remaining`
  * `X-RateLimit-Reset`
* Redis cluster support for horizontal scaling

---

# Resume Description

Distributed Rate Limiter (Spring Boot, Redis, Lua)

* Built a distributed **token bucket rate limiter** using Redis and Lua scripts for atomic updates across multiple service instances.
* Implemented a **Spring middleware filter** to enforce per-user API limits before controller execution.
* Designed Redis hash-based bucket storage with TTL eviction for inactive users.
* Verified correctness across multiple application instances sharing a centralized Redis store.

---
