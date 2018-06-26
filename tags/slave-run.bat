@echo off


cd ./
set parentDir = %cd%


cd ./mi-ocr-worker-win-app
call mi-ocr-worker-win-app-run.bat

cd %parentDir %
Wscript sleep20.vbs
cd ./mi-ocr-web-captcha-service
call mi-ocr-web-captcha-service-install.bat
call mi-ocr-web-captcha-service-run.bat


cd %parentDir %
cd ./mi-ocr-web-user-service
call mi-ocr-web-user-service-install.bat
call mi-ocr-web-user-service-run.bat
 