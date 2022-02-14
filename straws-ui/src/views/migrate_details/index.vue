<template>
    <el-container>
        <el-page-header @back="goBack" content="数据迁移" style="margin-top: 10px"></el-page-header>

        <el-card shadow="never" class="migrate-detail">
            <el-row>
                <el-col :lg="6">
                    <statistic title="任务名称" :value="migrate.migrateName"></statistic>
                </el-col>
                <el-col :lg="6">
                    <statistic title="来源数据源" :value="migrate.originDsName"></statistic>
                </el-col>
                <el-col :lg="6">
                    <statistic title="目标数据源" :value="migrate.targetDsName"></statistic>
                </el-col>
                <el-col :lg="6">
                    <statistic title="运行状态" :value="status"></statistic>
                </el-col>
            </el-row>
        </el-card>
        
        <el-header class="topbar">
            <div class="left-panel">
                <el-button type="primary" icon="el-icon-s-order" @click="createDetails">生成表映射</el-button>
                <el-button type="primary" icon="el-icon-plus" @click="save">添加映射</el-button>
                <el-button type="danger" icon="el-icon-remove" @click="clearDetails">清空映射</el-button>
                <el-button type="warning" icon="el-icon-refresh" @click="save">刷新映射</el-button>
                <el-button type="success" icon="el-icon-coin" @click="migrateTable">迁移表结构</el-button>
                <el-button type="success" icon="el-icon-tickets" @click="migrateData">迁移表数据</el-button>
            </div>
            <div class="right-panel">
                <div class="right-panel-search">
                    <el-input placeholder="搜索映射表名"></el-input>
                    <el-button type="primary" icon="el-icon-search"></el-button>
                </div>
            </div>
        </el-header>

        <el-main>
            <el-table
            :data="pageResult.list"
            element-loading-text="加载数据中"
            v-loading="loading"
            :header-row-style="{height:45+'px'}"
            :row-style="{height:60+'px'}"
            style="width: 100%;">
                <el-table-column type="selection"   width="55" ></el-table-column>
                <el-table-column prop="id"          label="编号"    width="100" ></el-table-column>
                <el-table-column prop="originTableName"    label="源头表" ></el-table-column>
                <el-table-column prop="targetTableName"    label="目标表" ></el-table-column>
                <el-table-column prop="transformers"    label="数据转换器" ></el-table-column>
                <el-table-column label="创建表" >
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.isCreateTable === true" type="success">是</el-tag>
                        <el-tag v-else type="success" >否</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="字段映射" >
                    <template>
                        自动
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            style="color: #67C23A"
                            icon="el-icon-edit"
                            @click="edit(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                            type="text"
                            style="color: #F56C6C"
                            icon="el-icon-delete"
                            @click="remove(scope.$index, scope.row)">删除</el-button>        
                    </template>
                </el-table-column>
                <template #empty>
					<el-empty :description="'暂无数据'" :image-size="100"></el-empty>
				</template>
            </el-table>

            <pagination :total="pageResult.total" @page-change="pageChange"></pagination>
        </el-main>

        <save-update ref="SaveUpdate" @success="getPage"></save-update>

    </el-container>
</template>

<script>
import Statistic from '@/components/Statistic'
import SaveUpdate from './save-update.vue'

export default {
    components: {
        Statistic,
        SaveUpdate
    },
    data() {
        return {
            migrate: {
                migrateId: null,
                migrateName: '',
                originDsId: null,
                originDsName: '',
                targetDsId: null,
                targetDsName: ''
            },
            pageResult: { // 表格分页数据   
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            status: '未启动', 
            loading: false, //表格加载动画
            timer: null,  //定时器
        }
    },
    created() {
        this.migrate.migrateId  = this.$route.query.migrateId
        this.migrate.migrateName = this.$route.query.migrateName
        this.migrate.originDsId = this.$route.query.originDsId
        this.migrate.originDsName = this.$route.query.originDsName
        this.migrate.targetDsId = this.$route.query.targetDsId
        this.migrate.targetDsName = this.$route.query.targetDsName
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
            let response = await this.$API.migrate_detail.getPage(this.migrate.migrateId)   
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

        // 生成映射
        createDetails() {
            this.$confirm('本操作将会重新生成任务内的映射, 是否继续?', '操作提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                this.$API.migrate_detail.createDetails(this.migrate.migrateId).then(response => {
                    this.getPage()
                    this.$message.success(response.message)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消生成'
                })
            })
        },

        // 清空映射
        async clearDetails() {
            this.$confirm('本操作将会清空任务内所有映射, 是否继续?', '删除提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                this.$API.migrate_detail.clearDetails(this.migrate.migrateId).then(response => {
                    this.getPage()
                    this.$message.success(response.message)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                })
            })
        },

        async migrateTable() {
            let response = await this.$API.migrate_detail.migrateTable(this.migrate.migrateId)   
            this.$message.success(response.message)
        },

        async migrateData() {
            this.$confirm('迁移表数据可能会较为耗时，是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                this.$API.migrate_detail.migrateData(this.migrate.migrateId).then(response => {
                    this.$message.success(response.message)
                })                   
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消操作'
                })
            })
        },


        save() {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open({
                    'migrateId' : this.migrate.migrateId,
                    'isCreateTable' : true
                }) 
            })
        },

        edit(index, row) {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open(row) 
            })
        },

        remove(index, row) {
            this.$API.migrate_detail.deleteById(row.id).then(response => {
                this.getPage()
                this.$message.success(response.message)
            })
        },

        goBack() {
            // 返回
        }
    }
}
</script>

<style lang="scss" scoped>
.el-container {
    padding: 10px;
}

.migrate-detail {
    width: 100%; margin-top: 20px;
}

.el-main {
    width: 100%;
    height: 100%;
    padding: 0;
}

.topbar {
    height: 50px; border-bottom: 1px solid #ebeef5; border-top: 1px solid #ebeef5;background: #fff;
    box-shadow: 0 1px 4px rgba(0,21,41,.08);display: flex;justify-content:space-between; margin-top: 10px;

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