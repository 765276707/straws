import axios from 'axios'
// import router from '@/router/index'
// import store from '@/store/index'
import { Message, MessageBox } from 'element-ui'
import { getAccessToken } from '@/server/token'


// 从对应的env配置中读取路径
axios.defaults.baseURL = 'http://127.0.0.1:9000/straws';
// 设置超时时间
axios.defaults.timeout = 10000
// 设置跨域是否携带凭证
axios.defaults.withCredentials = true
// 设置响应状态码（默认是2xx为成功，其余皆为失败）
axios.defaults.validateStatus = status => {
    return /^(2|3)\d{2}$/.test(status);
}
// 设置post的请求头，告知服务器请求主体的数据格式 (默认是：application/json)
// axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded'
// axios.defaults.transformRequest = data => qs.stringify(data)


/**
 * 请求拦截器
 */
axios.interceptors.request.use(config => {    
    // 需要访问令牌
    let accessToken = getAccessToken();
    accessToken && (config.headers.Authorization = accessToken);
    return config;
}, error => {
    return Promise.reject(error);
})


/**
 * 响应拦截器
 */
axios.interceptors.response.use(
    // 2xx    
    response => {
        let {success, message, data} = response.data
        // 业务失败
        if (!success) {
            errorMessage(message, 3)
            return Promise.reject()
        }
        // 业务成功
        return {success, message, data}
    }, 

    // 非2xx
    error => {
        let { response } = error;
        if (response) 
        {
            // 服务器有响应 4xx 或 5xx
            let errorMsg = response.data.errorMessage || '服务器出错了'
            errorMessage(errorMsg, 5);
        } 
        
        else
        {
            // 服务器无任何响应（服务器未开启或客户端未联网）
            if (!window.navigator.onLine) {
                // 客户端未联网，一般跳转到断网页面
                errorMessage('链接服务器失败，请检查网络是否正常', 5)
                return
            }
            return Promise.reject(error)
        }

        return Promise.reject(error)
    }
)

/**
 * 输出错误提示
 * @param {*} message 错误消息
 * @param {*} showTime 显示时间，单位：秒
 */
function errorMessage(message, showTime) {
    Message({
        message: message,
        type: 'error',
        duration: showTime * 1000
    })
}


// 最后返回封装好的axios
export default axios