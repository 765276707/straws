<template>
    <!--
        新增或编辑表单
    -->
    <el-dialog 
        :title="title" 
        :visible.sync="visible"
        v-if="visible"
        width="600px"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        append-to-body>
        <el-form  ref="FORM" :model="form" :rules="rule" label-width="120px">
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
                    <el-form-item label="数据源名" prop="name">
                        <el-input v-model="form.name" placeholder="请输入数据源名" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="产品类型" prop="typeId">
                        <dict-select v-model="form.typeId" :type="form.dictType" :placeholder="'请选择产品类型'" style="width: 100%"></dict-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="驱动类" prop="driverClassName">
                        <el-input v-model="form.driverClassName" placeholder="请输入完整的驱动类名" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="JDBC地址" prop="jdbcUrl">
                        <el-input type="textarea" rows="5" v-model="form.jdbcUrl" placeholder="请输入完整的JDBC地址" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="数据源账号" prop="username">
                        <el-input v-model="form.username" placeholder="请输入数据源账号" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="数据源密码" prop="password">
                        <el-input type="password" v-model="form.password" placeholder="请输入数据源密码" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="启用状态" prop="status">
                        <el-radio-group v-model="form.status">
                            <el-radio :label="1">启用</el-radio>
                            <el-radio :label="2">禁用</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="info" plain @click="checkConn" :loading="loading">测试连接...</el-button>
            <el-button type="primary" @click="submit()">确 定</el-button>
            <el-button @click="close()">关 闭</el-button>
        </div>
    </el-dialog>
</template>

<script>
import DictSelect from '../common/dict-select.vue'

export default {
    data() {
        return {
            title: '',
            visible: false,
            form: {
                
            },
            rule: {
                name: [{ required: true, message: '请输入数据源名', trigger: 'blur' }],
                typeId: [{ required: true, message: '请选择数据源类型', trigger: 'blur' }],
                driverClassName: [{ required: true, message: '请输入驱动类', trigger: 'blur' }],
                jdbcUrl: [{ required: true, message: '请输入JDBC地址', trigger: 'blur' }],
                username: [{ required: true, message: '请输入数据源账号', trigger: 'blur' }],
                password: [{ required: true, message: '请输入数据密码', trigger: 'blur' }],
            },
            dictType: 'db_type',
            loading: false
        }
    },
    components: {
        DictSelect
    },
    methods: {
        /**
         * 提交表单
         */
        async submit() {
            if (!this.valid('FORM')) return
            // 校验通过
            let response = null;
            if (!this.form.id) {
                // 新增
                response = await this.$API.datasource.insertEntity(this.form)
            } else {
                // 更新
                response = await this.$API.datasource.updateEntity(this.form)
            }
            this.$emit('success', this.form.id)
            this.$message.success(response.message)
            this.close()
        },

        /**
         * 测试数据库连接
         */
        checkConn() {
            if (!this.valid('FORM')) return
            // 校验通过
            this.loading = true
            try {
                this.$API.connection.checkConnection(this.form).then(response => {
                    if (response.success) {
                        this.$message.success(response.message)
                    } else {
                        this.$message.error(response.message)
                    }
                })
            } finally {
                this.loading = false
            }
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
                this.title = '编辑数据源'    
            } else {
                this.title = '新增数据源'
            }
            // 赋值
            this.form = Object.assign({}, row)
        },

        /**
         * 关闭表单
         */
        close() {
            this.$refs['FORM'].resetFields()
            this.$refs['FORM'].clearValidate()
            this.visible = false
        }
    }
}
</script>

<style>

</style>