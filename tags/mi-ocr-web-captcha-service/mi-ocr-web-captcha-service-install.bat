@echo off  
SC QUERY mi-ocr-web-captcha-service > NUL  
IF ERRORLEVEL 1060 GOTO NOTEXIST  
GOTO EXIST  
  
:NOTEXIST  
ECHO Install  
mi-ocr-web-captcha-service-install.exe install
GOTO END  
  
:EXIST  
ECHO Uninstall
sc stop mi-ocr-web-captcha-service
mi-ocr-web-captcha-service-install.exe uninstall
mi-ocr-web-captcha-service-install.exe install
GOTO END  
  
:END
@echo on