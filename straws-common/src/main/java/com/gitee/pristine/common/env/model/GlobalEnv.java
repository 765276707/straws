package com.gitee.pristine.common.env.model;

/**
 * @author Pristine Xu
 * @date 2022/2/5 13:11
 * @description:
 */
public class GlobalEnv {

    private SysEnv sys;

    private CpuEnv cpu;

    private JvmEnv jvm;

    private MemEnv mem;

    public SysEnv getSys() {
        return sys;
    }

    public void setSys(SysEnv sys) {
        this.sys = sys;
    }

    public CpuEnv getCpu() {
        return cpu;
    }

    public void setCpu(CpuEnv cpu) {
        this.cpu = cpu;
    }

    public JvmEnv getJvm() {
        return jvm;
    }

    public void setJvm(JvmEnv jvm) {
        this.jvm = jvm;
    }

    public MemEnv getMem() {
        return mem;
    }

    public void setMem(MemEnv mem) {
        this.mem = mem;
    }

    @Override
    public String toString() {
        return "GlobalEnv{" +
                "sys=" + sys +
                ", cpu=" + cpu +
                ", jvm=" + jvm +
                ", mem=" + mem +
                '}';
    }
}
