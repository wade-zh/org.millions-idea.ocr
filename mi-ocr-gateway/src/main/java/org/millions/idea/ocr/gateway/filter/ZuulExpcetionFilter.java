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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.gateway.handler.GlobalExceptionHandler;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;

import javax.servlet.http.HttpServletResponse;

public class ZuulExpcetionFilter extends ZuulFilter {
    final static Logger logger = LogManager.getLogger(ZuulExpcetionFilter.class);

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
        ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ctx.set("error.exception", throwable.getCause());
        logger.error(String.format("网关异常: %s",throwable.getCause().getMessage() + "{" + JsonUtil.getJson(throwable) +  "}"));
        return null;
    }
}
