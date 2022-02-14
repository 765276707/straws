package com.gitee.pristine.spi.statis;

/**
 * Chunk汇报统计类
 * @author Pristine Xu
 * @date 2022/1/13 8:11
 * @description: Chunk内的数据都要汇报到本类
 */
public class Statistics {

    // 记录数
    private long capacityCounter;

    // 字节数
    private long bytesCounter;

    // 记录时间戳
    private long timestamp;

    // 限制速率
    private final long bytesPerSecond;

    /**
     * 传入的单位是KB
     * @param bytesPerSecond
     */
    public Statistics(long bytesPerSecond) {
        this.bytesPerSecond = bytesPerSecond * 1024;
        this.setInitValue();
    }

    public synchronized void reset() {
        this.setInitValue();
    }

    public synchronized void resetCounter() {
        this.capacityCounter = 0;
        this.bytesCounter = 0;
    }

    private void setInitValue() {
        this.capacityCounter = 0;
        this.bytesCounter = 0;
        this.timestamp = System.currentTimeMillis();
    }

    public synchronized void setCapacityCounter(final long value) {
        this.capacityCounter = value;
    }

    public synchronized long getCapacityCounter() {
        return capacityCounter;
    }

    public synchronized void setBytesCounter(final long value) {
        this.bytesCounter = value;
    }

    public synchronized long getBytesCounter() {
        return bytesCounter;
    }

    public synchronized void incrCapacityCounter(final long value) {
        this.capacityCounter += value;
    }

    public synchronized boolean incrBytesCounter(final long value) {
        this.bytesCounter += value;
        // 判断当前的字节数是否超过限定速率
        if (this.bytesCounter >= bytesPerSecond) {
            return true;
        }
        return false;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public synchronized Statistics merge(final Statistics statistics) {
        if (statistics == null) {
            return this;
        }
        this.incrCapacityCounter(statistics.getCapacityCounter());
        this.incrBytesCounter(statistics.getBytesCounter());
        return this;
    }

}
