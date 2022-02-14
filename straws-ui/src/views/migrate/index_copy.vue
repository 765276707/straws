<template>
    <el-container>
        <el-aside width="300px">
			<el-container>
				<el-header>
					<el-input placeholder="输入关键字进行过滤"  clearable></el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree 
                        ref="menu" 
                        class="menu" 
                        node-key="id" 
                        :data="treeList" 
                        :props="treeProps" 
                        highlight-current 
                        :expand-on-click-node="false" 
                        check-strictly
                        @node-click="menuClick" 
                        >

						<template #default="{node}">
							<span class="custom-tree-node el-tree-node__label">
								<span class="label">
									{{ node.label }}
								</span>
								<span class="do">
									
								</span>
							</span>
						</template>

					</el-tree>
				</el-main>
				<el-footer style="height:51px;">
					<el-button type="primary" size="mini" icon="el-icon-plus"></el-button>
					<el-button type="danger" size="mini" plain icon="el-icon-delete"></el-button>
				</el-footer>
			</el-container>
		</el-aside>

        <el-container>
			<el-main class="nopadding" style="padding:20px;" ref="main">
				adasd
			</el-main>
		</el-container>

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
            treeList: [],
            treeProps: {
                label: 'name'
            },
            loading: false, //表格加载动画
        }
    },
    mounted() {
        // 初始化列表
        this.getList()
    },
    methods: {
        /**
         * @title 获取列表
         */
        async getList() {
            // this.loading = true
            let response = await this.$API.migrate.getList(this.searchText)   
            this.treeList = response.data
            // this.loading = false
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


.el-container {height: 100%;}
.el-aside {border-right: 1px solid #e6e6e6;}
.el-header {border-bottom: 1px solid #e6e6e6;padding:13px 15px;display: flex;justify-content: space-between;align-items: center;}
.el-header .left-panel {display: flex;align-items: center;}
.el-header .right-panel {display: flex;align-items: center;}
.el-header .right-panel > * + * {margin-left:10px;}
.el-footer {background: #fff;border-top: 1px solid #e6e6e6;padding:13px 15px;}
.el-main {padding:15px;}

.custom-tree-node {display: flex;flex: 1;align-items: center;justify-content: space-between;font-size: 14px;padding-right: 24px;height:100%;}
.custom-tree-node .label {display: flex;align-items: center;height: 100%;}
.custom-tree-node .label .el-tag {margin-left: 5px;}
.custom-tree-node .do {display: none;}
.custom-tree-node .do i {margin-left:5px;color: #999;padding:5px;}
.custom-tree-node .do i:hover {color: #333;}

.custom-tree-node:hover .do {display: inline-block;}

</style>