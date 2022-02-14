<template>
    <!--
        新增或编辑表单
    -->
    <el-dialog 
        :title="title" 
        :visible.sync="visible"
        v-if="visible"
        width="500px"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        append-to-body>
        <el-form  ref="form" :model="form" :rules="rule" label-width="120px">
            <!--隐藏id字段-->
            <el-row>
                <!--隐藏id字段-->
                <el-col :span="24" v-show="false">
                    <el-form-item prop="id" v-show="false">
                        <el-input v-model="form.id"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="任务名" prop="name">
                        <el-input v-model="form.name" placeholder="请输入任务名" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="来源数据源" prop="originDsId">
                        <datasource-select v-model="form.originDsId" :placeholder="'请选择来源数据源'" style="width: 100%"></datasource-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="目标数据源" prop="targetDsId">
                        <datasource-select v-model="form.targetDsId" :placeholder="'请选择目标数据源'" style="width: 100%"></datasource-select>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submit()">确 定</el-button>
            <el-button @click="close()">关 闭</el-button>
        </div>
    </el-dialog>
</template>

<script>
import DatasourceSelect from '@/views/datasource/select.vue'

export default {
    components: {
        DatasourceSelect
    },
    data() {
        return {
            title: '',
            visible: false,
            form: {
                
            },
            rule: {
                name: [{ required: true, message: '请输入迁移任务名称', trigger: 'blur' }],
                originDsId: [{ required: true, message: '请选择来源数据源', trigger: 'blur' }],
                targetDsId: [{ required: true, message: '请选择目标数据源', trigger: 'blur' }]
            }
        }
    },
    methods: {
        /**
         * 提交表单
         */
        async submit() {
            if (!this.valid('form')) return
            // 校验通过
            let response = null;
            if (!this.form.id) {
                // 新增
                response = await this.$API.migrate.insertEntity(this.form)
            } else {
                // 更新
                response = await this.$API.migrate.updateEntity(this.form)
            }
            this.$emit('success', this.form.id)
            this.$message.success(response.message)
            this.close()
        },

        /**
         * 校验表单
         */
        valid(formRef) {
            let res = false;
            this.$refs[formRef].validate((valid) => {
                if (valid) {
                    res = valid
                }
            })
            return res;
        },

        /**
         * 打开表单
         */
        open(row) {
            this.visible = true
            if (row.id) {
                this.title = '编辑迁移任务'
            } else{
                this.title = '新增迁移任务'
            }
            // 赋值
            this.form = Object.assign({}, row)
        },

        /**
         * 关闭表单
         */
        close() {
            this.$refs['form'].resetFields()
            this.$refs['form'].clearValidate()
            this.visible = false
        }
    }
}
</script>

<style>

</style>