// 引入组件样式
import { MessageBox } from 'element-ui'
import NProgress from 'nprogress' 
import 'nprogress/nprogress.css' 
// router和store
import router from '@/router'
import store from '@/store'
// 令牌
import { getAccessToken } from '@/server/token' 
import getPageTitle from '@/utils/get-page-title'

// NProgress配置
NProgress.configure({ showSpinner: false }) 

// 访问白名单
const whiteList = ['/login'] 

router.beforeEach(async(to, from, next) => {
  
    // 开启进度条和获取页面标题
    NProgress.start()
    document.title = getPageTitle(to.meta.title)

    // 获取访问令牌
    const hasToken = getAccessToken()

    if (hasToken) {
        if (to.path === '/login') 
        {
            // 当前已登录且处于登录页，则直接跳转至主页
            next({ path: '/' })
            NProgress.done()
        } 
        else 
        {
            // 当前已登录，判断是否有登录用户信息
            const hasAuthority = store.getters.roles && store.getters.roles>0
            if (hasAuthority) 
            {
                next()
            } 
            else 
            {
                console.log("  hasAuthority:  false" )
                try {
                    // 获取登录用户信息，重置路由表
                    console.log("  getInfo" )
                    await store.dispatch('user/getInfo')
                    // const asyncRoutes = await store.dispatch('asyncRoute/generateRoutes')
                    // router.addRoutes(asyncRoutes)
                    // 继续访问
                    next()
                    // next({ ...to, replace: true })
                } catch (error) {
                    // 未分配任何菜单或权限
                    redirectLogin('您的账号授权异常', next, to)  
                }
            }
        }
    } 
    else 
    {
        if (whiteList.indexOf(to.path) !== -1) {
            // 白名单放行
            next()
        } else {
            // 重定向至登录页
            redirectLogin('您的身份认证已过期，请重新登录', next, to)      
        }
    }
})

/**
 * 重定向到登录页
 * @param {*} message 
 * @param {*} next 
 * @param {*} to 
 */
function redirectLogin(message, next, to) {
  MessageBox.alert(message, '提示', {
      confirmButtonText: '确定',
      type: 'error',
      center: true
  }).then(() => {
      store.dispatch('user/resetToken').then(() => {
        next(`/login?redirect=${to.path}`)
      })
      NProgress.done()
  })
}

/**
 * 路由钩子结束
 */
router.afterEach(() => {
  NProgress.done()
})
