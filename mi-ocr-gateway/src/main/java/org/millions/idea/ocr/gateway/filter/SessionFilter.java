/***
 * @pName mi-ocr-gateway
 * @name SessionFilter
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.gateway.filter;


import com.alibaba.fastjson.JSON;
import org.millions.idea.ocr.gateway.HttpResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@WebFilter(urlPatterns = "/**", filterName = "sessionFilter")
public class SessionFilter implements Filter {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final Set<String> EXCLUDE = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/user-api/login", "/user-api/category/initCache")));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        if(EXCLUDE.contains(path)) {
            filterChain.doFilter(servletRequest, response);
            return;
        }

        // 获取登录令牌
        String token =  request.getParameter("token");
        if (token == null) {
            returnFaildMessage(response, "请先登录");
            return;
        }

        // 校验与续期操作
        Integer result = redisTemplate.getExpire(token).intValue();
        if(result == null || result <= 0){
            returnFaildMessage(response, "请重新登录");
            return;
        }
        result += 30;
        redisTemplate.expire(token,result, TimeUnit.SECONDS);
        filterChain.doFilter(servletRequest, response);
    }

    /**
     * 返回失败消息
     * @param response
     * @param msg
     * @throws IOException
     */
    private void returnFaildMessage(HttpServletResponse response, String msg) throws IOException {
        response.reset();
        response.setContentType("application/json;charset=utf-8");
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Prama", "no-cache");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(new HttpResp(1, msg)));
        writer.flush();
        writer.close();
    }

    @Override
    public void destroy() {

    }
}
