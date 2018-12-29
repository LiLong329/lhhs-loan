/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.110：lhhs
Source Server Version : 50636
Source Host           : 192.168.1.110:3306
Source Database       : loan-test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-04-04 15:55:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ACT_GE_PROPERTY`
-- ----------------------------
DROP TABLE IF EXISTS `ACT_GE_PROPERTY`;
CREATE TABLE `ACT_GE_PROPERTY` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_GE_PROPERTY
-- ----------------------------
INSERT INTO `ACT_GE_PROPERTY` VALUES ('next.dbid', '1', '1');
INSERT INTO `ACT_GE_PROPERTY` VALUES ('schema.history', 'create(5.21.0.0)', '1');
INSERT INTO `ACT_GE_PROPERTY` VALUES ('schema.version', '5.21.0.0', '1');
