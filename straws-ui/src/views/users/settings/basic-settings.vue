<template>
    <el-row>
        <el-col style="width: 300px; padding-left: 20px;">
            <el-form ref="form" :model="form" label-width="80px" label-position="top">
                <el-form-item label="真实姓名" prop="realname">
                    <el-input v-model="form.realname" placeholder="请输入真实姓名"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio-group v-model="form.gender">
                        <el-radio :label="'男'"></el-radio>
                        <el-radio :label="'女'"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="联系电话" prop="phoneNumber">
                    <el-input v-model="form.phoneNumber" placeholder="请输入联系电话"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入电子邮箱"></el-input>
                </el-form-item>
                <el-form-item label="个人简介" prop="remark">
                    <el-input type="textarea" v-model="form.remark" :rows="5" placeholder="个人简介"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">更新基本资料</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script>
export default {
    name: 'BasicSettings',
    props: {
        value: {
            type: Object
        }
    },
    data() {
        return {
            // form: {
            //     username: '',
            //     gender: '',
            //     phoneNumber: '',
            //     email: '',
            //     remark: ''
            // }
        }
    },
    computed: {
        form() {
            return this.value
        }
    },
    methods: {
        async onSubmit() {
            let response = await this.$API.user.updateEntity(this.form)
            this.$message.success(response.message)
        }
    }
}
</script>

<style lang="scss" scoped>

</style>