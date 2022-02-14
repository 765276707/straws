<template>
    <el-container>
        <el-header class="topbar">
            <div class="left-panel">
                <el-button type="primary" icon="el-icon-plus" @click="save">添加</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
            </div>
            <div class="right-panel">
                <div class="right-panel-search">
                    <el-input v-model="searchText" placeholder="脚本名称"></el-input>
                    <el-button type="primary" icon="el-icon-search" @click="getPage"></el-button>
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
                <el-table-column prop="id"           label="编号"    width="100" ></el-table-column>
                <el-table-column prop="name"         label="脚本名" ></el-table-column>
                <el-table-column prop="hashKey"      label="哈希Key" ></el-table-column>
                <el-table-column prop="content"      label="脚本内容" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="description"  label="描述"     :show-overflow-tooltip="true"></el-table-column>
                <el-table-column label="是否启用">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.isEnabled" type="success" size="mini">启用</el-tag>
                        <el-tag v-else type="danger" size="mini">禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="是否已检测">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.isChecked" type="success" size="mini">安全</el-tag>
                        <el-tag v-else type="info" size="mini">未检测</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            @click="edit(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                            type="text"
                            style="color: #F56C6C"
                            @click="delRow(scope.$index, scope.row)">删除</el-button>  
                    </template>
                </el-table-column>
                <template #empty>
                    <el-empty :description="'暂无数据'" :image-size="100"></el-empty>
                </template>
            </el-table>

            <pagination :total="pageResult.total" @page-change="pageChange"></pagination>
        </el-main>

    
        <save-update ref="SaveUpdate" @success="getPage"></save-update>

        <show ref="Show"></show>

    </el-container>
</template>

<script>
import SaveUpdate from './save-update.vue'
import Show from './show.vue'

export default {
    components: {
        SaveUpdate,
        Show
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
            let response = await this.$API.scripts.getPage(this.searchText)   
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

    
        refreshPage() {
            this.searchText = ''
            this.getPage()
        },

        show(row) {
            this.$nextTick(() => {
                this.$refs.Show.open(row.content); 
            })
        },

        save() {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open({
                    isEnabled: true
                }); 
            })
        },

        edit(index, row) {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open(row); 
            })
        },

        delRow(index, row) {
            this.$confirm('此操作将永久删除该脚本, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                this.$API.scripts.deleteById(row.id).then(response => {
                    this.getPage()
                    this.$message.success(response.message)
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
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