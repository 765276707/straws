/**
 * 后台Http接口地址统一入口
 * @author Pristine Xu 
 * @create 2021-12-16
 */
import common from './modules/common'
import datasource from './modules/datasource'
import connection from './modules/connection'
import migrate from './modules/migrate'
import migrate_detail from './modules/migrate-detail'
import task from './modules/task'
import task_log from './modules/task-log'
import scripts from './modules/scripts'
import dict from './modules/dict'
import auth from './modules/auth'
import user from './modules/user'
import role from './modules/role'
import verifycode from './modules/verifycode'
import statics from './modules/statics'
import config from './modules/config'
import oplog from './modules/oplog'
import loginlog from './modules/loginlog'

export default {
    common,         // 公共资源接口
    datasource,     // 数据源接口
    connection,     // Jdbc相关接口
    scripts,        // 脚本接口
    migrate,        // 数据迁移接口
    migrate_detail, // 数据映射接口
    dict,           // 数据字典接口
    task,           // 定时同步接口
    task_log,       // 定时同步日志接口
    auth,           // 登录接口
    user,           // 用户管理接口
    role,           // 角色管理接口
    verifycode,     // 验证码模块
    statics,        // 统计接口，对应控制台页面数据
    config,         // 全局配置
    oplog,          // 操作日志
    loginlog        // 登录日志
}