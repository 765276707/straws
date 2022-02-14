package com.gitee.pristine.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author Pristine Xu
 * @date 2022/1/13 17:27
 * @description:
 */
public class DateUtil {

    /**
     * 获取当前毫秒时间
     * @return
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 计算之前的时间和当前时间之间的间隔，毫秒
     * @return
     */
    public static long calcIntervalBeforeTime(long beforeTimeMills) {
        long now = getTimeStamp();
        return now - beforeTimeMills;
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String formatAsDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String formatAsMillisecond(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(date);
    }

    /**
     * 将毫秒转化为人类可读时间
     * @param ms
     * @return
     */
    public static String humanReadableTime(long ms) {
        if (0 <= ms && ms < 1000) {
            return ms + " ms";
        } else if (ms >= 1000 && ms < 60*1000) {
            return ms/1000 + " s";
        } else {
            return ms/60000 + " m";
        }
    }
}
