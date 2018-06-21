cd ./mi-ocr-eureka-server
call mi-ocr-eureka-server-install.bat
call mi-ocr-eureka-server-run.bat

Wscript sleep30s.vbs

cd ../

cd ./mi-ocr-gateway
call mi-ocr-gateway-install.bat
call mi-ocr-gateway-run.bat

Wscript sleep20s.vbs

cd ../

cd ./mi-ocr-web-captcha-service
call mi-ocr-web-captcha-service-install.bat
call mi-ocr-web-captcha-service-run.bat

Wscript sleep10s.vbs

cd ../

cd ./mi-ocr-worker-win-app
call mi-ocr-worker-win-app-run.bat
