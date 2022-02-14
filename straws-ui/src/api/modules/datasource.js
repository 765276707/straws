import request from '@/server/request'

const FUNC_PATH = "/sys/datasource"

export default {
    getById,
    getList,
    getPage,
    insertEntity,
    updateEntity,
    deleteById,
    deleteAfterCheckPwd
}

/**
 * 查询数据源
 * @param id 数据源编号
 */
function getById(id) {
    return request.get(FUNC_PATH+'/getById', {
        params: {'id': id}
    })
}

/**
 * 查询数据源列表
 * @param searchText 查询参数
 */
function getList(searchText) {
    return request.get(FUNC_PATH+'/getList', {params: {
        'searchText': searchText
    }})
}

/**
 * 查询数据源列表
 * @param searchText 查询参数
 */
function getPage(searchText) {
    return request.get(FUNC_PATH+'/getPage', {params: {
        'searchText': searchText
    }})
}

/**
 * 新增数据源
 * @param data 数据源参数
 */
function insertEntity(data) {
    return request.post(FUNC_PATH+'/insertEntity', data)
}

/**
 * 编辑数据源
 * @param data 数据源参数
 */
function updateEntity(data) {
    return request.put(FUNC_PATH+'/updateEntity', data)
}

/**
 * 删除数据源
 * @param {*} id 数据源编号
 */
function deleteById(id) {
    return request.delete(FUNC_PATH+'/deleteById', {
        params: {'id': id}
    })
}

function deleteAfterCheckPwd(data) {
    return request.post(FUNC_PATH+'/deleteAfterCheckPwd', data)
}