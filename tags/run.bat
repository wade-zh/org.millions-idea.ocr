@echo off
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

cd ./mi-ocr-eureka-server
call mi-ocr-eureka-server-install.bat
call mi-ocr-eureka-server-run.bat

Wscript sleep30.vbs

cd ../

cd ./mi-ocr-gateway
call mi-ocr-gateway-install.bat
call mi-ocr-gateway-run.bat

Wscript sleep20.vbs

cd ../

cd ./mi-ocr-web-captcha-service
call mi-ocr-web-captcha-service-install.bat
call mi-ocr-web-captcha-service-run.bat

Wscript sleep10.vbs

cd ../

cd ./mi-ocr-worker-win-app
call mi-ocr-worker-win-app-run.bat
