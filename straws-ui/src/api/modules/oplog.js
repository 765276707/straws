import request from '@/server/request'

const FUNC_PATH = "/sys/oplog"

export default {
    getList,
    getPage
}


function getList() {
    return request.get(FUNC_PATH+'/getList')
}

function getPage(searchText) {
    return request.get(FUNC_PATH+'/getPage', {params: {
        'searchText': searchText
    }})
}
