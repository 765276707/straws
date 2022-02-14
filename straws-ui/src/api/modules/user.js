import request from '@/server/request'

const FUNC_PATH = "/sys/user"

export default {
    getById,
    getByLogin,
    getList,
    getPage,
    insertEntity,
    updateEntity,
    modifyPassword,
    resetPassword,
    deleteById,
    getAvatar
}


function getById(id) {
    return request.get(FUNC_PATH+'/getById', {
        params: {'id': id}
    })
}

function getByLogin() {
    return request.get(FUNC_PATH+'/getByLogin')
}

function getList(searchText) {
    return request.get(FUNC_PATH+'/getList', {params: {
        'searchText': searchText
    }})
}

function getPage(searchText) {
    return request.get(FUNC_PATH+'/getPage', {params: {
        'searchText': searchText
    }})
}

function insertEntity(data) {
    return request.post(FUNC_PATH+'/insertEntity', data)
}

function updateEntity(data) {
    return request.put(FUNC_PATH+'/updateEntity', data)
}

function modifyPassword(data) {
    return request.put(FUNC_PATH+'/modifyPassword', data)
}

function resetPassword(data) {
    return request.put(FUNC_PATH+'/resetPassword', data)
}

function deleteById(data) {
    return request.delete(FUNC_PATH+'/deleteById', {
        params: data
    })
}

function getAvatar() {
    return request.defaults.baseURL
}
