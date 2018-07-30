/***
 * @pName mi-ocr-web-common-utility
 * @name RequestUtil
 * @user HongWei
 * @date 2018/7/25
 * @desc
 */
package org.millions.idea.ocr.web.common.utility.utils;


import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import org.apache.commons.lang3.StringUtils;
import org.millions.idea.ocr.web.common.utility.json.Address;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RequestUtil {
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (StringUtils.isEmpty(requestedWith)) {
            return false;
        } else if (StringUtils.isNotEmpty(requestedWith) && requestedWith.equals("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)
                && StringUtils.contains(ip, ",")) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            ip = StringUtils.substringBefore(ip, ",");
        }
        // 处理localhost访问
        if (StringUtils.isBlank(ip) || "unkown".equalsIgnoreCase(ip)
                || StringUtils.split(ip, ".").length != 4) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ip = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    //获取请求的域名
    public static String getDomain(HttpServletRequest request){
        String contextPath = request.getContextPath();
        if (StringUtils.isNotEmpty(contextPath)) {
            return contextPath;
        } else {
            if (request.getServerPort() == 80 || request.getServerPort() == 443) {
                return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
            } else {
                return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            }
        }
    }

    public static String getCity(String ip){
        try {
            String url = "http://ip.ws.126.net/ipquery?ip=" + ip;
            String json = HttpClientUtil.get(HttpConfig.custom().url(url));
            if(json == null) return null;
            json = json.substring(json.indexOf("{"), json.length());
            Address address = (Address)JsonUtil.getModel(json, Address.class);
            return address.getProvince() + address.getCity();
        } catch (Exception e) {
            return null;
        }
    }

}
