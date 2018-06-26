if exist .\publish (
rd /s /q .\publish
mkdir .\publish
) else (mkdir .\publish)


xcopy /e /y /d .\tags\run.bat .\publish\
xcopy /e /y /d .\tags\stop.bat .\publish\
xcopy /e /y /d .\tags\sleep.bat .\publish\
cd .\publish
call .\sleep.bat
cd ..\

xcopy /y /s /e  .\tags\mic-ocr-config-test\mi-ocr-eureka-server .\mi-ocr-eureka-server\src\main\resources\
xcopy /y /s /e  .\tags\mic-ocr-config-test\mi-ocr-gateway .\mi-ocr-gateway\src\main\resources\
xcopy /y /s /e  .\tags\mic-ocr-config-test\mi-ocr-web-captcha-service .\mi-ocr-web-captcha\mi-ocr-web-captcha-controller\src\main\resources\
xcopy /y /s /e  .\tags\mic-ocr-config-test\mi-ocr-web-user-service .\mi-ocr-web-user\mi-ocr-web-user-controller\src\main\resources\
xcopy /y /s /e .\tags\mic-ocr-config-test\mi-ocr-models\T0003604 .\publish\mi-ocr-models\T0003604\
rd /s /q .\publish\mic-ocr-config-test

cd .\mi-ocr-eureka-server
call mvn clean package -Dmaven.test.skip=true
xcopy /y .\target\mi-ocr-eureka-server.jar ..\publish\
cd ..\


cd .\mi-ocr-gateway
call mvn clean package -Dmaven.test.skip=true
xcopy /y .\target\mi-ocr-gateway.jar ..\publish\


cd ..\mi-ocr-web-captcha\mi-ocr-web-captcha-parent
call mvn clean package -Dmaven.test.skip=true
cd ..\mi-ocr-web-captcha-controller
xcopy /y .\target\mi-ocr-web-captcha-service.jar ..\..\publish\
cd ..\..\

cd .\mi-ocr-web-user\mi-ocr-web-user-parent
call mvn clean package -Dmaven.test.skip=true
cd ..\mi-ocr-web-user-controller
xcopy /y .\target\mi-ocr-web-user-service.jar ..\..\publish\
cd ..\..\


xcopy /y /s /e .\tags\mi-ocr-worker-win-app .\publish\mi-ocr-worker-win-app\
xcopy /y /s /e .\tags\mi-ocr-eureka-server .\publish\mi-ocr-eureka-server\
xcopy /y /s /e .\tags\mi-ocr-gateway .\publish\mi-ocr-gateway\
xcopy /y /s /e .\tags\mi-ocr-web-captcha-service .\publish\mi-ocr-web-captcha-service\
xcopy /y /s /e .\tags\mi-ocr-web-user-service .\publish\mi-ocr-web-user-service\
