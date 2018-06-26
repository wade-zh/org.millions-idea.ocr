if exist .\slave (
rd /s /q .\slave
mkdir .\slave
) else (mkdir .\slave)


xcopy /e /y /d .\tags\run.bat .\slave\
xcopy /e /y /d .\tags\stop.bat .\slave\
xcopy /e /y /d .\tags\sleep.bat .\slave\
xcopy /e /y /d .\tags\run-slave.bat .\slave\
cd .\slave
call .\sleep.bat
cd ..\

xcopy /y /s /e  .\tags\mic-ocr-config-test\mi-ocr-web-captcha-service-slave .\mi-ocr-web-captcha\mi-ocr-web-captcha-controller\src\main\resources\
xcopy /y /s /e  .\tags\mic-ocr-config-test\mi-ocr-web-user-service-slave .\mi-ocr-web-user\mi-ocr-web-user-controller\src\main\resources\
xcopy /y /s /e .\tags\mic-ocr-config-test\mi-ocr-models\T0003604 .\slave\mi-ocr-models\T0003604\
rd /s /q .\slave\mic-ocr-config-test


cd .\mi-ocr-web-captcha\mi-ocr-web-captcha-parent
call mvn clean package -Dmaven.test.skip=true
cd ..\mi-ocr-web-captcha-controller
xcopy /y .\target\mi-ocr-web-captcha-service.jar ..\..\slave\
cd ..\..\

cd .\mi-ocr-web-user\mi-ocr-web-user-parent
call mvn clean package -Dmaven.test.skip=true
cd ..\mi-ocr-web-user-controller
xcopy /y .\target\mi-ocr-web-user-service.jar ..\..\slave\
cd ..\..\


xcopy /y /s /e .\tags\mi-ocr-worker-win-app .\slave\mi-ocr-worker-win-app\
xcopy /y /s /e .\tags\mi-ocr-web-captcha-service .\slave\mi-ocr-web-captcha-service\
xcopy /y /s /e .\tags\mi-ocr-web-user-service .\slave\mi-ocr-web-user-service\
