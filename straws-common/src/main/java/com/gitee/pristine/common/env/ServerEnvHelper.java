package com.gitee.pristine.common.env;

import com.gitee.pristine.common.env.model.*;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import java.text.DecimalFormat;
import java.util.Properties;

/**
 * 服务器环境助手
 * @author Pristine Xu
 * @date 2022/2/5 12:05
 * @description:
 */
public class ServerEnvHelper {

    private static SystemInfo systemInfo = new SystemInfo();
    private static HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private static Properties props = System.getProperties();


    /**
     * 获取 CPU 变量
     * @return
     */
    public static CpuEnv getCpuEnv() {
        CentralProcessor processor = hardware.getProcessor();
        CpuEnv cpu = new CpuEnv();
        cpu.setLogicalProcessorCount(processor.getLogicalProcessorCount());
        cpu.setPhysicalProcessorCount(processor.getPhysicalProcessorCount());
        return cpu;
    }

    /**
     * 获取JVM信息
     * @return
     */
    public static JvmEnv getJvmEnv() {
        Runtime runtime = Runtime.getRuntime();
        long jvmTotalMemoryByte = runtime.totalMemory();
        long freeMemoryByte = runtime.freeMemory();
        JvmEnv jvm = new JvmEnv();
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setTotalMemory(formatByte(runtime.totalMemory()));
        jvm.setFreeMemory(formatByte(runtime.freeMemory()));
        jvm.setUsedMemoryRate(new DecimalFormat("#.##").format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte));
        return jvm;
    }


    public static MemEnv getMemEnv() {
        GlobalMemory globalMemory = systemInfo.getHardware().getMemory();
        long totalByte = globalMemory.getTotal();
        long availableByte = globalMemory.getAvailable();
        MemEnv memory = new MemEnv();
        memory.setTotalMemory(formatByte(totalByte));
        memory.setUsedMemory(formatByte(totalByte - availableByte));
        memory.setFreeMemory(formatByte(availableByte));
        memory.setUsedMemoryRate(new DecimalFormat("#.##").format((totalByte - availableByte) * 1.0 / totalByte));
        return memory;
    }

    public static SysEnv getSysEnv() {
        SysEnv sys = new SysEnv();
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        return sys;
    }

    public static GlobalEnv getGlobalEnv() {
        GlobalEnv global = new GlobalEnv();
        global.setCpu(getCpuEnv());
        global.setJvm(getJvmEnv());
        global.setMem(getMemEnv());
        global.setSys(getSysEnv());
        return global;
    }

    /**
     * 单位转换
     */
    private static String formatByte(long byteNumber) {
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

}
