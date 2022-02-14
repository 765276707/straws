<template>
    <div class="page-container">
        <el-row>
            <!-- 个人详情展示 -->
            <el-col :xs="24" :span="7" >
                <div class="left">
                    <el-card>
                        <!-- 头像 -->
                        <el-row type="flex" justify="center">
                            <el-avatar :size="120" :src="avatarURL"></el-avatar>
                        </el-row>
                        
                        <!-- 用户名、签名 -->
                        <el-row type="flex" justify="center" style="padding-top: 10px; font-size: 20px;">
                            <b>{{ loginUser.username }}</b>
                        </el-row>
                        <el-row type="flex" justify="center" style="padding-top: 5px; font-size: 12px;">
                            {{ loginUser.remark }}
                        </el-row>
                        
                        <!-- 职位、部门、地址... -->
                        <el-row type="flex" justify="left" style="padding-top: 20px; padding-left:30px; font-size: 14px;">
                            <i class="el-icon-phone-outline"></i>{{ loginUser.phoneNumber }}
                        </el-row>
                        <el-row type="flex" justify="left" style="padding-top: 10px; padding-left:30px; font-size: 14px;">    
                            <i class="el-icon-date"></i>{{ loginUser.email }}
                        </el-row>
                        

                    </el-card>
                </div>
            </el-col>

            <!-- 个人关联信息 -->
            <el-col :xs="24" :span="17" >
                <div class="right">
                    <el-card>
                        <el-tabs :tab-position="'left'">
                            <el-tab-pane label="基本设置">
                                <p style="padding-left: 20px;">基本设置</p>
                                <basic-settings :value="loginUser"></basic-settings>
                            </el-tab-pane>
                            <el-tab-pane label="修改密码">
                                <p style="padding-left: 20px;">修改密码</p>
                                <password-settings></password-settings>
                            </el-tab-pane>
                        </el-tabs>
                    </el-card>
                </div>           
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'

import BasicSettings from './settings/basic-settings'
import PasswordSettings from './settings/password-settings.vue'

export default {
    name: 'UserCenter',
    components: {
        BasicSettings,
        PasswordSettings
    },
    data() {
        return {
            loginUser: {
                avatar: ''
            }
        }
    },
    computed: {
        ...mapGetters([
            'name'
        ]),
        // 头像展示地址
        avatarURL() {
            let userAvatar = this.loginUser.avatar
            return this.$API.user.getAvatar() + this.loginUser.avatar
        }
    },
    created() {
        this.getLoginUser()
    },
    methods: {

        /**
         * 获取当前登录用户
         */
        async getLoginUser() {
            let response = await this.$API.user.getByLogin()   
            this.loginUser = response.data
        },

    }
}
</script>


<style lang="scss" scoped>
.left {
    margin: 10px;
}

.right {
    margin: 10px;
}

.el-divider--horizontal{
     margin: 8px 0;
     background: 0 0;
     border-top: 1px dashed #e8eaec;
 }


</style>