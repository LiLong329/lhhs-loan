/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.110：lhhs
Source Server Version : 50636
Source Host           : 192.168.1.110:3306
Source Database       : loan-test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-04-04 15:58:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `act_user`
-- ----------------------------
DROP TABLE IF EXISTS `act_user`;
CREATE TABLE `act_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `des` varchar(100) DEFAULT NULL COMMENT '描述',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色',
  `no` varchar(32) DEFAULT NULL COMMENT '员工编号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别:男,女',
  `email` varchar(100) DEFAULT NULL COMMENT 'EMAIL',
  `phone` varchar(60) DEFAULT NULL COMMENT '电话',
  `status` varchar(10) DEFAULT NULL COMMENT '状态(有效：03；无效：99)',
  `department_id` varchar(10) DEFAULT NULL COMMENT '部门',
  `company_id` varchar(32) DEFAULT NULL COMMENT '所属公司',
  `union_id` varchar(32) DEFAULT NULL COMMENT '归属集团',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_user` varchar(32) DEFAULT NULL COMMENT '最后操作人',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后操作时间',
  `field1` varchar(50) DEFAULT NULL,
  `field2` varchar(50) DEFAULT NULL,
  `field3` varchar(128) DEFAULT NULL,
  `field4` varchar(128) DEFAULT NULL,
  `field5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `ACT_USER_IDX2` (`user_name`),
  KEY `ACT_USER_IDX1` (`role_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流管理员表';

-- ----------------------------
-- Records of act_user
-- ----------------------------
