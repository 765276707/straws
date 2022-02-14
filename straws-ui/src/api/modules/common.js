import request from '@/server/request'

const FUNC_PATH = "/common"

export default {
    getTables,
}


/**
 * 获取某个数据库下的表
 * @param schema 数据库名
 */
function getTables(migrateId) {
    return request.get(FUNC_PATH+'/getTables', {params: {
        'migrateId': migrateId
    }})
}