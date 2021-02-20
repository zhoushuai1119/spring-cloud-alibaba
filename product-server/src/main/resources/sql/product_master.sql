/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : product_master1

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 20/02/2021 10:08:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `LXJP` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_product_0
-- ----------------------------
DROP TABLE IF EXISTS `t_product_0`;
CREATE TABLE `t_product_0`  (
  `ID` bigint NOT NULL COMMENT 'ID',
  `PRODUCT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '产品名称',
  `PRICE` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `PRODUCT_TYPE` int NULL DEFAULT NULL COMMENT '产品类型',
  `CREATE_TIME` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_product_1
-- ----------------------------
DROP TABLE IF EXISTS `t_product_1`;
CREATE TABLE `t_product_1`  (
  `ID` bigint NOT NULL COMMENT 'ID',
  `PRODUCT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '产品名称',
  `PRICE` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `PRODUCT_TYPE` int NULL DEFAULT NULL COMMENT '产品类型',
  `CREATE_TIME` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
