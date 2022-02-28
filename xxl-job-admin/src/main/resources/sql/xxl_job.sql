/*
 Navicat Premium Data Transfer

 Source Server         : 139.196.208.53
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 139.196.208.53:3306
 Source Schema         : xxl_job

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 28/02/2022 21:45:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器名称',
  `address_type` tinyint NOT NULL DEFAULT 0 COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
INSERT INTO `xxl_job_group` VALUES (8, 'mq-executor', 'MQ-EXECUTOR', 0, 'http://172.17.0.4:8202/', '2022-02-28 21:44:53');

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器任务参数',
  `system_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '系统编码',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int NOT NULL DEFAULT 0 COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime NULL DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint NOT NULL DEFAULT 0 COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint NOT NULL DEFAULT 0 COMMENT '上次调度时间',
  `trigger_next_time` bigint NOT NULL DEFAULT 0 COMMENT '下次调度时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_info
-- ----------------------------
INSERT INTO `xxl_job_info` VALUES (18, 8, 'user定时任务测试', '2022-02-19 18:54:47', '2022-02-23 08:34:52', 'zhoushuai', 'zs11195310@163.com', 'CRON', '0 0 1 * * ? *', 'DO_NOTHING', 'ROUND', 'mqJobHandler', 'EC_TASK_USER_JOB_TEST', 'user', 'SERIAL_EXECUTION', 0, 3, 'BEAN', '', 'GLUE代码初始化', '2022-02-19 18:54:47', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (19, 8, 'product定时任务测试', '2022-02-19 18:56:04', '2022-02-23 08:34:57', 'zhoushuai', 'zs11195310@163.com', 'CRON', '0 0 1 * * ? *', 'DO_NOTHING', 'ROUND', 'mqJobHandler', 'EC_TASK_PRODUCT_JOB_TEST', 'product', 'SERIAL_EXECUTION', 0, 3, 'BEAN', '', 'GLUE代码初始化', '2022-02-19 18:56:04', '', 1, 1646010000000, 1646067600000);
INSERT INTO `xxl_job_info` VALUES (20, 8, 'payment定时任务测试', '2022-02-19 18:56:46', '2022-02-23 08:35:03', 'zhoushuai', 'zs11195310@163.com', 'CRON', '0 0 1 * * ? *', 'DO_NOTHING', 'ROUND', 'mqJobHandler', 'EC_TASK_PAYMENT_JOB_TEST', 'payment', 'SERIAL_EXECUTION', 0, 3, 'BEAN', '', 'GLUE代码初始化', '2022-02-19 18:56:46', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (21, 8, 'activiti定时任务测试', '2022-02-19 18:57:20', '2022-02-23 08:35:08', 'zhoushuai', 'zs11195310@163.com', 'CRON', '0 0 1 * * ? *', 'DO_NOTHING', 'ROUND', 'mqJobHandler', 'EC_TASK_ACTIVITI_JOB_TEST', 'activiti', 'SERIAL_EXECUTION', 0, 3, 'BEAN', '', 'GLUE代码初始化', '2022-02-19 18:57:20', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (22, 8, 'order定时任务测试', '2022-02-19 18:57:54', '2022-02-23 06:01:21', 'zhoushuai', 'zs11195310@163.com', 'CRON', '0 0 1 * * ? *', 'DO_NOTHING', 'ROUND', 'mqJobHandler', 'EC_TASK_ORDER_JOB_TEST', 'order', 'SERIAL_EXECUTION', 0, 3, 'BEAN', '', 'GLUE代码初始化', '2022-02-19 18:57:54', '', 1, 1646010000000, 1646067600000);

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock`  (
  `lock_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_lock
-- ----------------------------
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');

-- ----------------------------
-- Table structure for xxl_job_log_0
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_0`;
CREATE TABLE `xxl_job_log_0`  (
  `id` bigint NOT NULL,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `system_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系统编码',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '调度-日志',
  `handle_time` datetime NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '执行-日志',
  `alarm_status` tinyint NOT NULL DEFAULT 0 COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  `callback_code` int NOT NULL COMMENT '完成-结果',
  `callback_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '完成-日志',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '完成-时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_log_0
-- ----------------------------
INSERT INTO `xxl_job_log_0` VALUES (705049936617537537, 8, 22, 'http://172.17.0.4:8202/', 'mqJobHandler', 'order', 'EC_TASK_ORDER_JOB_TEST', NULL, 3, '2022-02-28 21:36:44', 200, '任务触发类型：手动触发<br>调度机器：172.17.0.3<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://172.17.0.4:8202/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://172.17.0.4:8202/<br>code：200<br>msg：null', '2022-02-28 21:36:44', 200, '', 0, 200, NULL, '2022-02-28 21:36:44');
INSERT INTO `xxl_job_log_0` VALUES (705050103924129792, 8, 19, 'http://172.17.0.4:8202/', 'mqJobHandler', 'product', 'EC_TASK_PRODUCT_JOB_TEST', NULL, 3, '2022-02-28 21:37:24', 200, '任务触发类型：手动触发<br>调度机器：172.17.0.3<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://172.17.0.4:8202/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://172.17.0.4:8202/<br>code：200<br>msg：null', '2022-02-28 21:37:24', 200, '', 0, 200, NULL, '2022-02-28 21:37:24');

-- ----------------------------
-- Table structure for xxl_job_log_1
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_1`;
CREATE TABLE `xxl_job_log_1`  (
  `id` bigint NOT NULL,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `system_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系统编码',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '调度-日志',
  `handle_time` datetime NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '执行-日志',
  `alarm_status` tinyint NOT NULL DEFAULT 0 COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  `callback_code` int NOT NULL COMMENT '完成-结果',
  `callback_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '完成-日志',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '完成-时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_log_1
-- ----------------------------
INSERT INTO `xxl_job_log_1` VALUES (705050492551561216, 8, 22, 'http://172.17.0.4:8202/', 'mqJobHandler', 'order', 'EC_TASK_ORDER_JOB_TEST', NULL, 3, '2022-02-28 21:38:57', 200, '任务触发类型：手动触发<br>调度机器：172.17.0.3<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://172.17.0.4:8202/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://172.17.0.4:8202/<br>code：200<br>msg：null', '2022-02-28 21:38:57', 200, '', 0, 200, NULL, '2022-02-28 21:38:57');

-- ----------------------------
-- Table structure for xxl_job_log_2
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_2`;
CREATE TABLE `xxl_job_log_2`  (
  `id` bigint NOT NULL,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `system_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系统编码',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '调度-日志',
  `handle_time` datetime NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '执行-日志',
  `alarm_status` tinyint NOT NULL DEFAULT 0 COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  `callback_code` int NOT NULL COMMENT '完成-结果',
  `callback_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '完成-日志',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '完成-时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_log_2
-- ----------------------------
INSERT INTO `xxl_job_log_2` VALUES (705026013922328576, 8, 22, 'http://172.17.0.4:8202/', 'mqJobHandler', 'order', 'EC_TASK_ORDER_JOB_TEST', NULL, 3, '2022-02-28 20:01:40', 200, '任务触发类型：手动触发<br>调度机器：172.17.0.3<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://172.17.0.4:8202/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://172.17.0.4:8202/<br>code：200<br>msg：null', '2022-02-28 20:01:41', 200, '', 0, 200, NULL, '2022-02-28 20:01:42');
INSERT INTO `xxl_job_log_2` VALUES (705050392743903233, 8, 19, 'http://172.17.0.4:8202/', 'mqJobHandler', 'product', 'EC_TASK_PRODUCT_JOB_TEST', NULL, 3, '2022-02-28 21:38:33', 200, '任务触发类型：手动触发<br>调度机器：172.17.0.3<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://172.17.0.4:8202/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://172.17.0.4:8202/<br>code：200<br>msg：null', '2022-02-28 21:38:33', 200, '', 0, 200, NULL, '2022-02-28 21:38:33');

-- ----------------------------
-- Table structure for xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_report`;
CREATE TABLE `xxl_job_log_report`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime NULL DEFAULT NULL COMMENT '调度-时间',
  `running_count` int NOT NULL DEFAULT 0 COMMENT '运行中-日志数量',
  `suc_count` int NOT NULL DEFAULT 0 COMMENT '执行成功-日志数量',
  `fail_count` int NOT NULL DEFAULT 0 COMMENT '执行失败-日志数量',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_trigger_day`(`trigger_day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_log_report
-- ----------------------------
INSERT INTO `xxl_job_log_report` VALUES (1, '2022-02-21 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (2, '2022-02-20 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (3, '2022-02-19 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (4, '2022-02-21 08:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (5, '2022-02-20 08:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (6, '2022-02-19 08:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (7, '2022-02-22 00:00:00', 0, 6, 3, NULL);
INSERT INTO `xxl_job_log_report` VALUES (8, '2022-02-22 08:00:00', 0, 6, 3, NULL);
INSERT INTO `xxl_job_log_report` VALUES (9, '2022-02-23 08:00:00', 0, 11, 56, NULL);
INSERT INTO `xxl_job_log_report` VALUES (10, '2022-02-23 00:00:00', 0, 11, 56, NULL);
INSERT INTO `xxl_job_log_report` VALUES (11, '2022-02-24 08:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (12, '2022-02-25 08:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (13, '2022-02-25 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (14, '2022-02-24 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (15, '2022-02-26 08:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (16, '2022-02-26 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (17, '2022-02-27 08:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (18, '2022-02-27 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (19, '2022-02-28 00:00:00', 0, 3, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (20, '2022-02-28 08:00:00', 0, 5, 0, NULL);

-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_logglue
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_g_k_v`(`registry_group`, `registry_key`, `registry_value`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 431 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_registry
-- ----------------------------
INSERT INTO `xxl_job_registry` VALUES (437, 'EXECUTOR', 'mq-executor', 'http://172.17.0.4:8202/', '2022-02-28 21:44:45');

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `role` tinyint NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_user
-- ----------------------------
INSERT INTO `xxl_job_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);
INSERT INTO `xxl_job_user` VALUES (3, 'zhoushuai', 'e10adc3949ba59abbe56e057f20f883e', 0, '8');

SET FOREIGN_KEY_CHECKS = 1;
