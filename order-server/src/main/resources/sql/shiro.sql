/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : shiro

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 08/02/2021 16:16:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户\r\n主姓名',
  `account_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '银行\r\n卡号',
  `account_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '帐户密码',
  `account_balance` double NULL DEFAULT NULL COMMENT '帐户余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account_info
-- ----------------------------
INSERT INTO `account_info` VALUES (2, '张三的账户', '1', '', 10000);

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` int NULL DEFAULT NULL,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (1, 'add', 2);
INSERT INTO `t_permission` VALUES (2, 'del', 1);
INSERT INTO `t_permission` VALUES (3, 'update', 2);
INSERT INTO `t_permission` VALUES (4, 'query', 3);
INSERT INTO `t_permission` VALUES (5, 'user:query', 1);
INSERT INTO `t_permission` VALUES (6, 'user:edit', 2);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `rolename` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'admin');
INSERT INTO `t_role` VALUES (2, 'manager');
INSERT INTO `t_role` VALUES (3, 'normal');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'zhoushuai', '4dfe1f6fac27cb0245030055e9810525', '256d6c1b706fa06048766238aeab1980');
INSERT INTO `t_user` VALUES (2, 'tom', '0e33507757f95453f823f9e9836b4085', '3040ef0fb82a4a8c55cb8005bffee4a9');
INSERT INTO `t_user` VALUES (3, 'jack', '37f6b357d8ef09f6530f99b81bb6ce0a', '220a11e741eb525262b2404894ac5b52');
INSERT INTO `t_user` VALUES (40, 'zssss', '12345678', 'idjdjdjjd');
INSERT INTO `t_user` VALUES (41, 'zssss', '12345678', 'idjdjdjjd');
INSERT INTO `t_user` VALUES (42, 'zssss', '12345678', 'idjdjdjjd');
INSERT INTO `t_user` VALUES (43, 'zssss', '12345678', 'idjdjdjjd');
INSERT INTO `t_user` VALUES (44, 'zssss', '12345678', 'idjdjdjjd');
INSERT INTO `t_user` VALUES (45, 'zssss', '12345678', 'idjdjdjjd');
INSERT INTO `t_user` VALUES (46, 'zssss', '12345678', 'idjdjdjjd');
INSERT INTO `t_user` VALUES (47, 'zssss', '12345678', 'idjdjdjjd');
INSERT INTO `t_user` VALUES (48, 'zssss', '12345678', 'idjdjdjjd');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `user_id` int NULL DEFAULT NULL,
  `role_id` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1);
INSERT INTO `t_user_role` VALUES (1, 3);
INSERT INTO `t_user_role` VALUES (2, 2);
INSERT INTO `t_user_role` VALUES (2, 3);
INSERT INTO `t_user_role` VALUES (3, 3);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
INSERT INTO `undo_log` VALUES (11, 98159630464585728, '192.168.10.1:8091:98159586151763968', 'serializer=jackson', 0x7B7D, 1, '2021-01-28 20:51:20', '2021-01-28 20:51:20', NULL);
INSERT INTO `undo_log` VALUES (14, 98159630431031296, '192.168.10.1:8091:98159586151763968', 'serializer=jackson', 0x7B7D, 1, '2021-01-28 20:51:20', '2021-01-28 20:51:20', NULL);

SET FOREIGN_KEY_CHECKS = 1;
