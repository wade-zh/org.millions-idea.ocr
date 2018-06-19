xcopy /y F:\repository\org.millions-idea.ocr\mi-ocr-eureka-server\target\mi-ocr-eureka-server.jar %cd%\1.0.0-20180619
xcopy /y F:\repository\org.millions-idea.ocr\mi-ocr-gateway\target\mi-ocr-gateway.jar %cd%\1.0.0-20180619
xcopy /y F:\repository\org.millions-idea.ocr\mi-ocr-web\mi-ocr-web-controller\target\mi-ocr-web-service.jar %cd%\1.0.0-20180619
xcopy /e /y  F:\repository\org.millions-idea.ocr\mi-ocr-worker\mi-ocr-worker-win\mi-ocr-worker-win-app\bin\Release %cd%\1.0.0-20180619\mi-ocr-worker-win-app
rd /s /q %cd%\1.0.0-20180619\mi-ocr-logs
pause