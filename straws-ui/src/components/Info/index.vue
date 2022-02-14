<template>
    <!--
        用户选择器
    -->
    <div>    
        <el-input v-model="value" placeholder="请选择数据源">
            <el-button slot="append" icon="el-icon-timer"  @click="openPickerUser"></el-button>
        </el-input>

        <!--
           查询可选用户对话框
        -->
        <el-dialog 
            title="选择数据源" 
            :visible.sync="userDialog.visible"
            v-if="userDialog.visible"
            width="850px"
            :show-close="false"
            :close-on-click-modal="false"
            :close-on-press-escape="false"
            append-to-body>

            <div class="topbar">
                <div class="left-panel">
                    <el-button type="primary" icon="el-icon-check" @click="submitPicker()">确 定</el-button>
                    <el-button type="danger" plain icon="el-icon-close" @click="closeDialog()">取 消</el-button>
                </div>
                <div class="right-panel">
                    <div class="right-panel-search">
                        <el-input placeholder="请输入内容" v-model="searchForm.searchText" class="input-with-select">
                            <el-button slot="append" icon="el-icon-search" @click="getUserPage()"></el-button>
                        </el-input>
                    </div>
                </div>
            </div>

            <el-table
                ref="userTable"
                :data="pageDetail.list"
                max-height="250"
                highlight-current-row
                style="width: 100%;">
                <el-table-column property="id" label="编号" width="60"></el-table-column>
                <el-table-column property="name" label="数据源名"></el-table-column>
                <!-- <el-table-column property="productName" label="产品类型"></el-table-column> -->
                <el-table-column label="产品类型">
                    <template slot-scope="scope">
                        <el-image v-if="scope" :src="'http://127.0.0.1:8080/porter/' + scope.row.productIcon" style="width:30px; height:30px;"></el-image>
                        {{ scope.row.productName }}
                    </template>
                </el-table-column>
                <template #empty>
					<el-empty :description="'暂无数据'" :image-size="100"></el-empty>
				</template>
            </el-table>

            <pagination :total="pageDetail.total" @page-change="pageChange" style="padding-top: 10px;"></pagination>
        </el-dialog>
    </div>
</template>

<script>
export default {
    props: {
        value: {
            type: Number,
            default: -1
        }
    },
    data() {
        return {
            pageDetail: {      //可选用户分页数据  
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            searchForm: {     //可选用户查询表单
                pageNum: 1,
                pageSize: 10,
                searchText: '',
                // excludes: [] 
            },
            selectedRows: [], //可选用户列表中为选中状态的指定用户
            tableData: [],    //已选择的指定用户     
            userDialog: {
                visible: false,
            }
        }
    },
    created() {
        this.getUserPage()
    },
    watch: {
        tableData(newVal) {
            // 解析返回给父组件的值
            let retVal = newVal.map(item => item.username)
            this.$emit('rows-selected', retVal)
        }
    },
    methods: {
        /**
         * @title 查询可选用户列表
         */
        async getUserPage() {
            this.loading = true
            let response = await this.$API.datasource.getPage('')   
            this.pageDetail = response.data
            this.loading = false
        },

        /**
         * @title 打开选中对话框
         */
        openPickerUser() {
            this.getUserPage()
            this.userDialog.visible = true
        },


        /**
         * @title 提交选中的用户到选中表
         */
        submitPicker() {
            // 获取当前选中的行
            // this.tableData = this.tableData.concat(this.selectedRows)
            // this.searchForm.excludes = this.searchForm.excludes.concat(
            //     this.selectedRows.map(row => {
            //         return row.username
            //     })
            // )
            this.closeDialog()
        },


        /**
         * @title 更改分页参数
         */
        pageChange(pageNum, pageSize) {
            this.searchForm.pageNum = pageNum
            this.searchForm.pageSize = pageSize
            this.getUserPage()
        },


        /**
         * @title 查询可选用户列表选中状态变更时触发
         */
        handleSelectionChange(val) {
            this.selectedRows = val
        },


        /**
         * @title 关闭新增或编辑表单对话框
         */
        closeDialog() {
            this.resetForm('searchForm')
            this.userDialog.visible = false
        },


        /**
         * @title 重置表单
         * @param formName 表单名称
         */
        resetForm(formName) {
            this.$refs[formName].resetFields()
            this.$refs[formName].clearValidate()
        }
    }
}
</script>

<style lang="scss" scope>
.topbar {
    height: 50px;border-bottom: 1px solid #ebeef5; background: #fff;box-shadow: 0 1px 4px rgba(0,21,41,.08);display: flex;justify-content:space-between;

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