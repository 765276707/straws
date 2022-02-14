<template>
    <el-container>
        <el-header class="topbar">
            <div class="left-panel">
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
                <el-table-column prop="loginUser"    label="登录账号" ></el-table-column>
                <el-table-column prop="loginMode"    label="登录方式" ></el-table-column>
                <el-table-column prop="loginTime"    label="登录时间" ></el-table-column>
                <el-table-column prop="loginIp"      label="登录IP" ></el-table-column>
                <el-table-column prop="loginOs"   label="操作系统" ></el-table-column>
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
            let response = await this.$API.loginlog.getPage(this.searchText)   
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