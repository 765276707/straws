<template>
    <!--
        新增或编辑表单
    -->
    <el-dialog 
        :title="title" 
        :visible.sync="visible"
        v-if="visible"
        width="700px"
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
                    <el-form-item label="脚本名称" prop="name">
                        <el-input v-model="form.name" placeholder="请输入脚本名称" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="脚本内容" prop="content">
                        <codemirror v-model="form.content" :options="editorOptions"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="是否启用" prop="isEnabled">
                        <el-radio-group v-model="form.isEnabled">
                            <el-radio :label="true">启用</el-radio>
                            <el-radio :label="false">禁用</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="脚本描述" prop="description">
                        <el-input type="textarea" :rows="3" v-model="form.description" placeholder="请输入脚本内容" style="width: 100%"/>
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
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import "codemirror/theme/material.css"
require("codemirror/mode/groovy/groovy")

export default {
    data() {
        return {
            title: '',
            visible: false,
            form: {},
            rule: {},
            editorOptions: {
                tabSize: 4,
                mode: 'text/x-groovy',
                theme: 'material',
                lineNumbers: true,
                line: true
            }
        }
    },
    components: {
        codemirror
    },
    methods: {
        /**
         * @method 提交表单
         */
        async submit() {
            let response = null;
            if (!this.form.id) {
                // 新增
                response = await this.$API.scripts.insertEntity(this.form)
            } else {
                // 更新
                response = await this.$API.scripts.updateEntity(this.form)
            }
            this.$emit('success', this.form.id)
            this.close()
            this.$message.success(response.message)
        },

        /**
         * 打开表单
         */
        open(row) {
            this.visible = true
            if (row) {
                this.title = '编辑脚本'
                // 赋值
                this.form = Object.assign({}, row)
            } else{
                this.title = '新增脚本'
            }
        },

        /**
         * @method 关闭表单
         */
        close() {
            this.$refs['form'].resetFields()
            this.$refs['form'].clearValidate()
            this.visible = false
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
