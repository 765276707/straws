package com.gitee.pristine.common.env.model;

/**
 * 系统内存
 * @author Pristine Xu
 * @date 2022/2/5 12:58
 * @description:
 */
public class MemEnv {

    // 总内存
    private String totalMemory;
    // 已用内存
    private String usedMemory;
    // 空闲内存
    private String freeMemory;
    // 内存使用率
    private String usedMemoryRate;

    public String getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(String usedMemory) {
        this.usedMemory = usedMemory;
    }

    public String getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(String freeMemory) {
        this.freeMemory = freeMemory;
    }

    public String getUsedMemoryRate() {
        return usedMemoryRate;
    }

    public void setUsedMemoryRate(String usedMemoryRate) {
        this.usedMemoryRate = usedMemoryRate;
    }

    @Override
    public String toString() {
        return "MemEnv{" +
                "totalMemory='" + totalMemory + '\'' +
                ", usedMemory='" + usedMemory + '\'' +
                ", freeMemory='" + freeMemory + '\'' +
                ", usedMemoryRate='" + usedMemoryRate + '\'' +
                '}';
    }
}
