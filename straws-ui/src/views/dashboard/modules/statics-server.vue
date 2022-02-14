<template>
    <div>
        <el-descriptions class="margin-top" title="服务器信息" :column="2" border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-user"></i>
                    服务器系统
                </template>
                {{ globalEnv.sys.osName }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    服务器架构
                </template>
                    {{ globalEnv.sys.osArch }}
            </el-descriptions-item>
            
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    CPU 物理核心数
                </template>
                    {{ globalEnv.cpu.physicalProcessorCount }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    CPU 逻辑核心数
                </template>
                    {{ globalEnv.cpu.logicalProcessorCount }}
            </el-descriptions-item>
            
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    系统运行内存
                </template>
                    {{ globalEnv.mem.totalMemory }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    JVM运行内存
                </template>
                    {{ globalEnv.jvm.totalMemory }}
            </el-descriptions-item>

            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    系统剩余可用运行内存
                </template>
                    {{ globalEnv.mem.freeMemory }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    JVM剩余可用运行内存
                </template>
                    {{ globalEnv.jvm.freeMemory }}
            </el-descriptions-item>
            
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    系统内存使用率
                </template>
                    <el-progress type="dashboard" :percentage="globalEnv.mem.usedMemoryRate * 100" :color="colors"></el-progress>
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    JVM内存使用率
                </template>
                    <el-progress type="dashboard" :percentage="globalEnv.jvm.usedMemoryRate * 100" :color="colors"></el-progress>
            </el-descriptions-item>
        </el-descriptions> 
    </div>
</template>

<script>
export default {
    props: {
        globalEnv: {
            type: Object
        }
    },
    data() {
        return {
            percentage: 90,
            colors: [
                {color: '#67C23A', percentage: 30},
                {color: '#409EFF', percentage: 60},
                {color: '#E6A23C', percentage: 80},
                {color: '#F56C6C', percentage: 100}
            ]
        }
    }
}
</script>

<style>

</style>