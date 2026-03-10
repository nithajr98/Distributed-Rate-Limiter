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

