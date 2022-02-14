import request from '@/server/request'


export default {
    getVerifyCode,
}


/**
 * 获取验证码
 */
function getVerifyCode() {
    return request.get('/verifyCode')
}