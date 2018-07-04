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
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;

public class ZuulLimitInterceptor extends HandlerInterceptorAdapter {
    static Logger logger = LogManager.getLogger(ZuulLimitInterceptor.class);
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.err.println(response.getStatus());
        if (response.getStatus() == 429){
            logger.info("访问过于频繁:" + getUser(request) + "用户IP:" + getIPAddress(request));
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
            writer.write(JSON.toJSONString(new HttpResp(1, "服务异常")));
            writer.flush();
            writer.close();
            return false;
        }
        return super.preHandle(request,response,handler);
    }

    private  String getUser(HttpServletRequest request){
        String token = request.getParameter("token");
        if(token == null || token.length() == 0){
            return JsonUtil.getJson(request);
        }
        Object data = redisTemplate.opsForValue().get(token);
        if(data == null) throw new MessageException("请重新登录");
        return String.valueOf(data);
    }

    private String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}


