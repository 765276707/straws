import request from '@/server/request'

const FUNC_PATH = "/sys/script"

export default {
    getById,
    getList,
    getPage,
    insertEntity,
    updateEntity,
    deleteById,
}


function getById(id) {
    return request.get(FUNC_PATH+'/getById', {
        params: {'id': id}
    })
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

function deleteById(id) {
    return request.delete(FUNC_PATH+'/deleteById', {
        params: {'id': id}
    })
}
