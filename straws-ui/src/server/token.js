import Cookies from 'js-cookie'

/**
 * 后台令牌存储工具类
 * @Cache: Cookies
 */
const ACCESS_TOKEN_KEY  = 'access_token'

// 访问令牌
export function getAccessToken() {
    return Cookies.get(ACCESS_TOKEN_KEY)
}

export function setAccessToken(accessToken) {
    return Cookies.set(ACCESS_TOKEN_KEY, accessToken)
}

export function removeAccessToken() {
    return Cookies.remove(ACCESS_TOKEN_KEY)
}




