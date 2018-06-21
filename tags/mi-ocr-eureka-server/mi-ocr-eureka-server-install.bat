@echo off  
SC QUERY mi-ocr-eureka-server > NUL  
IF ERRORLEVEL 1060 GOTO NOTEXIST  
GOTO EXIST  
  
:NOTEXIST  
ECHO Install  
mi-ocr-eureka-server-install.exe install
GOTO END  
  
:EXIST  
ECHO Uninstall
sc stop mi-ocr-eureka-server
mi-ocr-eureka-server-install.exe uninstall
mi-ocr-eureka-server-install.exe install
GOTO END  
  
:END
@echo on