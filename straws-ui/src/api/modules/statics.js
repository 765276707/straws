import request from '@/server/request'

const FUNC_PATH = "/statics"

export default {
    getStaticsList,
    getGlobalEnv,
    getLatestTaskLogs
}


function getStaticsList() {
    return request.get(FUNC_PATH+'/getStaticsList')
}

function getGlobalEnv() {
    return request.get(FUNC_PATH+'/getGlobalEnv')
}

function getLatestTaskLogs() {
    return request.get(FUNC_PATH+'/getLatestTaskLogs')
}
