@echo off  
SC QUERY mi-ocr-web-user-service > NUL  
IF ERRORLEVEL 1060 GOTO NOTEXIST  
GOTO EXIST  
  
:NOTEXIST  
ECHO Install  
mi-ocr-web-user-service-install.exe install
GOTO END  
  
:EXIST  
ECHO Uninstall
sc stop mi-ocr-web-user-service
mi-ocr-web-user-service-install.exe uninstall
mi-ocr-web-user-service-install.exe install
GOTO END  
  
:END
@echo on