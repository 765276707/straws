<template>
    <el-table
    :data="list"
    :header-row-style="{height:45+'px'}"
    :row-style="{height:60+'px'}"
    style="width: 100%;">
        <el-table-column prop="id"           label="编号" width="55" ></el-table-column>
        <el-table-column prop="taskName"      label="任务名称" ></el-table-column>
        <el-table-column prop="taskStartTime"      label="任务开始时间" ></el-table-column>
        <el-table-column prop="taskFinishTime"     label="任务结束时间"></el-table-column>      
        <el-table-column prop="transferAverageTime"     label="平均传输耗时"></el-table-column>
        <el-table-column prop="transferRecordCount"  label="传输总记录数" ></el-table-column>
        <el-table-column prop="transferRecordBytes"  label="传输总字节数"></el-table-column>
        <el-table-column prop="transferAverageSpeed"  label="传输平均速率"></el-table-column>
        <el-table-column label="执行结果" >
            <template slot-scope="scope">
                <el-tag v-if="scope.row.taskResult == 1" type="success" effect="dark">成功</el-tag>
                <el-tag v-if="scope.row.taskResult == 2" type="danger" effect="dark">失败</el-tag>
            </template>
        </el-table-column>
        <template #empty>
            <el-empty :description="'暂无数据'" :image-size="100"></el-empty>
        </template>
    </el-table>
</template>

<script>
export default {
    props: {
        list: {
            type: Array
        }
    },
    // data() {
    //     return {
    //         loading: false,
    //         list: []
    //     }
    // },
    // created() {
    //     this.getLatestTaskLogs()
    // }, 
    // methods: {

    //     async getLatestTaskLogs() {
    //         let response = await this.$API.statics.getLatestTaskLogs()   
    //         this.list = response.data
    //     },

    // }
}
</script>

<style>

</style>