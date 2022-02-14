import request from '@/server/request'

const FUNC_PATH = "/sys/conf"

export default {
    getDefault,
    updateDefault,
    resetDefault
}


function getDefault() {
    return request.get(FUNC_PATH+'/getDefault')
}

function updateDefault(data) {
    return request.put(FUNC_PATH+'/updateDefault', data)
}

function resetDefault() {
    return request.put(FUNC_PATH+'/resetDefault')
}