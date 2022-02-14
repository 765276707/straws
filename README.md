# straws <a href='https://gitee.com/xu_zhibin/straws/stargazers'><img src='https://gitee.com/xu_zhibin/straws/badge/star.svg?theme=white' alt='star'></img></a> <a href='https://gitee.com/xu_zhibin/shield/members'><img src='https://gitee.com/xu_zhibin/shield/badge/fork.svg?theme=dark' alt='fork'></img></a>
![Straws](https://gitee.com/xu_zhibin/straws/raw/master/docs/logo.png)

#### 介绍
Straws是一款开源的离线数据同步中间件(ETL)，提供Mysql、SqlServer等离线同步场景，同时支持定时同步（全量、增量、CDC三种模式）和数据转换清洗等功能，目前项目仍在持续完善中，若您要使用请自行验证通过过方可使用。

#### 环境要求
- JDK8
- Maven 3.2以上版本
- MySQL 8以上版本
- Windows/MacOS/Linux系统

#### 应用场景
- 相同或异构数据库的数据同步，表结构迁移（暂不支持索引、函数、存储过程迁移）
- 相同或异构数据库的定时同步，支持全量、增量、CDC三种同步模式，可以针对数据进行转换、过滤等
- 目前仅适配了Mysql、SqlServer两种关系型数据库，后续会陆续适配更多不同类型的数据库 

#### 系统架构
![Straws](https://gitee.com/xu_zhibin/straws/raw/master/docs/straws.png)

#### 安装教程
1.  安装JDK8、安装Maven3.2以上版本
2.  安装Mysql8，创建名为straws的数据库
3.  克隆项目到本地
4.  将web模块内的schema文件夹内的sql文件导入到straws数据库内
5.  编译、打包、部署
6.  需要拓展的tx可以自行拓展，但请遵守项目指定的开源协议

#### 项目配置（待补充）
1.  基础配置
2.  加密配置
3.  同步配置

#### 简单使用
1.  添加自己的数据源
2.  指定同步或迁移的源头数据源和目标数据源，填写参数
3.  启动任务

#### 界面展示
![Straws](https://gitee.com/xu_zhibin/straws/raw/master/docs/straws_login.png)
![Straws](https://gitee.com/xu_zhibin/straws/raw/master/docs/straws_dashboard.png)
![Straws](https://gitee.com/xu_zhibin/straws/raw/master/docs/straws_datasource.png)
![Straws](https://gitee.com/xu_zhibin/straws/raw/master/docs/straws_migrate.png)
![Straws](https://gitee.com/xu_zhibin/straws/raw/master/docs/straws_sync.png)
![Straws](https://gitee.com/xu_zhibin/straws/raw/master/docs/straws_cpwd.png)

#### 参与贡献
1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

