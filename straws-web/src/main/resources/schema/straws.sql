/*
Navicat MySQL Data Transfer

Source Server         : local_mysql_root
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : straws

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2022-02-08 13:00:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `force_update_password` bit(1) NOT NULL COMMENT '是否强制用户定期更新密码',
  `force_update_interval` int NOT NULL COMMENT '密码过期使劲按间隔，单位：小时',
  `enc_dspwd_in_db` bit(1) NOT NULL COMMENT '是否对数据源密码在数据库存储时进行加密',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'DEFAULT', '\0', '720', '');

-- ----------------------------
-- Table structure for sys_datasource
-- ----------------------------
DROP TABLE IF EXISTS `sys_datasource`;
CREATE TABLE `sys_datasource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源名称，必须唯一',
  `status` int NOT NULL COMMENT '数据源状态，1：启用 2：禁用',
  `type_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源类型，对用字典code：database_type',
  `type_name` varchar(50) DEFAULT NULL,
  `driver_class_name` varchar(200) NOT NULL,
  `driver_version` varchar(20) DEFAULT NULL,
  `jdbc_url` varchar(255) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_datasource
-- ----------------------------
INSERT INTO `sys_datasource` VALUES ('1', 'origin', '1', '1', null, 'com.mysql.cj.jdbc.Driver', null, 'jdbc:mysql://127.0.0.1:3306/db_origin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&allowMultiQueries=true&rewriteBatchedStatements=true', 'xzb', 'xzb19930617', null, '2022-01-23 06:38:34', null);
INSERT INTO `sys_datasource` VALUES ('3', 'target', '1', '1', null, 'com.mysql.cj.jdbc.Driver', null, 'jdbc:mysql://127.0.0.1:3306/db_target?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&allowMultiQueries=true&rewriteBatchedStatements=true', 'xzb', 'xzb19930617', null, '2022-01-23 08:28:58', null);
INSERT INTO `sys_datasource` VALUES ('4', 'target2', '1', '1', null, 'com.mysql.cj.jdbc.Driver', null, 'jdbc:mysql://127.0.0.1:3306/target2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&allowMultiQueries=true&rewriteBatchedStatements=true', 'xzb', 'xzb19930617', null, '2022-01-24 19:15:54', null);
INSERT INTO `sys_datasource` VALUES ('5', 'sqlserver_target', '1', '2', 'SQL Server', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', null, 'jdbc:sqlserver://127.0.0.1:1433;DatabaseName=db_target', 'sa', 'xzb19930617', null, '2022-01-24 20:06:12', '2022-01-24 22:10:43');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dict_type` varchar(50) NOT NULL,
  `dict_name` varchar(50) DEFAULT NULL,
  `item_code` varchar(50) NOT NULL,
  `item_value` varchar(50) NOT NULL,
  `enabled` bit(1) NOT NULL COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', 'db_type', '数据库类型', '1', 'MySQL', '');
INSERT INTO `sys_dict` VALUES ('2', 'db_type', '数据库类型', '2', 'SQL Server', '');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login_user` varchar(50) DEFAULT NULL,
  `login_mode` varchar(10) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `login_ip` varchar(32) DEFAULT NULL,
  `login_os` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_migrate
-- ----------------------------
DROP TABLE IF EXISTS `sys_migrate`;
CREATE TABLE `sys_migrate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `origin_ds_id` bigint NOT NULL,
  `origin_ds_name` varchar(50) DEFAULT NULL,
  `target_ds_id` bigint NOT NULL,
  `target_ds_name` varchar(50) DEFAULT NULL,
  `transformers` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '转换器脚本编号集合',
  `exec_time` datetime DEFAULT NULL,
  `exec_result` int DEFAULT NULL COMMENT '执行结果，1：未开始，2：已完成，3：未完成',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_migrate
-- ----------------------------
INSERT INTO `sys_migrate` VALUES ('1', '从origin迁移到target', '1', null, '3', null, null, null, null, '2022-01-23 19:04:53', null);
INSERT INTO `sys_migrate` VALUES ('3', 'origin -> target2', '1', null, '4', null, null, null, null, '2022-01-24 19:21:58', null);
INSERT INTO `sys_migrate` VALUES ('4', 'mysql -> sqlserver', '1', null, '5', null, null, null, null, '2022-01-24 22:11:06', null);
INSERT INTO `sys_migrate` VALUES ('5', 'sqlserver -> mysql', '5', null, '3', null, null, null, null, '2022-01-25 06:47:47', null);
INSERT INTO `sys_migrate` VALUES ('6', 'sqlserver -> mysql target', '5', null, '3', null, null, null, null, '2022-02-01 08:13:19', null);

-- ----------------------------
-- Table structure for sys_migrate_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_migrate_detail`;
CREATE TABLE `sys_migrate_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `migrate_id` bigint NOT NULL COMMENT '任务编号',
  `origin_table_name` varchar(100) NOT NULL COMMENT '源头表的表名',
  `target_table_name` varchar(100) NOT NULL COMMENT '目标表的表名',
  `is_create_table` bit(1) DEFAULT NULL COMMENT '是否创建表',
  `origin_table_select_sql` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '来源表的数据查询sql',
  `target_table_insert_sql` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `target_table_create_sql` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '目标表的建表语句',
  `transformers` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int DEFAULT NULL COMMENT '状态，1：未执行， 2：已完成，3：未完成，4：错误中断',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间，同一批次的创建时间应相同',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_migrate_detail
-- ----------------------------
INSERT INTO `sys_migrate_detail` VALUES ('124', '4', 'tb_kafka', 'tb_kafka', '', 'select id,name,code,gender,create_time from tb_kafka', 'insert into tb_kafka(id,name,code,gender,create_time) values (?,?,?,?,?)', 'CREATE TABLE tb_kafka([id] BIGINT NOT NULL IDENTITY(1,1),[name] NVARCHAR(255) DEFAULT NULL,[code] NVARCHAR(255) DEFAULT NULL,[gender] NVARCHAR(10) DEFAULT NULL,[create_time] DATETIME2 DEFAULT NULL); ALTER TABLE [dbo].[tb_kafka] ADD PRIMARY KEY ([id]);', null, null, '2022-01-30 08:30:17', null);
INSERT INTO `sys_migrate_detail` VALUES ('187', '6', 'tb_demo', 'tb_demo', '', 'select id,name,code,def_value,def_value_02,is_enable,long_value,create_time,double_value,float_value,decimal_value,char_value,longtext_value,longtext2_value,blob_value,longblob_value,numeric_value,remark,date_value,year_value,binary_value from tb_demo', 'insert into tb_demo(id,name,code,def_value,def_value_02,is_enable,long_value,create_time,double_value,float_value,decimal_value,char_value,longtext_value,longtext2_value,blob_value,longblob_value,numeric_value,remark,date_value,year_value,binary_value) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)', 'CREATE TABLE `tb_demo`(`id` INTEGER NOT NULL AUTO_INCREMENT,`name` VARCHAR(50) NOT NULL,`code` VARCHAR(50) NOT NULL,`def_value` VARCHAR(255) DEFAULT \'我是默认值\',`def_value_02` INTEGER DEFAULT \'10\',`is_enable` BIT(1) DEFAULT NULL,`long_value` BIGINT DEFAULT NULL,`create_time` DATETIME NOT NULL,`double_value` DOUBLE(53,0) DEFAULT NULL,`float_value` DOUBLE(53,0) DEFAULT NULL,`decimal_value` DECIMAL(10,2) DEFAULT NULL,`char_value` CHAR(10) DEFAULT NULL,`longtext_value` LONGTEXT DEFAULT NULL,`longtext2_value` LONGTEXT DEFAULT NULL,`blob_value` LONGBLOB DEFAULT NULL,`longblob_value` LONGBLOB DEFAULT NULL,`numeric_value` DECIMAL(8,3) NOT NULL,`remark` VARCHAR(100) DEFAULT NULL,`date_value` DATETIME DEFAULT NULL,`year_value` DATETIME DEFAULT NULL,`binary_value` BINARY(200) DEFAULT NULL, PRIMARY KEY (`id`,`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8', null, null, '2022-02-08 09:22:38', null);
INSERT INTO `sys_migrate_detail` VALUES ('188', '6', 'tb_kafka', 'tb_kafka', '', 'select id,name,code,gender,create_time from tb_kafka', 'insert into tb_kafka(id,name,code,gender,create_time) values (?,?,?,?,?)', 'CREATE TABLE `tb_kafka`(`id` BIGINT NOT NULL AUTO_INCREMENT,`name` VARCHAR(255) DEFAULT NULL,`code` VARCHAR(255) DEFAULT NULL,`gender` VARCHAR(10) DEFAULT NULL,`create_time` DATETIME DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8', null, null, '2022-02-08 09:22:38', null);
INSERT INTO `sys_migrate_detail` VALUES ('189', '6', 'tb_user', 'tb_user', '', 'select id,name,gender,birthday,create_time,update_time from tb_user', 'insert into tb_user(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)', 'CREATE TABLE `tb_user`(`id` INTEGER NOT NULL AUTO_INCREMENT,`name` VARCHAR(255) DEFAULT NULL,`gender` VARCHAR(255) DEFAULT NULL,`birthday` DATETIME DEFAULT NULL,`create_time` DATETIME DEFAULT NULL,`update_time` DATETIME DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8', null, null, '2022-02-08 09:22:38', null);
INSERT INTO `sys_migrate_detail` VALUES ('190', '6', 'tb_user_a', 'tb_user_a', '', 'select id,name,gender,birthday,create_time,update_time from tb_user_a', 'insert into tb_user_a(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)', 'CREATE TABLE `tb_user_a`(`id` INTEGER NOT NULL AUTO_INCREMENT,`name` VARCHAR(255) DEFAULT NULL,`gender` VARCHAR(255) DEFAULT NULL,`birthday` DATETIME DEFAULT NULL,`create_time` DATETIME DEFAULT NULL,`update_time` DATETIME DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8', null, null, '2022-02-08 09:22:38', null);
INSERT INTO `sys_migrate_detail` VALUES ('191', '6', 'tb_user_b', 'tb_user_b', '', 'select id,name,gender,birthday,create_time,update_time from tb_user_b', 'insert into tb_user_b(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)', 'CREATE TABLE `tb_user_b`(`id` INTEGER NOT NULL AUTO_INCREMENT,`name` VARCHAR(255) DEFAULT NULL,`gender` VARCHAR(255) DEFAULT NULL,`birthday` DATETIME DEFAULT NULL,`create_time` DATETIME DEFAULT NULL,`update_time` DATETIME DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8', null, null, '2022-02-08 09:22:38', null);
INSERT INTO `sys_migrate_detail` VALUES ('192', '6', 'tb_user_c', 'tb_user_c', '', 'select id,name,gender,birthday,create_time,update_time from tb_user_c', 'insert into tb_user_c(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)', 'CREATE TABLE `tb_user_c`(`id` INTEGER NOT NULL AUTO_INCREMENT,`name` VARCHAR(255) DEFAULT NULL,`gender` VARCHAR(255) DEFAULT NULL,`birthday` DATETIME DEFAULT NULL,`create_time` DATETIME DEFAULT NULL,`update_time` DATETIME DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8', null, null, '2022-02-08 09:22:38', null);
INSERT INTO `sys_migrate_detail` VALUES ('193', '6', 'tb_user_d', 'tb_user_d', '', 'select id,name,gender,birthday,create_time,update_time from tb_user_d', 'insert into tb_user_d(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)', 'CREATE TABLE `tb_user_d`(`id` INTEGER NOT NULL AUTO_INCREMENT,`name` VARCHAR(255) DEFAULT NULL,`gender` VARCHAR(255) DEFAULT NULL,`birthday` DATETIME DEFAULT NULL,`create_time` DATETIME DEFAULT NULL,`update_time` DATETIME DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8', null, null, '2022-02-08 09:22:38', null);

-- ----------------------------
-- Table structure for sys_oplog
-- ----------------------------
DROP TABLE IF EXISTS `sys_oplog`;
CREATE TABLE `sys_oplog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT NULL COMMENT '操作类型',
  `type_color` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型对应的颜色，拓展方便前端组件定义组件颜色',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作内容或描述',
  `method` varchar(255) DEFAULT NULL COMMENT '操作的方法名',
  `optime` datetime DEFAULT NULL COMMENT '操作时间',
  `opuser` varchar(50) DEFAULT NULL COMMENT '操作用户，这里存储的是用户名 username',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_oplog
-- ----------------------------
INSERT INTO `sys_oplog` VALUES ('8', '更新', '#409EFF', '重置系统配置', 'resetDefault', '2022-02-08 08:07:11', 'admin', '2022-02-08 08:07:11');
INSERT INTO `sys_oplog` VALUES ('9', '更新', '#409EFF', '更新系统配置', 'updateDefault', '2022-02-08 08:07:15', 'admin', '2022-02-08 08:07:15');
INSERT INTO `sys_oplog` VALUES ('10', '新增', '#67C23A', '新增用户', 'insertEntity', '2022-02-08 08:08:01', 'admin', '2022-02-08 08:08:01');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', null, null);

-- ----------------------------
-- Table structure for sys_script
-- ----------------------------
DROP TABLE IF EXISTS `sys_script`;
CREATE TABLE `sys_script` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `hash_key` varchar(64) DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `description` varchar(200) DEFAULT NULL,
  `is_enabled` bit(1) DEFAULT NULL COMMENT '是否启用',
  `is_checked` bit(1) DEFAULT NULL COMMENT '是否通过安全检测',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_script
-- ----------------------------
INSERT INTO `sys_script` VALUES ('1', 'FemaleTransform', '7e0a05374a52c2f1037b7ad256e5c0db', 'package groovy;\n\nimport com.gitee.pristine.spi.transformer.Transformer;\n\npublic class FemaleTransformer implements Transformer {\n\n    @Override\n    public Object[] transfer(Object[] record) {\n        if (\"男\".equals(record[2])) {\n            return null;\n        }\n        return record;\n    }\n  \n}', '返回非男的数据记录11', '', '', '2022-01-27 04:36:00', '2022-02-03 21:58:35');

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `group_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `origin_ds_id` bigint NOT NULL,
  `origin_ds_name` varchar(50) DEFAULT NULL,
  `target_ds_id` bigint NOT NULL,
  `target_ds_name` varchar(50) DEFAULT NULL,
  `origin_table_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `target_table_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `columns` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '需要同步的列名，以逗号分割',
  `sync_mode` int DEFAULT NULL,
  `split_pk` varchar(50) DEFAULT NULL COMMENT '进行任务切分的列名，必须为自增列',
  `split_pk_type` int DEFAULT NULL COMMENT '进行任务切分的列类型，必须为int或long类型',
  `last_min_split_pk` bigint DEFAULT NULL,
  `last_max_split_pk` bigint DEFAULT NULL,
  `time_cron` varchar(20) DEFAULT NULL,
  `workers_per_group` int DEFAULT NULL,
  `bytes_per_second` int DEFAULT NULL,
  `select_fetch_size` int DEFAULT '200',
  `insert_batch_size` int DEFAULT '200',
  `transforms` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '以逗号分割，最多存储5个',
  `run_status` bit(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO `sys_task` VALUES ('1', '同步tb_user表', '测试分组', '1', null, '5', null, 'tb_user', 'tb_user', 'id,name,gender,birthday,create_time,update_time', '2', 'id', '1', null, null, '0 * * * * ? *', '1', '5000', '1000', '1000', null, '\0', '2022-01-27 10:55:56', '2022-01-30 13:26:42');
INSERT INTO `sys_task` VALUES ('2', '同步tb_user_a表', '测试分组', '1', null, '5', null, 'tb_user_a', 'tb_user_a', 'id,name,gender,birthday,create_time,update_time', '1', 'id', '1', null, null, '0 * * * * ? *', '1', '5000', '1000', '1000', null, '\0', '2022-01-27 10:55:56', '2022-01-30 13:28:30');
INSERT INTO `sys_task` VALUES ('3', '同步tb_user_b表', '测试分组', '1', null, '5', null, 'tb_user_b', 'tb_user_b', 'id,name,gender,birthday,create_time,update_time', '1', 'id', '1', null, null, '0 * * * * ? *', '2', '5000', '1000', '1000', null, '\0', '2022-01-27 10:55:56', '2022-01-30 07:46:28');
INSERT INTO `sys_task` VALUES ('4', '同步tb_user_c表', '测试分组', '1', null, '5', null, 'tb_user_c', 'tb_user_c', 'id,name,gender,birthday,create_time,update_time', '1', 'id', '1', null, null, '0 * * * * ? *', '1', '5000', '1000', '1000', null, '\0', '2022-01-27 10:55:56', '2022-01-30 13:28:54');
INSERT INTO `sys_task` VALUES ('5', '同步tb_user_d表', '测试分组', '1', null, '5', null, 'tb_user_d', 'tb_user_d', 'id,name,gender,birthday,create_time,update_time', '1', 'id', '1', null, null, '0 * * * * ? *', '1', '5000', '1000', '1000', null, '\0', '2022-01-27 10:55:56', '2022-01-30 13:29:02');
INSERT INTO `sys_task` VALUES ('6', '同步tb_kafka表', '测试分组', '1', null, '5', null, 'tb_kafka', 'tb_kafka', 'id,name,code, gender,create_time', '3', 'id', '2', '0', '4', '0 * * * * ? *', '1', '5000', '1000', '1000', null, '\0', '2022-01-27 10:55:56', '2022-01-30 13:27:35');
INSERT INTO `sys_task` VALUES ('7', '测试006', '测试分组', '5', null, '3', null, 'tb_user', 'tb_user', 'id,name,gender,birthday,create_time,update_time', '2', 'id', '1', '0', '100001', '0 * * * * ? *', '2', '4096', '1000', '1000', null, '\0', '2022-02-01 10:36:22', '2022-02-06 09:23:43');

-- ----------------------------
-- Table structure for sys_task_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_task_log`;
CREATE TABLE `sys_task_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_id` bigint NOT NULL COMMENT '定时同步任务编号',
  `task_name` varchar(50) DEFAULT NULL,
  `task_start_time` datetime DEFAULT NULL COMMENT '任务开始执行时间',
  `task_finish_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `task_result` int DEFAULT NULL COMMENT '执行结果，1：成功， 2：失败',
  `task_failed_reason` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '失败原因',
  `transfer_record_count` bigint DEFAULT NULL COMMENT '传输的数据总数',
  `transfer_record_bytes` varchar(50) DEFAULT NULL COMMENT '传输的数据总字节数',
  `transfer_average_time` varchar(50) DEFAULT NULL COMMENT '传输平均花费时间（多个chunk）',
  `transfer_average_speed` varchar(50) DEFAULT NULL COMMENT '传输的平均速率',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_task_log
-- ----------------------------
INSERT INTO `sys_task_log` VALUES ('271', '7', '测试006', '2022-02-02 03:39:37', '2022-02-02 03:39:50', '2', null, '0', '0 b', '0 ms', '0 b/s', '2022-02-02 03:39:50');
INSERT INTO `sys_task_log` VALUES ('272', '7', '测试006', '2022-02-02 03:40:56', '2022-02-02 03:41:04', '1', null, '89995', '22.12 MB', '6 s', '3.69 MB', '2022-02-02 03:41:04');
INSERT INTO `sys_task_log` VALUES ('273', '7', '测试006', '2022-02-02 03:49:32', '2022-02-02 03:49:44', '2', null, '0', '0 b', '0 ms', '0 b/s', '2022-02-02 03:49:44');
INSERT INTO `sys_task_log` VALUES ('274', '7', '测试006', '2022-02-02 03:51:59', '2022-02-02 03:52:11', '2', null, '0', '0 b', '0 ms', '0 b/s', '2022-02-02 03:52:11');
INSERT INTO `sys_task_log` VALUES ('275', '7', '测试006', '2022-02-02 03:53:44', '2022-02-02 03:53:56', '2', null, '0', '0 b', '0 ms', '0 b/s', '2022-02-02 03:53:56');
INSERT INTO `sys_task_log` VALUES ('276', '7', '测试006', '2022-02-02 03:56:20', '2022-02-02 03:56:32', '2', 'org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [insert into tb_user(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)]; Duplicate entry \'1\' for key \'tb_user.PRIMARY\'; nested exception is java.sql.BatchUpdateException: Duplicate entry \'1\' for key \'tb_user.PRIMARY\'', '0', '0 b', '0 ms', '0 b/s', '2022-02-02 03:56:32');
INSERT INTO `sys_task_log` VALUES ('277', '7', '测试006', '2022-02-06 07:07:09', '2022-02-06 07:07:20', '2', 'org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [insert into tb_user(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)]; Duplicate entry \'1\' for key \'tb_user.PRIMARY\'; nested exception is java.sql.BatchUpdateException: Duplicate entry \'1\' for key \'tb_user.PRIMARY\'', '0', '0 b', '0 ms', '0 b/s', '2022-02-06 07:07:20');
INSERT INTO `sys_task_log` VALUES ('278', '7', '测试006', '2022-02-06 07:08:00', '2022-02-06 07:08:12', '2', 'org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [insert into tb_user(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)]; Duplicate entry \'1\' for key \'tb_user.PRIMARY\'; nested exception is java.sql.BatchUpdateException: Duplicate entry \'1\' for key \'tb_user.PRIMARY\'', '0', '0 b', '0 ms', '0 b/s', '2022-02-06 07:08:12');
INSERT INTO `sys_task_log` VALUES ('279', '7', '测试006', '2022-02-06 07:09:00', '2022-02-06 07:09:12', '2', 'org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [insert into tb_user(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)]; Duplicate entry \'1\' for key \'tb_user.PRIMARY\'; nested exception is java.sql.BatchUpdateException: Duplicate entry \'1\' for key \'tb_user.PRIMARY\'', '0', '0 b', '0 ms', '0 b/s', '2022-02-06 07:09:12');
INSERT INTO `sys_task_log` VALUES ('280', '7', '测试006', '2022-02-06 07:10:00', '2022-02-06 07:10:12', '2', 'org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [insert into tb_user(id,name,gender,birthday,create_time,update_time) values (?,?,?,?,?,?)]; Duplicate entry \'1\' for key \'tb_user.PRIMARY\'; nested exception is java.sql.BatchUpdateException: Duplicate entry \'1\' for key \'tb_user.PRIMARY\'', '0', '0 b', '0 ms', '0 b/s', '2022-02-06 07:10:12');
INSERT INTO `sys_task_log` VALUES ('281', '7', '测试006', '2022-02-06 07:11:00', '2022-02-06 07:11:08', '1', null, '89995', '22.12 MB', '6 s', '3.69 MB', '2022-02-06 07:11:08');
INSERT INTO `sys_task_log` VALUES ('282', '7', '测试006', '2022-02-06 07:12:00', '2022-02-06 07:12:00', '1', null, '0', '0 bytes', '4 ms', '0 bytes', '2022-02-06 07:12:00');
INSERT INTO `sys_task_log` VALUES ('283', '7', '测试006', '2022-02-06 07:13:00', '2022-02-06 07:13:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:13:00');
INSERT INTO `sys_task_log` VALUES ('284', '7', '测试006', '2022-02-06 07:14:00', '2022-02-06 07:14:00', '1', null, '0', '0 bytes', '2 ms', '0 bytes', '2022-02-06 07:14:00');
INSERT INTO `sys_task_log` VALUES ('285', '7', '测试006', '2022-02-06 07:15:00', '2022-02-06 07:15:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:15:00');
INSERT INTO `sys_task_log` VALUES ('286', '7', '测试006', '2022-02-06 07:16:00', '2022-02-06 07:16:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:16:00');
INSERT INTO `sys_task_log` VALUES ('287', '7', '测试006', '2022-02-06 07:17:00', '2022-02-06 07:17:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:17:00');
INSERT INTO `sys_task_log` VALUES ('288', '7', '测试006', '2022-02-06 07:18:00', '2022-02-06 07:18:00', '1', null, '0', '0 bytes', '2 ms', '0 bytes', '2022-02-06 07:18:00');
INSERT INTO `sys_task_log` VALUES ('289', '7', '测试006', '2022-02-06 07:19:00', '2022-02-06 07:19:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:19:00');
INSERT INTO `sys_task_log` VALUES ('290', '7', '测试006', '2022-02-06 07:20:00', '2022-02-06 07:20:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:20:00');
INSERT INTO `sys_task_log` VALUES ('291', '7', '测试006', '2022-02-06 07:21:00', '2022-02-06 07:21:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:21:00');
INSERT INTO `sys_task_log` VALUES ('292', '7', '测试006', '2022-02-06 07:22:00', '2022-02-06 07:22:05', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:22:05');
INSERT INTO `sys_task_log` VALUES ('293', '7', '测试006', '2022-02-06 07:23:00', '2022-02-06 07:23:00', '1', null, '0', '0 bytes', '2 ms', '0 bytes', '2022-02-06 07:23:00');
INSERT INTO `sys_task_log` VALUES ('294', '7', '测试006', '2022-02-06 07:24:00', '2022-02-06 07:24:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:24:00');
INSERT INTO `sys_task_log` VALUES ('295', '7', '测试006', '2022-02-06 07:25:00', '2022-02-06 07:25:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:25:00');
INSERT INTO `sys_task_log` VALUES ('296', '7', '测试006', '2022-02-06 07:26:00', '2022-02-06 07:26:00', '1', null, '0', '0 bytes', '2 ms', '0 bytes', '2022-02-06 07:26:00');
INSERT INTO `sys_task_log` VALUES ('297', '7', '测试006', '2022-02-06 07:27:00', '2022-02-06 07:27:00', '1', null, '0', '0 bytes', '2 ms', '0 bytes', '2022-02-06 07:27:00');
INSERT INTO `sys_task_log` VALUES ('298', '7', '测试006', '2022-02-06 07:28:00', '2022-02-06 07:28:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:28:00');
INSERT INTO `sys_task_log` VALUES ('299', '7', '测试006', '2022-02-06 07:29:00', '2022-02-06 07:29:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:29:00');
INSERT INTO `sys_task_log` VALUES ('300', '7', '测试006', '2022-02-06 07:30:00', '2022-02-06 07:30:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:30:00');
INSERT INTO `sys_task_log` VALUES ('301', '7', '测试006', '2022-02-06 07:31:00', '2022-02-06 07:31:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:31:00');
INSERT INTO `sys_task_log` VALUES ('302', '7', '测试006', '2022-02-06 07:32:00', '2022-02-06 07:32:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:32:00');
INSERT INTO `sys_task_log` VALUES ('303', '7', '测试006', '2022-02-06 07:33:00', '2022-02-06 07:33:00', '1', null, '0', '0 bytes', '3 ms', '0 bytes', '2022-02-06 07:33:00');
INSERT INTO `sys_task_log` VALUES ('304', '7', '测试006', '2022-02-06 07:34:00', '2022-02-06 07:34:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:34:00');
INSERT INTO `sys_task_log` VALUES ('305', '7', '测试006', '2022-02-06 07:35:00', '2022-02-06 07:35:00', '1', null, '0', '0 bytes', '5 ms', '0 bytes', '2022-02-06 07:35:00');
INSERT INTO `sys_task_log` VALUES ('306', '7', '测试006', '2022-02-06 07:36:00', '2022-02-06 07:36:00', '1', null, '0', '0 bytes', '2 ms', '0 bytes', '2022-02-06 07:36:00');
INSERT INTO `sys_task_log` VALUES ('307', '7', '测试006', '2022-02-06 07:37:00', '2022-02-06 07:37:00', '1', null, '0', '0 bytes', '2 ms', '0 bytes', '2022-02-06 07:37:00');
INSERT INTO `sys_task_log` VALUES ('308', '7', '测试006', '2022-02-06 07:38:00', '2022-02-06 07:38:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:38:00');
INSERT INTO `sys_task_log` VALUES ('309', '7', '测试006', '2022-02-06 07:39:00', '2022-02-06 07:39:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:39:00');
INSERT INTO `sys_task_log` VALUES ('310', '7', '测试006', '2022-02-06 07:40:00', '2022-02-06 07:40:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:40:00');
INSERT INTO `sys_task_log` VALUES ('311', '7', '测试006', '2022-02-06 07:41:00', '2022-02-06 07:41:00', '1', null, '0', '0 bytes', '1 ms', '0 bytes', '2022-02-06 07:41:00');
INSERT INTO `sys_task_log` VALUES ('312', '7', '测试006', '2022-02-06 09:23:09', '2022-02-06 09:23:09', '1', null, '0', '0 bytes', '7 ms', '0 bytes', '2022-02-06 09:23:09');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `realname` varchar(50) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `avatar` varchar(150) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `last_password_modified` datetime DEFAULT NULL COMMENT '上次密码更新时间，用来校验密码是否过期',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'xzb1993', '00469b2d8c1f0e89e0d7024cde006439', 'as66jk', '暗格里的秘密', '男', '18585135366', '1806606800@qq.com', '/avatars/man.jpeg', '1', null, null, null, '2022-02-05 06:30:33');
INSERT INTO `sys_user` VALUES ('2', 'admin', 'dd26a43613bc5384ee57289b8f5f3a7f', '1f97f9', 'jack', '女', '18585135466', '18585135466@185.com', '/avatars/woman.jpeg', '1', null, null, '2022-02-04 12:14:08', '2022-02-05 07:06:11');
INSERT INTO `sys_user` VALUES ('3', 'user002', 'e6e3690c25b93e178a4827c36c2cc189', '54a2f6', '最好的我们', '男', '18356698356', '18356698356@qq.com', null, '1', null, null, '2022-02-08 07:14:02', null);
INSERT INTO `sys_user` VALUES ('4', 'user003', 'e81f1e79489d1ad9b7e1aabae3a55681', '2277ff', '何以笙', '男', '18359696666', '18359696666@qq.com', null, '1', null, null, '2022-02-08 08:08:01', null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
