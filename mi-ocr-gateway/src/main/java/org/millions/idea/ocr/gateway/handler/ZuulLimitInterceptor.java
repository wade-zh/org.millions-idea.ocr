/***
 * @pName mi-ocr-gateway
 * @name ZuulLimitInterceptor
 * @user HongWei
 * @date 2018/6/20
 * @desc
 */
package org.millions.idea.ocr.gateway.handler;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.gateway.HttpResp;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ZuulLimitInterceptor extends HandlerInterceptorAdapter {
    static Logger logger = LogManager.getLogger(ZuulLimitInterceptor.class);

    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (response.getStatus() == 429){
            response.reset();
            response.setContentType("application/json;charset=utf-8");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Prama", "no-cache");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(new HttpResp(1,"访问过于频繁")));
            writer.flush();
            writer.close();
            return false;
        }else if (response.getStatus() == 500){
            response.reset();
            response.setContentType("application/json;charset=utf-8");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Prama", "no-cache");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(new HttpResp(1, "网关服务异常")));
            writer.flush();
            writer.close();
            return false;
        }
        return super.preHandle(request,response,handler);
    }
}


