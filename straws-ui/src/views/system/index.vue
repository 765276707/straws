<template>
    <el-card class="system">
        <el-tabs :tab-position="'left'">
            <el-tab-pane label="系统设置">
                <p style="padding-left: 20px;">系统设置</p>
                <el-row>
                    <el-col style="width: 300px; padding-left: 20px;">
                        <el-form ref="form" :model="form" label-width="80px" label-position="top">
                            <el-form-item label="是否强制更新密码" prop="forceUpdatePassword">
                                <el-radio-group v-model="form.forceUpdatePassword">
                                    <el-radio :label="true">是</el-radio>
                                    <el-radio :label="false">否</el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item label="强制更新密码间隔" prop="forceUpdateInterval">
                                <el-input v-model="form.forceUpdateInterval" placeholder="强制更新密码间隔"></el-input>
                            </el-form-item>
                            <el-form-item label="是否加密数据源密码" prop="encDspwdInDb">
                                <el-radio-group v-model="form.encDspwdInDb">
                                    <el-radio :label="true">是</el-radio>
                                    <el-radio :label="false">否</el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="onSubmit">保存</el-button>
                                <el-button type="warning" @click="onReset">重置</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                </el-row>
            </el-tab-pane>
        </el-tabs>
    </el-card>
    
</template>

<script>
export default {
    data() {
        return {
            form: {
                forceUpdatePassword: null,
                forceUpdateInterval: 0,
                encDspwdInDb: null
            }
        }
    },
    created() {
        this.getConfig()
    },
    methods: {
        async getConfig() {
            let response = await this.$API.config.getDefault()   
            this.form = response.data
        },

        async onSubmit() {
            let response = await this.$API.config.updateDefault(this.form)
            this.$message.success(response.message)
            this.getConfig()
        },

        async onReset() {
            let response = await this.$API.config.resetDefault()
            this.$message.success('重置成功')
            this.getConfig()
        }
    }
}
</script>

<style lang="scss" scoped>
.system {
    margin: 30px;
    min-height: 500px;
}
</style>
