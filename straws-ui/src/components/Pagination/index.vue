<template>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-sizes="pageSizes"
        :current-page="pageNum"
        :page-size="pageSize"
        :total="total"
        background
        layout="total, sizes, prev, pager, next, jumper"
        style="text-align: right;">
    </el-pagination>
</template>

<script>
export default {
    name: 'Pagination',
    props: {
        // 传递参数 total (总条数)
        total: {
            type: Number
        }
    },
    data() {
        return {
            pageNum: 1,
            pageSize: 10,
            pageSizes: [10, 20, 30, 50, 100]
        }
    },
    methods: {

        /**
         * @title 更改页码
         * @description 绑定 page-change(pageNum, pageSize) 事件
         */ 
        handleCurrentChange(val) {
            this.pageNum = val
            this.$emit('page-change', this.pageNum, this.pageSize)
        },

        /**
         * @title 更改每页大小
         * @description 绑定 page-change(pageNum, pageSize) 事件
         */
        handleSizeChange(val) {
            this.pageSize = val
            this.$emit('page-change', this.pageNum, this.pageSize)
        }
    }
}
</script>

<style lang="scss" scoped>
.el-pagination {
    padding-top: 10px;
}
</style>