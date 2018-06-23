/***
 * @pName mi-ocr-gateway
 * @name ZuulExpcetionFilter
 * @user HongWei
 * @date 2018/6/23
 * @desc
 */
package org.millions.idea.ocr.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletResponse;

public class ZuulExpcetionFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        //log.error("this is a ErrorFilter :" + throwable.getCause().getMessage(), throwable);
        System.out.println("捕获异常" + throwable.getCause().getMessage());
        ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ctx.set("error.exception", throwable.getCause());
        return null;
    }
}
