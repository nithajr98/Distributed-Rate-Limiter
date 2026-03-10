#Distributed-Rate-Limiter
Tesing:
Run the following in powershell
for ($i=1; $i -le 10; $i++) {
try {
(Invoke-WebRequest "http://localhost:8080/tb/test?userId=128").Content
} catch {
$_.Exception.Response.StatusCode.value__
}
}

This project has:
   -Distributed rate limiter
   -Atomic Redis Lua script
   -Token bucket algorithm
   -Spring Boot integration
   -Script caching (EVALSHA)
   -TTL cleanup
