/***
 * @pName mi-ocr-gateway
 * @name GlobalExceptionHandler
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.gateway.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.gateway.HttpResp;
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    final static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = MessageException.class)
    public org.millions.idea.ocr.web.common.entity.common.HttpResp preException(HttpServletRequest request,
                                                                                MessageException exception) throws Exception {
        logger.error(String.format("自定义异常消息: %s",exception.toString()));
        if(exception.getError() == null) exception.setError(1);
        return new org.millions.idea.ocr.web.common.entity.common.HttpResp(exception.getError() == 0 ? 1 : exception.getError(), exception.getMessage());
    }


    @ExceptionHandler(value = TimeoutException.class)
    public org.millions.idea.ocr.web.common.entity.common.HttpResp preException(HttpServletRequest request,
                                                                                TimeoutException exception) throws Exception {
        return new org.millions.idea.ocr.web.common.entity.common.HttpResp(1, "连接超时");
    }

    @ExceptionHandler(value = ConnectException.class)
    @Order(6)
    public org.millions.idea.ocr.web.common.entity.common.HttpResp preException(HttpServletRequest request,
                                                                                ConnectException exception) throws Exception {
        return new org.millions.idea.ocr.web.common.entity.common.HttpResp(1, "无法连接数据中心");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public org.millions.idea.ocr.web.common.entity.common.HttpResp preException(HttpServletRequest request,
                                                                                RuntimeException exception) throws Exception {
        logger.error(String.format("运行时异常: %s",exception.toString()));
        return new org.millions.idea.ocr.web.common.entity.common.HttpResp(1, "操作失败");
    }

    @ExceptionHandler(value = Exception.class)
    public org.millions.idea.ocr.web.common.entity.common.HttpResp preException(HttpServletRequest request,
                                                                                Exception exception) throws Exception {
        logger.error(String.format("全局异常: %s",exception.toString()));
        return new org.millions.idea.ocr.web.common.entity.common.HttpResp(1, "系统异常");
    }
}
