@echo off  
SC QUERY mi-ocr-gateway > NUL  
IF ERRORLEVEL 1060 GOTO NOTEXIST  
GOTO EXIST  
  
:NOTEXIST  
ECHO Install  
mi-ocr-gateway-install.exe install
GOTO END  
  
:EXIST  
ECHO Uninstall
sc stop mi-ocr-gateway
mi-ocr-gateway-install.exe uninstall
mi-ocr-gateway-install.exe install
GOTO END  
  
:END
@echo on