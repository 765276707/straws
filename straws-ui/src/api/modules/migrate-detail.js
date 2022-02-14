import request from '@/server/request'

const FUNC_PATH = "/sys/migrate/detail"

export default {
    getById,
    getPage,
    insertEntity,
    updateEntity,
    deleteById,
    createDetails,
    clearDetails,
    migrateTable,
    migrateData
}



function getById(id) {
    return request.get(FUNC_PATH+'/getById', {
        params: {'id': id}
    })
}


function getPage(migrateId) {
    return request.get(FUNC_PATH+'/getPage', {params: {
        'migrateId': migrateId
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


function createDetails(id) {
    return request.get(FUNC_PATH+'/createDetails', {
        params: {'migrateId': id}
    })
}

function clearDetails(id) {
    return request.delete(FUNC_PATH+'/clearDetails', {
        params: {'migrateId': id}
    })
}

function migrateTable(id) {
    return request.get(FUNC_PATH+'/migrateTable', {
        params: {'migrateId': id}
    })
}

function migrateData(id) {
    return request.get(FUNC_PATH+'/migrateData', {
        params: {'migrateId': id}
    })
}