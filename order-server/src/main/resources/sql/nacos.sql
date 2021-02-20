/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 08/02/2021 16:17:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (13, 'order-server.properties', 'DEFAULT_GROUP', 'zhoushuai.test= 11122kssjs1111yyy', 'e3d858ae9c064584b1fd3db4bfbe2fb6', '2021-01-29 11:15:51', '2021-01-29 12:18:21', NULL, '0:0:0:0:0:0:0:1', '', 'd5060113-e85d-4757-88f6-bb24e3b4f82e', '11', '', '', 'properties', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint UNSIGNED NOT NULL,
  `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 1, 'order-server-dev.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjrrrrrrrrrrr', '4be6a5d670819c85a06e08940b551e1e', '2021-01-29 10:18:23', '2021-01-29 10:18:23', NULL, '0:0:0:0:0:0:0:1', 'I', '');
INSERT INTO `his_config_info` VALUES (1, 2, 'order-server-dev.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjrrrrrrrrrrr', '4be6a5d670819c85a06e08940b551e1e', '2021-01-29 10:19:50', '2021-01-29 10:19:51', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (0, 3, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjddddddd', '3e780695ad276dca6d4920673e2c2d57', '2021-01-29 10:20:28', '2021-01-29 10:20:28', NULL, '0:0:0:0:0:0:0:1', 'I', '');
INSERT INTO `his_config_info` VALUES (2, 4, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjddddddd', '3e780695ad276dca6d4920673e2c2d57', '2021-01-29 10:20:44', '2021-01-29 10:20:44', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 5, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjdddddddhhhh', '2404f0999e0cd24fa354d5b8f813b8f8', '2021-01-29 10:21:05', '2021-01-29 10:21:06', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 6, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjdddddddhhhh', '2404f0999e0cd24fa354d5b8f813b8f8', '2021-01-29 10:26:39', '2021-01-29 10:26:39', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 7, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjdddddddhhhhhhhgggggggggggggggggggggggg', 'a4b3803b40be825df33c6a9fef78da29', '2021-01-29 10:27:19', '2021-01-29 10:27:19', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 8, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= rrreeee', 'dfd1b31cc825eadd9220348bc5a540c8', '2021-01-29 10:30:49', '2021-01-29 10:30:49', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 9, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= rrreeee', 'dfd1b31cc825eadd9220348bc5a540c8', '2021-01-29 10:38:19', '2021-01-29 10:38:19', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 10, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= rrreeee888', '9b90cc299634823b6982cc61d508f0b8', '2021-01-29 10:46:21', '2021-01-29 10:46:21', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 11, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= rrreeee888uuu\n', '616f617d4504834c2282f3416b773051', '2021-01-29 10:47:12', '2021-01-29 10:47:12', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 12, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= rrreeee888uuulllllllllllll\n', '715e2559d18fb7689efe94d39c91bcef', '2021-01-29 10:49:27', '2021-01-29 10:49:27', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 13, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= rrreee\n', 'd4a5c62a8327912d47026077dcb348a0', '2021-01-29 10:50:22', '2021-01-29 10:50:22', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (0, 14, 'order-server.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjs1111', '9c4717d754024523c2a4e7b36b44bc6a', '2021-01-29 11:15:51', '2021-01-29 11:15:51', NULL, '0:0:0:0:0:0:0:1', 'I', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (13, 15, 'order-server.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjs1111', '9c4717d754024523c2a4e7b36b44bc6a', '2021-01-29 11:16:22', '2021-01-29 11:16:23', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (13, 16, 'order-server.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjs1111yyyyyyyyyyyy', '4bf39e7eb790ce23152e517b6da5eb90', '2021-01-29 12:03:22', '2021-01-29 12:03:22', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (13, 17, 'order-server.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjs1111yyyyyyyyyyyyjj', '9acb9d437c4c9330604aae9c9e2550b0', '2021-01-29 12:03:44', '2021-01-29 12:03:44', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (0, 18, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjsssssssssssssssssssssssssssssssssssssssssssssssssss', '24b7cb2488439cb5166b3d0699303429', '2021-01-29 12:04:45', '2021-01-29 12:04:45', NULL, '0:0:0:0:0:0:0:1', 'I', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (17, 19, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjsssssssssssssssssssssssssssssssssssssssssssssssssss', '24b7cb2488439cb5166b3d0699303429', '2021-01-29 12:05:07', '2021-01-29 12:05:07', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (17, 20, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjsssssjjjjjjjjj', 'd61046fb5b27ce6b1d5744def210dd5d', '2021-01-29 12:07:25', '2021-01-29 12:07:25', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (17, 21, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjsssssjjjjjjjjj111', '0e32c50526fb1b04782fabb01d43601d', '2021-01-29 12:07:43', '2021-01-29 12:07:44', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (2, 22, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= rrreee333\n', '5c047c1bd754c312c603033b5045dc2d', '2021-01-29 12:11:13', '2021-01-29 12:11:14', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (13, 23, 'order-server.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjs1111yyyyyyyyyyyyjjkkkkkkkkkkkkkkkk', '66804a52d42011a27eb4b11995622556', '2021-01-29 12:11:33', '2021-01-29 12:11:33', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (17, 24, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjsssssjjjjjjjjj11133', 'f0e4e5fcde3a659563d5354ceccc3118', '2021-01-29 12:11:44', '2021-01-29 12:11:44', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (17, 25, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjsssssjjjjjjjjj1113322', 'fdc424693b4f263cc690c25ef5017f51', '2021-01-29 12:15:09', '2021-01-29 12:15:09', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (17, 26, 'order-server-local.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjsjsssssjjjjjjjjj1113322111', 'f7b977ac2e1fefd11c70babfd7bd9535', '2021-01-29 12:15:25', '2021-01-29 12:15:26', NULL, '0:0:0:0:0:0:0:1', 'D', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (13, 27, 'order-server.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjs1111yyyyyyyyyyyyjjkkkkkkkkkkkkkkkk11', '6e36ad164edcd7828290fc4442c9e441', '2021-01-29 12:15:38', '2021-01-29 12:15:39', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');
INSERT INTO `his_config_info` VALUES (13, 28, 'order-server.properties', 'DEFAULT_GROUP', '', 'zhoushuai.test= kssjs1111yyy', 'bc6ef2be8b7e6f962bba9b94e9be402e', '2021-01-29 12:18:21', '2021-01-29 12:18:21', NULL, '0:0:0:0:0:0:0:1', 'U', 'd5060113-e85d-4757-88f6-bb24e3b4f82e');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
