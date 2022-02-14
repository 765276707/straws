<template>
    <el-container>
        <el-header class="topbar">
            <div class="left-panel">
                <el-button type="primary" icon="el-icon-plus" @click="save">添加</el-button>
                <el-button type="danger" plain icon="el-icon-delete">删除</el-button>
            </div>
            <div class="right-panel">
                <div class="right-panel-search">
                    <el-input v-model="searchText" placeholder="编号/名称/类型"></el-input>
                    <el-button type="primary" icon="el-icon-search"></el-button>
                </div>
            </div>
        </el-header>

        <el-main>
            <el-table
            :data="pageResult.list"
            element-loading-text="加载数据中"
            v-loading="loading"
            style="width: 100%;">
                <el-table-column type="selection"   width="55" ></el-table-column>
                <el-table-column prop="id"          label="编号"    width="60" ></el-table-column>
                <el-table-column prop="name"    label="任务名" ></el-table-column>
                <el-table-column prop="groupName"    label="任务分组" ></el-table-column>
                <el-table-column label="来源数据源" >
                    <template slot-scope="scope">
                        <i class="el-icon-coin"></i> {{scope.row.originDsName}}
                    </template>
                </el-table-column>
                <el-table-column prop="originTableName"    label="来源表" ></el-table-column>
                <el-table-column label="目标数据源" >
                    <template slot-scope="scope">
                        <i class="el-icon-coin"></i> {{scope.row.targetDsName}}
                    </template>
                </el-table-column>
                <el-table-column prop="targetTableName"    label="目标表" ></el-table-column>
                <el-table-column prop="columns"    label="同步列" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column label="同步模式" >
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.syncMode == 1" type="success" effect="dark">全量</el-tag>
                        <el-tag v-if="scope.row.syncMode == 2" type="warning" effect="dark">增量</el-tag>
                        <el-tag v-if="scope.row.syncMode == 3" effect="dark">CDC</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="splitPk"     label="切分列名" ></el-table-column>
                <el-table-column label="切分列类型" >
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.splitPkType == 1" type="success" effect="plain">INT</el-tag>
                        <el-tag v-if="scope.row.splitPkType == 2" type="warning" effect="plain">LONG</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="timeCron"    label="CRON表达式" ></el-table-column>
                <el-table-column prop="workersPerGroup"    label="Chunk数" ></el-table-column>
                <el-table-column prop="bytesPerSecond"    label="限速阈值（KB）" ></el-table-column>
                <el-table-column prop="selectFetchSize"    label="每次查询大小" ></el-table-column>
                <el-table-column prop="insertBatchSize"    label="批量插入大小" ></el-table-column>
                <el-table-column label="操作" width="100">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            @click="resumeOrPause(scope.$index, scope.row)">
                            <span v-if="scope.row.runStatus" style="color: #E6A23C">暂停</span>
                            <span v-else style="color:#67C23A">启动</span>
                        </el-button>
                        <el-button
                            type="text"
                            style="color: #909399"
                            @click="showLog(scope.$index, scope.row)">日志</el-button>   
                        <el-dropdown trigger="click">
                            <el-button
                            type="text">更多操作<i class="el-icon-arrow-down"></i></el-button> 
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item @click.native="edit(scope.$index, scope.row)">编辑</el-dropdown-item>
                                <el-dropdown-item @click.native="trigger(scope.$index, scope.row)">执行一次</el-dropdown-item>
                                <el-dropdown-item @click.native="deleteInRow(scope.$index, scope.row)">删除</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown> 
                    </template>
                </el-table-column>
                <template #empty>
					<el-empty :description="'暂无数据'" :image-size="100"></el-empty>
				</template>
            </el-table>

            <pagination :total="pageResult.total" @page-change="pageChange"></pagination>
        </el-main>

        <save-update ref="SaveUpdate" @success="getPage"></save-update>

        <task-log ref="TaskLog"></task-log>

    </el-container>
</template>

<script>
import SaveUpdate from './save-update.vue'
import TaskLog from '@/views/task_log/index.vue'

export default {
    components: {
        SaveUpdate,
        TaskLog
    },
    data() {
        return {
            searchText: '',
            pageResult: { // 表格分页数据   
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false, //表格加载动画
        }
    },
    mounted() {
        // 初始化列表
        this.getPage()
    },
    methods: {
        /**
         * @title 获取列表
         */
        async getPage() {
            this.loading = true
            let response = await this.$API.task.getPage(this.searchText)   
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

        async resumeOrPause(index, row) {
            let response = await this.$API.task.resumeOrPause(row.id)
            this.$message.success(response.message)
            this.getPage()
        },

        async trigger(index, row) {
            let response = await this.$API.task.trigger(row.id)
            this.$message.success(response.message)
            this.getPage()
        },

        save() {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open({
                    syncMode: 2,
                    bytesPerSecond: 2048,
                    workersPerGroup: 1,
                    runStatus: true
                }); 
            })
        },

        edit(index, row) {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open(row); 
            })
        },

        showLog(index, row) {
            this.$nextTick(() => {
                this.$refs.TaskLog.open(row); 
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.el-main {
    width: 100%;
    height: 100%;
    padding: 0;
}
.topbar {
    height: 50px;border-bottom: 1px solid #ebeef5; border-top: 1px solid #ebeef5;background: #fff;box-shadow: 0 1px 4px rgba(0,21,41,.08);display: flex;justify-content:space-between;

    .left-panel {
        display: flex;
        align-items: center;
    }
    .right-panel {
        display: flex;
        align-items: center;
    }

    .right-panel-search {display: flex;align-items: center;}

    .right-panel-search > * + * {margin-left:10px;}
}


</style>