package com.gitee.pristine.security.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 浏览器工具类
 * @author xzb
 */
public class BrowserUtil {

    /**
     * 获取客户端的浏览器类型
     * @param request
     * @return
     */
    public static String getClientBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        // 未知
        if (userAgent==null || "".equals(userAgent)) {
            return "Unknown";
        }
        // Chrome 浏览器？
        if (userAgent.indexOf("Chrome") > -1
                && userAgent.indexOf("Safari") > -1) {
            return "Chrome";
        }
        // Firefox 浏览器？
        if (userAgent.indexOf("Firefox") > -1) {
            return "Firefox";
        }
        // Edge 浏览器？
        if (userAgent.indexOf("Edge") > -1) {
            return "Edge";
        }
        // Safari 浏览器？
        if (userAgent.indexOf("Safari") > -1
                && userAgent.indexOf("Chrome") == -1) {
            return "Safari";
        }
        // Opera 浏览器？
        if (userAgent.indexOf("Opera") > -1) {
            return "Opera";
        }
        // IE 浏览器？ 11之前版本用MSIE判断，11及之后版本用Trident判断
        if ((userAgent.indexOf("MSIE") > -1 && userAgent.indexOf("compatible") > -1)
                || userAgent.indexOf("Trident") > -1) {
            return "IE";
        }
        // 其它浏览器？
        return "Others";
    }

}
