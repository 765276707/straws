package com.gitee.pristine.common.env.model;

/**
 * @author Pristine Xu
 * @date 2022/2/5 13:05
 * @description:
 */
public class SysEnv {

    // 系统名称
    private String osName;
    // 系统架构
    private String osArch;

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    @Override
    public String toString() {
        return "SysEnv{" +
                "osName='" + osName + '\'' +
                ", osArch='" + osArch + '\'' +
                '}';
    }
}
