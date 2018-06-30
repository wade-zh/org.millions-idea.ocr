/***
 * @pName mi-ocr-web-finance
 * @name GlobalExceptionHandler
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.order.handler;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.millions.idea.ocr.web.order.entity.exception.FinanceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    final static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MessageException.class)
    public HttpResp preException(HttpServletRequest request,
                                 MessageException exception) throws Exception {
        logger.error(String.format("自定义消息异常: %s",exception.toString()));
        return new HttpResp(exception.getError() == 0 ? 1 : exception.getError(), exception.getMessage());
    }


    @ExceptionHandler(value = FinanceException.class)
    public HttpResp preException(HttpServletRequest request,
                                 FinanceException exception) throws Exception {
        logger.error(String.format("自定义消息异常: %s",exception.toString()));
        System.err.println(exception.getMessage());
        return new HttpResp(1, exception.getError().toString());
    }
}

