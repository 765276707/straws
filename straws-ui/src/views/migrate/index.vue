<template>
    <el-container>
        <el-header class="topbar">
            <div class="left-panel">
                <el-button type="success" icon="el-icon-plus" @click="save">添加</el-button>
                <el-button type="warning" icon="el-icon-refresh" @click="refresh">刷新</el-button>
            </div>
            <div class="right-panel">
                <div class="right-panel-search">
                    <el-input v-model="searchText" placeholder="搜索任务名"></el-input>
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
                <el-table-column prop="name"    label="任务名" width="300"></el-table-column>
                <el-table-column prop="originDsName"    label="源头数据源" ></el-table-column>
                <el-table-column prop="targetDsName"    label="目标数据源" ></el-table-column>
                <el-table-column prop="transformers"    label="数据转换器" width="300"></el-table-column>
                <el-table-column prop="execTime"    label="执行时间" ></el-table-column>
                <el-table-column prop="createTime"   label="创建时间" ></el-table-column>
                <el-table-column prop="updateTime" label="修改时间" ></el-table-column>
                <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-ship"
                            @click="migrate(scope.$index, scope.row)">迁移</el-button>
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
import SaveUpdate from './save-update.vue'

export default {
    components: {
        SaveUpdate
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
            editorOptions: {
                tabSize: 4,
                mode: 'text/x-groovy',
                theme: 'material',
                lineNumbers: true,
                line: true
            }
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
            let response = await this.$API.migrate.getPage(this.searchText)   
            this.pageResult = response.data
            this.loading = false
        },

        refresh() {
            this.searchText = ''
            this.getPage()
        },

        /**
         * @title 更改分页
         */
        pageChange(pageNum, pageSize) {
            this.searchForm.pageNum = pageNum
            this.searchForm.pageSize = pageSize
            this.getPage()
        },

        save() {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open({}); 
            })
        },

        migrate(index, row) {
            this.$router.push({ path: '/migrate/details', query: {
                migrateId: row.id,
                migrateName: row.name,
                originDsId: row.originDsId,
                originDsName: row.originDsName,
                targetDsId: row.targetDsId,
                targetDsName: row.targetDsName
            }})
        },

        show(index, row) {
            this.$nextTick(() => {
                this.$refs.Show.open(row); 
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