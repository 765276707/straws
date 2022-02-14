package com.gitee.pristine.common.env.model;

/**
 * @author Pristine Xu
 * @date 2022/2/5 12:49
 * @description:
 */
public class JvmEnv {

    // VM版本
    private String version;
    // VM总内存
    private String totalMemory;
    // VM空闲内存
    private String freeMemory;
    // VM内存使用率
    private String usedMemoryRate;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
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
        return "JvmEnv{" +
                "version='" + version + '\'' +
                ", totalMemory='" + totalMemory + '\'' +
                ", freeMemory='" + freeMemory + '\'' +
                ", usedMemoryRate='" + usedMemoryRate + '\'' +
                '}';
    }
}
