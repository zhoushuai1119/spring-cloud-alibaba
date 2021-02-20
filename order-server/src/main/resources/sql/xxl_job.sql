/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : xxl_job

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 08/02/2021 16:17:31
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
INSERT INTO `xxl_job_group` VALUES (1, 'xxl-job-executor-sample', '示例执行器', 0, NULL, '2021-02-06 14:00:54');

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
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_info
-- ----------------------------
INSERT INTO `xxl_job_info` VALUES (1, 1, '测试任务1', '2018-11-03 22:21:31', '2021-01-26 12:56:06', 'XXL', '', 'CRON', '0 0 0 * * ? *', 'DO_NOTHING', 'ROUND', 'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 4, 'BEAN', '', 'GLUE代码初始化', '2018-11-03 22:21:31', '', 1, 1612540800000, 1612627200000);

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock`  (
  `lock_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_lock
-- ----------------------------
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');

-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
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
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_log
-- ----------------------------
INSERT INTO `xxl_job_log` VALUES (1, 1, 1, 'http://192.168.10.1:9999/', 'demoJobHandler', '', NULL, 0, '2021-01-26 12:32:54', 200, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9999/<br>code：200<br>msg：null', '2021-01-26 12:48:53', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (2, 1, 1, 'http://192.168.10.1:9999/', 'demoJobHandler', '', NULL, 0, '2021-01-26 12:33:24', 200, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9999/<br>code：200<br>msg：null', '2021-01-26 12:48:54', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (3, 1, 1, 'http://192.168.10.1:9998/', 'demoJobHandler', '', NULL, 0, '2021-01-26 12:37:41', 200, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9998/, http://192.168.10.1:9999/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9998/<br>code：200<br>msg：null', '2021-01-26 12:48:54', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (4, 1, 1, 'http://192.168.10.1:9999/', 'demoJobHandler', '', NULL, 0, '2021-01-26 12:37:53', 200, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9998/, http://192.168.10.1:9999/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9999/<br>code：200<br>msg：null', '2021-01-26 12:48:54', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (5, 1, 1, 'http://192.168.10.1:9998/', 'demoJobHandler', '', NULL, 4, '2021-01-26 12:43:35', 200, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9998/, http://192.168.10.1:9999/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：4<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9998/<br>code：200<br>msg：null<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', '2021-01-26 12:53:54', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (6, 1, 1, 'http://192.168.10.1:9999/', 'demoJobHandler', '', NULL, 4, '2021-01-26 12:43:54', 200, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9998/, http://192.168.10.1:9999/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：4<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9999/<br>code：200<br>msg：null<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', '2021-01-26 12:58:54', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (7, 1, 1, 'http://192.168.10.1:9999/', 'demoJobHandler', '', NULL, 4, '2021-01-26 12:52:20', 200, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9999/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：4<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9999/<br>code：200<br>msg：null<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', '2021-01-26 13:02:54', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (8, 1, 1, 'http://192.168.10.1:9999/', 'demoJobHandler', '', NULL, 3, '2021-01-26 12:53:56', 200, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9999/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9999/<br>code：200<br>msg：null<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', '2021-01-26 13:04:54', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (9, 1, 1, 'http://192.168.10.1:9999/', 'demoJobHandler', '', NULL, 4, '2021-01-26 12:56:10', 200, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.1:9999/]<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：4<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.1:9999/<br>code：200<br>msg：null<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', '2021-01-26 13:06:55', 500, '任务结果丢失，标记失败', 1);
INSERT INTO `xxl_job_log` VALUES (10, 1, 1, NULL, 'demoJobHandler', '', NULL, 3, '2021-01-26 12:58:56', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (11, 1, 1, NULL, 'demoJobHandler', '', NULL, 2, '2021-01-26 12:59:07', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：2<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (12, 1, 1, NULL, 'demoJobHandler', '', NULL, 1, '2021-01-26 12:59:17', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：1<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (13, 1, 1, NULL, 'demoJobHandler', '', NULL, 0, '2021-01-26 12:59:28', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (14, 1, 1, NULL, 'demoJobHandler', '', NULL, 3, '2021-01-26 13:02:58', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (15, 1, 1, NULL, 'demoJobHandler', '', NULL, 2, '2021-01-26 13:03:09', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：2<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (16, 1, 1, NULL, 'demoJobHandler', '', NULL, 1, '2021-01-26 13:03:19', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：1<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (17, 1, 1, NULL, 'demoJobHandler', '', NULL, 0, '2021-01-26 13:03:29', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (18, 1, 1, NULL, 'demoJobHandler', '', NULL, 2, '2021-01-26 13:05:00', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：2<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (19, 1, 1, NULL, 'demoJobHandler', '', NULL, 1, '2021-01-26 13:05:11', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：1<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (20, 1, 1, NULL, 'demoJobHandler', '', NULL, 0, '2021-01-26 13:05:21', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (21, 1, 1, NULL, 'demoJobHandler', '', NULL, 3, '2021-01-26 13:07:02', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (22, 1, 1, NULL, 'demoJobHandler', '', NULL, 2, '2021-01-26 13:07:12', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：2<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (23, 1, 1, NULL, 'demoJobHandler', '', NULL, 1, '2021-01-26 13:07:22', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：1<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (24, 1, 1, NULL, 'demoJobHandler', '', NULL, 0, '2021-01-26 13:07:33', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (25, 1, 1, NULL, 'demoJobHandler', '', NULL, 4, '2021-01-26 13:26:06', 500, '任务触发类型：手动触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：4<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (26, 1, 1, NULL, 'demoJobHandler', '', NULL, 3, '2021-01-26 13:26:13', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：3<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (27, 1, 1, NULL, 'demoJobHandler', '', NULL, 2, '2021-01-26 13:26:24', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：2<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (28, 1, 1, NULL, 'demoJobHandler', '', NULL, 1, '2021-01-26 13:26:34', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：1<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br><br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>', NULL, 0, NULL, 1);
INSERT INTO `xxl_job_log` VALUES (29, 1, 1, NULL, 'demoJobHandler', '', NULL, 0, '2021-01-26 13:26:44', 500, '任务触发类型：失败重试触发<br>调度机器：192.168.10.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：轮询<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_log_report
-- ----------------------------
INSERT INTO `xxl_job_log_report` VALUES (1, '2021-01-26 00:00:00', 0, 0, 29, NULL);
INSERT INTO `xxl_job_log_report` VALUES (2, '2021-01-25 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (3, '2021-01-24 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (4, '2021-01-27 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (5, '2021-01-28 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (6, '2021-01-29 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (7, '2021-01-30 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (8, '2021-01-31 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (9, '2021-02-01 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (10, '2021-02-05 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (11, '2021-02-04 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (12, '2021-02-03 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (13, '2021-02-06 00:00:00', 0, 0, 0, NULL);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_registry
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_user
-- ----------------------------
INSERT INTO `xxl_job_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
