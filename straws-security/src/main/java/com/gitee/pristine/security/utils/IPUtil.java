package com.gitee.pristine.security.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址工具类
 * @author xzb
 */
public class IPUtil {

    private final static String UNKNOWN = "unknown";

    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多重代理，则取第一个ip
        if (ip.contains(",")) {
            return ip.split(",")[0];
        }
        // 本地ip
        if (isLocalIp(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }

    /**
     * 是否为本地IP地址
     * @param ip
     * @return
     */
    private static boolean isLocalIp(String ip) {
        return "0:0:0:0:0:0:0:1".equals(ip);
    }
}
