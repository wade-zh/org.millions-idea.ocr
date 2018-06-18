/***
 * @pName mi-ocr-web
 * @name ApiExceptionInterceptor
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.controller.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ApiExceptionInterceptor {
    static Logger logger = LogManager.getLogger(ApiExceptionInterceptor.class);

    @ExceptionHandler(Exception.class)
    public JSONObject onException(Exception e){
        if (e != null){
            logger.error(e);
            JSONObject msg = new JSONObject();
            msg.put("msg", "系统错误");
            msg.put("section", "mi-ocr-service");
            return msg;
        }
        return null;
    }
}
