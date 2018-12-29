/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.110：lhhs
Source Server Version : 50636
Source Host           : 192.168.1.110:3306
Source Database       : loan-test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-04-04 15:57:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ACT_RE_PROCDEF`
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_PROCDEF`;
CREATE TABLE `ACT_RE_PROCDEF` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RE_PROCDEF
-- ----------------------------
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_five:1:20171207195737287qie2', '2', null, '居间业务', 'loan_loan_five', '1', '201712071957364064zqg', '居间业务.bpmn20.xml', '居间业务.loan_loan_five.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_five:2:201712072019020230d1y', '2', null, '三方放款（渠道/本地电销）', 'loan_loan_five', '2', '20171207201901758l8dm', '三方放款（渠道本地电销.bpmn20.xml', '三方放款（渠道本地电销.loan_loan_five.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_five:3:201712072110157219ah0', '2', null, '三方放款（渠道/本地电销）', 'loan_loan_five', '3', '201712072110146402zy7', '三方放款（渠道本地电销.bpmn20.xml', '三方放款（渠道本地电销.loan_loan_five.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_five:4:2017120721101954809e5', '2', null, '三方放款（渠道/本地电销）', 'loan_loan_five', '4', '201712072110189425r9i', '三方放款（渠道本地电销.bpmn20.xml', '三方放款（渠道本地电销.loan_loan_five.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_five:5:20171207211413688924g', '4', null, '三方放款（渠道/本地电销）', 'loan_loan_five', '5', '20171207211413414guww', '三方放款（渠道/本地电销）.bpmn20.xml', '三方放款（渠道/本地电销）.loan_loan_five.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_five:6:20171207211910657naqf', '3', null, '三方放款（渠道/本地电销）', 'loan_loan_five', '6', '20171207211910207i2te', '三方放款（渠道本地电销.bpmn20.xml', '三方放款（渠道本地电销.loan_loan_five.png', null, '1', '1', '2', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_five:7:20171208192642623a72s', '2', null, '三方放款（渠道/本地电销）', 'loan_loan_five', '7', '20171208192641546nwpk', '三方放款（渠道本地电销.bpmn20.xml', '三方放款（渠道本地电销.loan_loan_five.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_four:1:20171206095804274rsu7', '2', null, '放款审批-异地电销', 'loan_loan_four', '1', '201712060958039412fv7', '放款审批-异地电销.bpmn20.xml', '放款审批-异地电销.loan_loan_four.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_four:2:20171207201941617potz', '2', null, '自主或三方放款（异地电销）', 'loan_loan_four', '2', '20171207201941308c436', '自主或三方放款（异地电销）.bpmn20.xml', '自主或三方放款（异地电销）.loan_loan_four.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_four:3:2018011609505846076ss', '2', null, '自主或三方放款（异地电销）', 'loan_loan_four', '3', '20180116095052355sl3d', '自主或三方放款（异地电销）.bpmn20.xml', '自主或三方放款（异地电销）.loan_loan_four.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_four:4:20180201170022688c6pt', '2', null, '自主或三方放款（异地电销）', 'loan_loan_four', '4', '2018020117002082612fd', '自主或三方放款（异地电销）.bpmn20.xml', '自主或三方放款（异地电销）.loan_loan_four.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:10:4e4bb5111ab74c8d91c9b79bfe751d4d', '2', null, '放款审批', 'loan_loan_one', '10', 'c8f80c9b518644a38587e132ac68078a', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:11:09f1f344f54d4e57875306f4220caf1c', '2', null, '放款审批', 'loan_loan_one', '11', '8803189dd50c40d59b4c5c0fca5876eb', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:12:32a9e12656964c109794c7a2dc591e68', '2', null, '放款审批', 'loan_loan_one', '12', '66946605b82e48df8c0398abef3693e9', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:13:62bfcc1dae464d7186dc1624d5a687f2', '2', null, '放款审批', 'loan_loan_one', '13', 'd76af50e72484a8da3cd64d22dc120a5', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:14:201711021705352450trr', '2', null, '放款审批', 'loan_loan_one', '14', '20171102170533529r6c0', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:15:20171102200336446kwr2', '2', null, '放款审批', 'loan_loan_one', '15', '20171102200335769743e', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:16:2017110314190007448bz', '2', null, '放款审批', 'loan_loan_one', '16', '20171103141859615vsrg', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:17:20171122094414835r5fs', '2', null, '放款审批', 'loan_loan_one', '17', '20171122094412525ohmj', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:1:73d4c9e1da6f425caaa0381b0904c65b', '2', null, '放款审批', 'loan_loan_one', '1', 'f082148911e845548b93da2ef23f885b', '晋商睦家公司放款流程.bpmn20.xml', '晋商睦家公司放款流程.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:2:c62e78c93e2a40c1930501f17baae107', '2', null, '放款审批', 'loan_loan_one', '2', 'dd3f4e266e1b48a4bbb82c66915ea45b', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:3:1bc372c37ca34a7a865a8f5ee7c6d8d5', '2', null, '放款审批', 'loan_loan_one', '3', 'ee06950dd82144d0b9aa217bf1bd0111', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:4:91b6298bf91f474ab606f6f7fa19f056', '2', null, '放款审批', 'loan_loan_one', '4', 'f445a885f238498daea4e6e79eb122ca', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:5:8baf12ca72104f15b554bd2586753c05', '2', null, '放款审批', 'loan_loan_one', '5', '818f8a9c36bc415cb47c48f2a1a91bd5', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:6:bebcdd0b6e514ecf981bc918f1314d5c', '2', null, '放款审批', 'loan_loan_one', '6', 'a02455a7f5e24f8ca12e929fb009f071', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:7:afb3d757dbd24e5d87dd8baf57140fa6', '2', null, '放款审批', 'loan_loan_one', '7', '2b9fdacef6f2490a8fb76041ef8101dd', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:8:78875cbe402647e385b545c4ed48ece8', '2', null, '放款审批', 'loan_loan_one', '8', '430e46dd265440549deaebbc82520454', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_one:9:7a15504197f74ce3aa778cf60a7c4c79', '2', null, '放款审批', 'loan_loan_one', '9', 'af9d2f952f3146c08cf3cf2529e856a0', '放款审批.bpmn20.xml', '放款审批.loan_loan_one.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_six:1:20171207195747936g4lw', '2', null, '居间业务（哈尔滨）', 'loan_loan_six', '1', '20171207195747642v44g', '居间业务（哈尔滨）.bpmn20.xml', '居间业务（哈尔滨）.loan_loan_six.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_six:2:20171207201917367df6a', '2', null, '三方放款（异地电销）', 'loan_loan_six', '2', '201712072019171256b6a', '三方放款（异地电销）.bpmn20.xml', '三方放款（异地电销）.loan_loan_six.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_six:3:20171207211613563f6n9', '4', null, '三方放款（异地电销）', 'loan_loan_six', '3', '201712072116132675e7z', '三方放款（异地电销）.bpmn20.xml', '三方放款（异地电销）.loan_loan_six.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_six:4:2017120721191831572td', '3', null, '三方放款（异地电销）', 'loan_loan_six', '4', '20171207211917918veeb', '三方放款（异地电销）.bpmn20.xml', '三方放款（异地电销）.loan_loan_six.png', null, '1', '1', '2', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_six:5:2017120819265219561r0', '2', null, '三方放款（异地电销）', 'loan_loan_six', '5', '20171208192651740fmpk', '三方放款（异地电销）.bpmn20.xml', '三方放款（异地电销）.loan_loan_six.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_three:1:20171206095813782qr2m', '2', null, '放款审批-渠道', 'loan_loan_three', '1', '20171206095813469vddh', '放款审批-渠道.bpmn20.xml', '放款审批-渠道.loan_loan_three.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_three:2:20171207201933478re7l', '2', null, '自主或三方放款（渠道/本地电销）', 'loan_loan_three', '2', '201712072019331635tk1', '自主或三方放款（渠道本地电销）.bpmn20.xml', '自主或三方放款（渠道本地电销）.loan_loan_three.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_three:3:20180122144729295t7k7', '2', null, '自主或三方放款（渠道/本地电销）', 'loan_loan_three', '3', '20180122144728581qg5j', '自主或三方放款（渠道本地电销）.bpmn20.xml', '自主或三方放款（渠道本地电销）.loan_loan_three.png', null, '1', '1', '1', '');
INSERT INTO `ACT_RE_PROCDEF` VALUES ('loan_loan_three:4:201802011701324491e41', '2', null, '自主或三方放款（渠道/本地电销）', 'loan_loan_three', '4', '201802011701321753c1n', '自主或三方放款（渠道本地电销）.bpmn20.xml', '自主或三方放款（渠道本地电销）.loan_loan_three.png', null, '1', '1', '1', '');
