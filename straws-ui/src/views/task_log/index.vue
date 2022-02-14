<template>

    <el-drawer
    :title="title" 
    :visible.sync="visible"
    v-if="visible"
    size="990px"
    :show-close="false"
    :close-on-press-escape="false"
    append-to-body>

        <el-table
        :data="pageResult.list"
        element-loading-text="加载数据中"
        v-loading="loading"
        :header-row-style="{height:45+'px'}"
        :row-style="{height:60+'px'}"
        style="width: 100%;">
            <el-table-column prop="id"           label="编号" width="55" ></el-table-column>
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
                    <el-button 
                        v-if="scope.row.taskResult == 2"
                        type="text"
                        @click="showFailedReason(scope.$index, scope.row)">查看原因</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty :description="'暂无数据'" :image-size="100"></el-empty>
            </template>
        </el-table>

        <pagination :total="pageResult.total" @page-change="pageChange"></pagination>

        <el-dialog 
        :title="'失败原因'" 
        :visible.sync="showFailedReasonVisible"
        v-if="showFailedReasonVisible"
        width="800px"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        append-to-body>

            {{ failedReason }}

            <div slot="footer" class="dialog-footer">
                <el-button @click="closeFailedReason()">关 闭</el-button>
            </div>
        </el-dialog>

    </el-drawer>

    
</template>

<script>

export default {
    components: {

    },
    data() {
        return {
            title: '任务日志',
            visible: false,
            searchForm: {
                pageNum: 1,
                pageSize: 10,
                taskId: null
            },
            pageResult: { // 表格分页数据   
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false, //表格加载动画
            showFailedReasonVisible: false,
            failedReason: ''
        }
    },
    methods: {
        /**
         * @title 获取列表
         */
        async getPage() {
            this.loading = true
            let response = await this.$API.task_log.getPage(this.searchForm)   
            this.pageResult = response.data
            this.loading = false
        },

        /**
         * @title 更改分页
         */
        pageChange(pageNum, pageSize) {
            this.searchForm.pageNum = pageNum
            this.searchForm.pageSize = pageSize
            this.getPage()
        },

        /**
         * 打开表单
         */
        open(row) {
            this.title = '任务日志 - ' + row.name
            this.searchForm.taskId = row.id
            this.getPage()
            this.visible = true
        },

        /**
         * 关闭表单
         */
        close() {
            this.visible = false
        },
        
        showFailedReason(index, row) {
            this.showFailedReasonVisible = true
            console.log(row)
            this.failedReason = row.taskFailedReason
        },

        closeFailedReason() {
            this.showFailedReasonVisible = false
            this.failedReason = ''
        }
    }
}
</script>

<style>

</style>