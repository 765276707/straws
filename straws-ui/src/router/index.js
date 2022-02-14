import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '控制台', icon: 'dashboard' }
    }]
  },

  {
    path: '/datasource',
    component: Layout,
    children: [
      {
        path: 'datasource',
        name: 'DataSource',
        component: () => import('@/views/datasource/index'),
        meta: { title: '数据源管理', icon: 'database' }
      }
    ]
  },

  {
    path: '/migrate',
    component: Layout,
    children: [
      {
        path: 'migrate',
        name: 'Migrate',
        component: () => import('@/views/migrate/index'),
        meta: { title: '迁移管理', icon: 'migrate' }
      },
      {
        path: 'details',
        name: 'Details',
        hidden: true,
        component: () => import('@/views/migrate_details/index'),
        meta: { title: '数据迁移', icon: 'form' }
      }
    ]
  },

  {
    path: '/scripts',
    component: Layout,
    children: [
      {
        path: 'scripts',
        name: 'Scripts',
        component: () => import('@/views/scripts/index'),
        meta: { title: '脚本管理', icon: 'scripts' }
      }
    ]
  },

  {
    path: '/task',
    component: Layout,
    children: [
      {
        path: 'task',
        name: 'Task',
        component: () => import('@/views/task/index'),
        meta: { title: '定时同步', icon: 'schedule' }
      }
    ]
  },

  {
    path: '/users',
    component: Layout,
    children: [
      {
        path: 'users',
        name: 'Users',
        component: () => import('@/views/users/index'),
        meta: { title: '用户管理', icon: 'user' }
      },
      {
        path: 'center',
        name: 'UserCenter',
        component: () => import('@/views/users/center'),
        meta: { title: '个人中心', icon: 'form' },
        hidden: true
      }
    ]
  },

  {
    path: '/role',
    component: Layout,
    children: [
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/role/index'),
        meta: { title: '角色管理', icon: 'form' }
      }
    ],
    hidden: true
  },

  {
    path: '/system',
    component: Layout,
    children: [
      {
        path: 'system',
        name: 'System',
        component: () => import('@/views/system/index'),
        meta: { title: '系统配置', icon: 'settings' }
      }
    ]
  },

  {
    path: '/log',
    component: Layout,
    meta: { title: '系统日志', icon: 'logs' },
    children: [
      {
        path: 'oplog',
        name: 'Oplog',
        component: () => import('@/views/oplog/index'),
        meta: { title: '操作日志', icon: 'logs' }
      },
      {
        path: 'loginlog',
        name: 'Loginlog',
        component: () => import('@/views/loginlog/index'),
        meta: { title: '登录日志', icon: 'logs' },
      }
    ]
  },

  // {
  //   path: '/tools',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'tools',
  //       name: 'Tools',
  //       component: () => import('@/views/tools/index'),
  //       meta: { title: '常用工具', icon: 'tools' }
  //     }
  //   ]
  // },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
 

  // {
  //   path: 'external-link',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
  //       meta: { title: 'External Link', icon: 'link' }
  //     }
  //   ]
  // },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
