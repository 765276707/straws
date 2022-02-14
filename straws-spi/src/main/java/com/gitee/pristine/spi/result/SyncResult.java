package com.gitee.pristine.spi.result;

import com.gitee.pristine.common.utils.BytesUtil;

/**
 * 任务执行结果
 * @author Pristine Xu
 * @date 2022/1/13 18:01
 * @description:
 */
public class SyncResult {

    // 传输数据耗时
    private Long transferTime;
    // 传输的总记录数
    private Long totalRecords;
    // 传输的总字节数
    private Long totalBytes;
    // 传输的速率
    private Long bytesPerSecond;

    private SyncResult() {}

    public static SyncResult collect() {
        return new SyncResult();
    }

    public Long getTransferTime() {
        return transferTime;
    }

    public SyncResult transferTime(Long transferTime) {
        this.transferTime = transferTime;
        return this;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public SyncResult totalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
        return this;
    }

    public Long getTotalBytes() {
        return totalBytes;
    }

    public SyncResult totalBytes(Long totalBytes) {
        this.totalBytes = totalBytes;
        return this;
    }

    public Long getBytesPerSecond() {
        return bytesPerSecond;
    }

    public SyncResult bytesPerSecond(Long bytesPerSecond) {
        this.bytesPerSecond = bytesPerSecond;
        return this;
    }

    @Override
    public String toString() {
        return "SyncResult{" +
                "transferTime=" + transferTime +
                ", totalRecords=" + totalRecords +
                ", totalBytes=" + BytesUtil.humanReadableUnits(totalBytes) +
                ", bytesPerSecond=" + BytesUtil.humanReadableUnits(bytesPerSecond) +
                '}';
    }
}
