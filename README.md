#Distributed-Rate-Limiter<br>
Tesing:<br>
Run the following in powershell<br>
for ($i=1; $i -le 10; $i++) {<br>
try {<br>
(Invoke-WebRequest "http://localhost:8080/tb/test?userId=128").Content<br>
} catch {<br>
$_.Exception.Response.StatusCode.value__<br>
}<br>
}<br>
<br>
This project has:<br>
   -Distributed rate limiter<br>
   -Atomic Redis Lua script<br>
   -Token bucket algorithm<br>
   -Spring Boot integration<br>
   -Script caching (EVALSHA)<br>
   -TTL cleanup<br>
