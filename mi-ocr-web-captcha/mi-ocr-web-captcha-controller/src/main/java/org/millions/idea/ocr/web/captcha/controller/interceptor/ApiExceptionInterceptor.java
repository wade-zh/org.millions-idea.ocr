/***
 * @pName mi-ocr-web
 * @name ApiExceptionInterceptor
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.captcha.controller.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.common.entity.common.HttpResp;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ApiExceptionInterceptor {
    static Logger logger = LogManager.getLogger(ApiExceptionInterceptor.class);

    @ExceptionHandler(Exception.class)
    public String onException(Exception e){
        if (e != null){
            logger.error(e);
            return (String) JSON.toJSON(new HttpResp(1,"服务故障"));
        }
        return null;
    }
}
