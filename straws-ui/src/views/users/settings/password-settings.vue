<template>
    <div>
        <el-row>
            <el-col style="padding-left: 20px;">
                <el-alert
                title="定期更换密码，更有效的保护您的账号安全"
                type="warning"
                description="不建议您使用过于简单的密码或容易被猜到的密码，如多个重复或生日：666888、19901002"
                show-icon></el-alert>
            </el-col>
        </el-row>
        <el-row>
            <el-col style="width: 300px; padding-left: 20px;">
                <el-form ref="form" :model="form" label-width="80px" label-position="top">
                    <el-form-item label="原始密码">
                        <el-input type="password" v-model="form.oldPassword" placeholder="请输入原始密码"></el-input>
                    </el-form-item>
                    <el-form-item label="新的密码">
                        <el-input type="password" v-model="form.newPassword" placeholder="请输入新的密码"></el-input>
                    </el-form-item>
                    <el-form-item label="重复密码">
                        <el-input type="password" v-model="form.repPassword" placeholder="请输入重复密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">确认修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </div>
    
</template>

<script>
export default {
    name: 'PasswordSettings',
    data() {
        return {
            form: {
                oldPassword: '',
                newPassword: '',
                repPassword: ''
            }
        }
    },
    methods: {
        onSubmit() {
            this.$confirm('变更密码需要重新进行登录，是否继续?', '变更提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                this.$API.user.modifyPassword(this.form).then(response => {
                    this.$message.success(response.message)
                    setTimeout(() => {
                        this.logout()
                    }, 3000);
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消变更'
                })
            })
            
        },

        async logout() {
            await this.$store.dispatch('user/logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        }
    }
}
</script>

<style lang="scss" scoped>

</style>