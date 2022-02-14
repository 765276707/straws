package com.gitee.pristine.common.env.model;

/**
 * @author Pristine Xu
 * @date 2022/2/5 12:45
 * @description:
 */
public class CpuEnv {

    // 逻辑核心数
    private Integer logicalProcessorCount;
    // 物理核心数
    private Integer physicalProcessorCount;

    public Integer getLogicalProcessorCount() {
        return logicalProcessorCount;
    }

    public void setLogicalProcessorCount(Integer logicalProcessorCount) {
        this.logicalProcessorCount = logicalProcessorCount;
    }

    public Integer getPhysicalProcessorCount() {
        return physicalProcessorCount;
    }

    public void setPhysicalProcessorCount(Integer physicalProcessorCount) {
        this.physicalProcessorCount = physicalProcessorCount;
    }

    @Override
    public String toString() {
        return "CpuEnv{" +
                "logicalProcessorCount=" + logicalProcessorCount +
                ", physicalProcessorCount=" + physicalProcessorCount +
                '}';
    }
}
