package com.gitee.pristine.security.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作系统工具类
 * @author xzb
 */
public class OSUtil {

    /**
     * 获取客户端操作系统
     * @param request
     * @return
     */
    public static String getClientOS(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        // 未知
        if (userAgent==null || "".equals(userAgent)) {
            return "Unknown";
        }
        // windows os
        if (isWindows10(userAgent)) {
            return "Windows 10";
        }
        if (isWindows7(userAgent)) {
            return "Windows 7";
        }
        if (isWindows8(userAgent)) {
            return "Windows 8";
        }
        if (isWindowsXP(userAgent)) {
            return "Windows XP";
        }
        if (isOtherWindows(userAgent)) {
            return "Windows OS";
        }
        // Mac OS
        if (userAgent.indexOf("Mac") > -1) {
            return "Mac OS";
        }
        // Unix OS
        if (userAgent.indexOf("Unix") > -1) {
            return "Unix OS";
        }
        // Solaris OS
        if (userAgent.indexOf("SunOS") > -1) {
            return "Solaris OS";
        }
        // Linux OS
        if (userAgent.indexOf("Linux")>-1 || userAgent.indexOf("Ubuntu") > 0) {
            return "Linux OS";
        }
        // IOS
        if (userAgent.indexOf("iPhone") > -1) {
            return "IOS";
        }
        // Android
        if (userAgent.indexOf("Android") > -1) {
            return "Android";
        }
        // Others
        return "Others";
    }


    private static boolean isWindowsXP(String userAgent) {
        return userAgent.indexOf("NT 5.1") > -1;
    }

    private static boolean isWindows7(String userAgent) {
        return userAgent.indexOf("NT 6.1") > -1;
    }

    private static boolean isWindows8(String userAgent) {
        return userAgent.indexOf("NT 6.2") > -1 || userAgent.indexOf("NT 6.3") > -1;
    }

    private static boolean isWindows10(String userAgent) {
        return userAgent.indexOf("NT 10") > -1;
    }

    private static boolean isOtherWindows(String userAgent) {
        return userAgent.indexOf("NT 5") > -1 || userAgent.indexOf("NT 6") > -1;
    }

    private static boolean isWindows11() {
        return false;
    }
}
