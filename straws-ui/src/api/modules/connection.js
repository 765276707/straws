import request from '@/server/request'

const FUNC_PATH = "/common/conn"

export default {
    checkConnection,
}


/**
 * 测试链接
 * @param data 数据源参数
 */
 function checkConnection(data) {
    return request.post(FUNC_PATH + '/check', data)
}