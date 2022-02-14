<template>
  <div class="login_container">
    <div class="login_box" 
        :class="{'login_box_height_for_password': loginMode==='password', 
                 'login_box_height_for_smscode' : loginMode==='smscode'}">
      <div class="logo_box">
        <img src="../../assets/logo/logo.png" />   
      </div>
      <div class="form_box">
        <login-mode-password v-if="loginMode === 'password'"></login-mode-password>
      </div>
    </div>
  </div>
</template>

<script>
import LoginModePassword from './login-mode-password'

export default {
    name: "Login",
    components: {
        LoginModePassword
    },
    data() {
        return {
          // 登录模式, 此处预留，方便添加多种方式登录
          loginMode: 'password' 
        }
    },
    methods: {
        
        /**
         * 更改登录模式
         * @param mode 登录模式
         * @description password : 账号密码模式
         * @description smscode  : 短信验证码模式
         */
        triggerloginMode(mode) {
            this.loginMode = mode
        }

    }
};
</script>

<style style="scss" scoped>
.login_container {
  background-color: #2b4b6b;
  height: 100%;
}

.login_box {
  background-color: #fff;
  width: 420px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 1%;
}

.login_box_height_for_password {
  height: 350px;
}

.login_box_height_for_smscode {
  height: 300px;
}

.logo_box {
  width: 150px;
  height: 150px;
  background-color: #fff;
  border-radius: 50%;
  border: 1px;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 5px;
  box-shadow: 3px 3px 20px gray;
}

.logo_box img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #cccccc;
}

.logo_box .el-link {
  width: 100%;
  padding-top: 10px;
  text-align: center;
}

.form_box {
  position: absolute;
  top: 100px;
  width: 100%;
  padding: 10px 20px;
  box-sizing: border-box;
}

</style>