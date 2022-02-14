package com.gitee.pristine.spi.result;

import com.gitee.pristine.common.utils.BytesUtil;
import com.gitee.pristine.common.utils.DateUtil;
import com.gitee.pristine.domain.entity.Task;

import java.util.Date;
import java.util.List;

/**
 * 执行结果工具类
 * @author Pristine Xu
 * @date 2022/1/14 19:37
 * @description: 对执行的结果进行转换、合并、过滤
 */
public class SyncResultTool {

    /**
     * 对所有的Chunk执行结果进行统计
     * @param results
     * @return
     */
    public static SyncResult merge(List<SyncResult> results) {
        long totalRecords = 0;
        long totalBytes = 0;
        long totalTransferTime = 0;
        for (SyncResult res : results) {
            totalBytes   += res.getTotalBytes();
            totalRecords += res.getTotalRecords();
            totalTransferTime += res.getTransferTime();
        }
        long transferTime = totalTransferTime / results.size();
        long bytesPerSecond = calcBytesPerSecond(totalBytes, transferTime);
        return SyncResult.collect()
                .totalRecords(totalRecords)
                .totalBytes(totalBytes)
                .bytesPerSecond(bytesPerSecond)
                .transferTime(transferTime);
    }

    /**
     * 计算速率
     * @param recordsBytes
     * @param transferTime
     * @return
     */
    private static long calcBytesPerSecond(long recordsBytes, long transferTime) {
        long var = 1;
        if (transferTime > 1000) {
            var = transferTime / 1000;
        }
        return recordsBytes / var;
    }

    /**
     * 在控制台打印结果
     */
    public static void printInConsole(SyncResult result, Task task, Date startTime, Date finishTime) {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("=======================统计结果=======================");
        System.out.println("任务名称： " + task.getName());
        System.out.println("启动时间： " + DateUtil.format(startTime));
        System.out.println("结束时间： " + DateUtil.format(new Date()));
        System.out.println("传输耗时： " + DateUtil.humanReadableTime(result.getTransferTime()));
        System.out.println("传输总数： " + result.getTotalRecords() + " 条");
        System.out.println("传输字节： " + BytesUtil.humanReadableUnits(result.getTotalBytes()));
        System.out.println("设定速率： " + BytesUtil.humanReadableUnits(task.getBytesPerSecond() * 1024));
        System.out.println("实际速率： " + BytesUtil.humanReadableUnits(result.getBytesPerSecond()));
    }
}
