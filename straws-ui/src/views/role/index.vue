<template>
    <el-container>
        <el-header class="topbar">
            <div class="left-panel">
                <el-button type="success" icon="el-icon-plus" @click="save">添加</el-button>
                <el-button type="warning" icon="el-icon-refresh" @click="refresh">刷新</el-button>
            </div>
            <div class="right-panel">
                <div class="right-panel-search">
                    <el-input v-model="searchText" placeholder="编号/名称/类型"></el-input>
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
                <el-table-column type="selection"   width="55" ></el-table-column>
                <el-table-column prop="id"          label="编号"    width="100" ></el-table-column>
                <el-table-column prop="roleName"    label="角色名" ></el-table-column>
                <el-table-column prop="createTime"   label="创建时间" ></el-table-column>
                <el-table-column prop="updateTime" label="修改时间" ></el-table-column>
                <el-table-column label="操作" width="220">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="edit(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                            type="text"
                            style="color: #F56C6C"
                            icon="el-icon-delete"
                            @click="deleteInRow(scope.$index, scope.row)">删除</el-button>        
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
    data() {
        return {
            searchText: '',
            pageResult: { 
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false,
        }
    },
    components: {
        SaveUpdate
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
            let response = await this.$API.role.getPage(this.searchText)   
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
                this.$refs.SaveUpdate.open({
                    gender: '男',
                    status: 1
                }) 
            })
        },

        edit(index, row) {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open(row) 
            })
        },

        deleteInRow(index, row) {
            this.$confirm('此操作将会删除该角色，仅限超级管理员进行操作，是否继续?', '危险操作', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                this.$prompt('请输入您的密码，以确认删除', '确认删除', {
                    confirmButtonText: '确定删除',
                    cancelButtonText: '取消',
                    inputType: 'password'
                }).then(({ value }) => {
                    
                    this.$API.role.deleteById(
                        {
                            'password': value,
                            'id': row.id
                        }
                    ).then(response => {
                        this.getPage()
                        this.$message.success(response.message)
                    })

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    }) 
                })


            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                })
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