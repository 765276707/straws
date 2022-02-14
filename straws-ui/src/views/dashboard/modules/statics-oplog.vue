<template>
    <div>
        <el-descriptions class="margin-top" title="操作记录">
            <el-descriptions-item>
                <el-timeline :reverse="reverse" v-if="oplogs.length">
                    <el-timeline-item
                    v-for="(item, index) in oplogs"
                    :key="index"
                    :timestamp="item.optime">
                        <el-tag :color="item.typeColor" effect="dark">{{item.type}}</el-tag>
                        {{item.description}}
                    </el-timeline-item>
                </el-timeline>
                <el-empty description="暂没有您的操作记录" v-else></el-empty>
            </el-descriptions-item>
        
        </el-descriptions>
    </div>
</template>

<script>
export default {
    props: {
        oplogs: {
            type: Array
        }
    },
    data() {
        return {
            // 正序还是倒序
            reverse: false
        }
    }
}
</script>

<style>

</style>