<template>

    <el-drawer
    :title="title" 
    :visible.sync="visible"
    v-if="visible"
    size="40%"
    :show-close="false"
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
                    <el-form-item label="来源表名" prop="originTableName">
                        <!-- <el-input v-model="form.originTableName" placeholder="请输入来源表名" style="width: 85%"/> -->
                        <table-select v-model="form.originTableName" :migrateId="form.migrateId" placeholder="请选择来源表名" style="width: 85%"></table-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="目标表名" prop="targetTableName">
                        <el-input v-model="form.targetTableName" placeholder="请输入目标表名" style="width: 85%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="数据转换器" prop="transformers">
                        <script-picker v-model="form.transformers" style="width: 85%"></script-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row v-if="showSQL">
                <el-col :span="24">
                    <el-form-item label="创建目标表SQL" prop="targetTableCreateSql">
                        <!-- <el-input :rows="8" type="textarea" v-model="form.targetTableCreateSql" placeholder="请输入创建目标表SQL" style="width: 85%"/> -->
                        <codemirror v-model="form.targetTableCreateSql" :options="editorOptions" style="width: 85%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row v-if="showSQL">
                <el-col :span="24">
                    <el-form-item label="查询源头表SQL" prop="originTableSelectSql">
                        <!-- <el-input :rows="4" type="textarea" v-model="form.originTableSelectSql" placeholder="请输入查询源头表SQL" style="width: 85%"/> -->
                        <codemirror v-model="form.originTableSelectSql" :options="editorOptions" style="width: 85%;"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row v-if="showSQL">
                <el-col :span="24">
                    <el-form-item label="插入目标表SQL" prop="targetTableInsertSql">
                        <!-- <el-input :rows="4" type="textarea" v-model="form.targetTableInsertSql" placeholder="请输入插入目标表SQL" style="width: 85%"/> -->
                        <codemirror v-model="form.targetTableInsertSql" :options="editorOptions" style="width: 85%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="是否建表" prop="isCreateTable">
                        <el-radio-group v-model="form.isCreateTable">
                            <el-radio :label="true">创建</el-radio>
                            <el-radio :label="false">不创建</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>  
        </el-form>
        <div style="margin-bottom: 20px;">
            <el-row  type="flex" justify="center">
                <el-button type="primary" @click="submit()">确 定</el-button>
                <el-button @click="close()">关 闭</el-button>
            </el-row> 
        </div>
    </el-drawer>

    
</template>

<script>
import {codemirror} from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import "codemirror/theme/material.css"
require("codemirror/mode/sql/sql")

import sqlFormatter from 'sql-formatter'

import TableSelect from '@/views/common/table-select.vue'
import ScriptPicker from '@/views/scripts/script-picker.vue'


export default {
    components: {
        codemirror,
        TableSelect,
        ScriptPicker
    },
    data() {
        return {
            title: '',
            visible: false,
            form: {},
            rule: {
                originTableName: [{ required: true, message: '请输入来源表名', trigger: 'blur' }],
                targetTableName: [{ required: true, message: '请输入目标表名', trigger: 'blur' }]
            },
            showSQL: false,
            editorOptions: {
                tabSize: 4,
                mode: 'text/x-sql',
                theme: 'material',
                lineNumbers: true,
                line: true
            }
        }
    },
    filters: {
        format: function(val) {
            if (!val) return ''
            return sqlFormatter.format(val)
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
                response = await this.$API.migrate_detail.insertEntity(this.form)
            } else {
                // 更新
                response = await this.$API.migrate_detail.updateEntity(this.form)
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
                this.showSQL = true
                this.title = '编辑映射'
            } else{
                this.showSQL = false
                this.title = '新增映射'
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