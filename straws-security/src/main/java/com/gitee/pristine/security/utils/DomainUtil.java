package com.gitee.pristine.security.utils;

/**
 * 域名工具类
 * @author xzb
 */
public class DomainUtil {

    /**
     * 获取域名
     * @return
     */
    public static String getDomainName(String url) {
        String result = "";
        int j = 0, startIndex = 0, endIndex = 0;
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                j++;
                if (j == 2)
                    startIndex = i;
                else if (j == 3)
                    endIndex = i;
            }

        }
        result = url.substring(startIndex + 1, endIndex);
        return result;
    }

}
