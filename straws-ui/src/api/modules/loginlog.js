import request from '@/server/request'

const FUNC_PATH = "/sys/loginlog"

export default {
    getPage
}

function getPage(searchText) {
    return request.get(FUNC_PATH+'/getPage', {params: {
        'searchText': searchText
    }})
}
