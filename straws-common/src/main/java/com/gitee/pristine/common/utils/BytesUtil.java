package com.gitee.pristine.common.utils;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;

/**
 * 字节工具类
 * @author Pristine Xu
 * @date 2021/12/28 1:35
 * @description:
 */
public class BytesUtil {

    // 数字格式化，保留两位小数
    private final static DecimalFormat df = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.ROOT));


    /**
     * 获取对象的字节数大小
     * @param obj 对象
     * @returns 字节 byte
     */
    public static long sizeOf(Object obj) {
        return ObjectSizeCalculator.getObjectSize(obj);
    }

    /**
     * 获取集合的字节数大小
     * @param collection 集合
     * @param <T>
     * @return
     */
    public static<T> long sizeOf(Collection<T> collection) {
        long total = 0L;
        for (T t : collection) {
            total = total + sizeOf(t);
        }
        return total;
    }

    /**
     * 换算成人类可读的单位
     * @param bytes 字节数
     * @return
     */
    public static String humanReadableUnits(long bytes) {
        return humanReadableUnits(bytes, df);
    }

    /**
     * 换算成人类可读的单位
     * @param bytes 字节数
     * @param decimalFormat 数字格式
     * @return
     */
    private static String humanReadableUnits(long bytes, DecimalFormat decimalFormat) {
        if (bytes / 1073741824L > 0L) {
            return decimalFormat.format((double)((float)bytes / 1.07374182E9F)) + " GB";
        } else if (bytes / 1048576L > 0L) {
            return decimalFormat.format((double)((float)bytes / 1048576.0F)) + " MB";
        } else {
            return bytes / 1024L > 0L ? decimalFormat.format((double)((float)bytes / 1024.0F)) + " KB" : bytes + " bytes";
        }
    }

    /**
     * 字节数转KB
     * @param bytes 字节数
     * @return
     */
    public static double toKB(long bytes) {
        return (double) bytes / 1024L;
    }

    /**
     * 字节数转MB
     * @param bytes 字节数
     * @return
     */
    public static double toMB(long bytes) {
        return (double) ((float)bytes / 1048576.0F);
    }

    /**
     * 字节数转GB
     * @param bytes 字节数
     * @return
     */
    public static double toGB(long bytes) {
        return (double) ((float)bytes / 1.07374182E9F);
    }

}
