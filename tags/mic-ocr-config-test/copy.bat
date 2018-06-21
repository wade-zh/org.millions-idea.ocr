if exist %cd%\publish (
call %cd%\publish\stop.bat
rd /s /q %cd%\publish
mkdir %cd%\publish
) else (mkdir %cd%\publish)
cd %cd%\tags\mic-ocr-config-test
xcopy /e /y /d .\mi-ocr-eureka-server ..\..\mi-ocr-eureka-server\src\main\resources\
xcopy /e /y /d .\mi-ocr-gateway ..\..\mi-ocr-gateway\src\main\resources\
xcopy /e /y /d .\mi-ocr-web-captcha ..\..\mi-ocr-web-captcha\mi-ocr-web-captcha-controller\src\main\resources\
xcopy /e /y /d .\mi-ocr-models\T0003604 ..\..\mi-ocr-models\T0003604\
xcopy /e /y /d .\mi-ocr-worker-win-app ..\..\mi-ocr-worker-win-app\Release\
cd ..\..\
cd %cd%\mi-ocr-eureka-server
call mvn clean package -Dmaven.test.skip=true
xcopy .\target\mi-ocr-eureka-server.jar ..\publish\
cd ..\
cd %cd%\mi-ocr-gateway
call mvn clean package -Dmaven.test.skip=true
xcopy .\target\mi-ocr-gateway.jar ..\publish\
cd ..\
cd %cd%\mi-ocr-web-captcha\mi-ocr-web-captcha-parent
call mvn clean package -Dmaven.test.skip=true
cd ..\mi-ocr-web-captcha-controller
xcopy .\target\mi-ocr-web-captcha-service.jar ..\..\publish\
cd ..\..\
xcopy /e /y /d .\tags .\publish\