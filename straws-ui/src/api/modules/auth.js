import request from '@/server/request'

const FUNC_PATH = "/auth"

export default {
    login,
    logout,
    getInfo
}

/**
 * 登录
 * @param data 
 */
function login(data) {
    return request.post(FUNC_PATH+'/login', data)
}

function logout() {
    return request.get(FUNC_PATH+'/logout')
}

function getInfo() {
    return request.get(FUNC_PATH+'/getInfo')
}
