@echo off

sc stop mi-ocr-eureka-server
sc stop mi-ocr-gateway
sc stop mi-ocr-web-captcha-service


set port=20000
for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do (
    echo kill the process %%m who use the port %port%
    taskkill /f /pid %%m
)

set port=20001
for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do (
    echo kill the process %%m who use the port %port%
    taskkill /f /pid %%m
)

taskkill /F /im mi-ocr-worker-win-app.exe

taskkill /F /im java.exe