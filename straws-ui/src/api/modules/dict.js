import request from '@/server/request'

const FUNC_PATH = "/sys/dict"

export default {
    getList
}

function getList(dictType) {
    return request.get(FUNC_PATH+'/getList', {params: {
        'dictType': dictType
    }})
}
