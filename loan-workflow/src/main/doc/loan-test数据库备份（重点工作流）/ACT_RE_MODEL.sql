/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.110：lhhs
Source Server Version : 50636
Source Host           : 192.168.1.110:3306
Source Database       : loan-test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-04-04 15:57:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ACT_RE_MODEL`
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_MODEL`;
CREATE TABLE `ACT_RE_MODEL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RE_MODEL
-- ----------------------------
INSERT INTO `ACT_RE_MODEL` VALUES ('201711091427140407dt6', '2', null, '', null, '2017-11-09 14:27:14', '2017-11-09 14:27:14', '1', '{\"name\":null,\"revision\":1,\"description\":\"\"}', null, '201711091427140642ibw', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('2017110914274583398q8', '2', null, '', null, '2017-11-09 14:27:45', '2017-11-09 14:27:45', '2', '{\"name\":null,\"revision\":2,\"description\":\"\"}', null, '201711091427458544cgn', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109160701762hkgz', '2', null, '', null, '2017-11-09 16:07:01', '2017-11-09 16:07:01', '3', '{\"name\":null,\"revision\":3,\"description\":\"\"}', null, '20171109160701783ncrv', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109160741896rqao', '2', null, '', null, '2017-11-09 16:07:41', '2017-11-09 16:07:41', '4', '{\"name\":null,\"revision\":4,\"description\":\"\"}', null, '20171109160741907hd1p', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('201711091610216143ww2', '2', null, '', null, '2017-11-09 16:10:21', '2017-11-09 16:10:21', '5', '{\"name\":null,\"revision\":5,\"description\":\"\"}', null, '20171109161021635d3oj', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109161033063rt8r', '2', null, '', null, '2017-11-09 16:10:33', '2017-11-09 16:10:33', '6', '{\"name\":null,\"revision\":6,\"description\":\"\"}', null, '2017110916103308114aj', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('201711091615272704ajg', '2', null, '', null, '2017-11-09 16:15:27', '2017-11-09 16:15:27', '7', '{\"name\":null,\"revision\":7,\"description\":\"\"}', null, '201711091615272967l31', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109161624321w1wq', '2', null, '', null, '2017-11-09 16:16:24', '2017-11-09 16:16:24', '8', '{\"name\":null,\"revision\":8,\"description\":\"\"}', null, '20171109161624345nh3n', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109161815557hjts', '2', null, '', null, '2017-11-09 16:18:15', '2017-11-09 16:18:15', '9', '{\"name\":null,\"revision\":9,\"description\":\"\"}', null, '20171109161815578g7ku', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109161835833dut6', '2', null, '', null, '2017-11-09 16:18:35', '2017-11-09 16:18:35', '10', '{\"name\":null,\"revision\":10,\"description\":\"\"}', null, '20171109161835854e661', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109161907742f44p', '2', null, '', null, '2017-11-09 16:19:07', '2017-11-09 16:19:07', '11', '{\"name\":null,\"revision\":11,\"description\":\"\"}', null, '20171109161907761awcr', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109162503305u8e2', '2', null, '', null, '2017-11-09 16:25:03', '2017-11-09 16:25:03', '12', '{\"name\":null,\"revision\":12,\"description\":\"\"}', null, '20171109162503327c600', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109162537296tcx1', '2', null, '', null, '2017-11-09 16:25:37', '2017-11-09 16:25:37', '13', '{\"name\":null,\"revision\":13,\"description\":\"\"}', null, '20171109162537315z0v7', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109162741761s75f', '2', null, '', null, '2017-11-09 16:27:41', '2017-11-09 16:27:41', '14', '{\"name\":null,\"revision\":14,\"description\":\"\"}', null, '20171109162741776qc2e', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171109162841186bopc', '2', null, '', null, '2017-11-09 16:28:41', '2017-11-09 16:28:41', '15', '{\"name\":null,\"revision\":15,\"description\":\"\"}', null, '20171109162841220zf43', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('201711220937084416x1s', '10', '哈尔滨呼叫系统对接小贷', 'loan_loan_two', null, '2017-11-22 09:37:08', '2017-11-28 18:12:46', '1', '{\"name\":\"哈尔滨呼叫系统对接小贷\",\"revision\":1,\"description\":\"哈尔滨呼叫系统对接小贷\"}', null, '2017112209370846049fe', '201711221431095217hi5', '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171122094316997feil', '4', '放款审批', 'loan_loan_one', null, '2017-11-22 09:43:16', '2017-11-29 16:46:23', '3', '{\"name\":\"放款审批\",\"revision\":3,\"description\":\"\"}', '20171103141859615vsrg', '20171122094317023jx1h', '20171129164623025ye2w', '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171207210115500nt6x', '2', '自主或三方放款（渠道本地电销）.bpmn20.xml', 'loan_loan_three', null, '2017-12-07 21:01:15', '2017-12-07 21:01:15', '1', '{\"name\":\"自主或三方放款（渠道/本地电销）\",\"revision\":1,\"description\":null}', '201712072019331635tk1', '2017120721011551295s1', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20171207210126519qwyx', '2', '自主或三方放款（异地电销）.bpmn20.xml', 'loan_loan_four', null, '2017-12-07 21:01:26', '2017-12-07 21:01:26', '1', '{\"name\":\"自主或三方放款（异地电销）\",\"revision\":1,\"description\":null}', '20171207201941308c436', '20171207210126524bhla', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20180115183432352ozi3', '4', '流程测试', 'atest', null, '2018-01-15 18:34:32', '2018-01-16 09:49:49', '1', '{\"name\":\"流程测试\",\"revision\":1,\"description\":\"流程测试\"}', null, '20180115183432371hx1r', '20180116094948841ykt0', '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20180116100957365m5f6', '2', '高成文测试', '测试', null, '2018-01-16 10:09:57', '2018-01-16 10:09:57', '1', '{\"name\":\"高成文测试\",\"revision\":1,\"description\":\"测试模块\"}', null, '20180116100957373g7v7', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20180125160734608nrow', '2', '三方放款（异地电销）.bpmn20.xml', 'loan_loan_six', null, '2018-01-25 16:07:35', '2018-01-25 16:07:35', '1', '{\"name\":\"三方放款（异地电销）\",\"revision\":1,\"description\":null}', '20171208192651740fmpk', '20180125160734644zxum', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20180125162840793hysx', '2', '自主或三方放款（异地电销）.bpmn20.xml', 'loan_loan_four', null, '2018-01-25 16:28:41', '2018-01-25 16:28:41', '2', '{\"name\":\"自主或三方放款（异地电销）\",\"revision\":2,\"description\":null}', '20180116095052355sl3d', '201801251628408154y21', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20180201170138426misg', '2', '自主或三方放款（渠道本地电销）.bpmn20.xml', 'loan_loan_three', null, '2018-02-01 17:01:38', '2018-02-01 17:01:38', '2', '{\"name\":\"自主或三方放款（渠道/本地电销）\",\"revision\":2,\"description\":null}', '201802011701321753c1n', '20180201170138443cvij', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('2018031517025177027l6', '2', '测试', 'test', null, '2018-03-15 17:02:52', '2018-03-15 17:02:52', '1', '{\"name\":\"测试\",\"revision\":1,\"description\":\"测试工作流\"}', null, '20180315170251778rvxb', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('2018031517030051051ax', '2', '测试', 'test', null, '2018-03-15 17:03:01', '2018-03-15 17:03:01', '2', '{\"name\":\"测试\",\"revision\":2,\"description\":\"\"}', null, '20180315170300522vobr', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20180315170336422av2q', '2', 'OA系统', 'test', null, '2018-03-15 17:03:36', '2018-03-15 17:03:36', '3', '{\"name\":\"OA系统\",\"revision\":3,\"description\":\"\"}', null, '20180315170336438sqt4', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('20180326201849541zztc', '2', '三方放款（渠道本地电销.bpmn20.xml', 'loan_loan_five', null, '2018-03-26 20:18:50', '2018-03-26 20:18:50', '1', '{\"name\":\"三方放款（渠道/本地电销）\",\"revision\":1,\"description\":null}', '20171208192641546nwpk', '20180326201849567r4hx', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('201804021015108376zsu', '2', '自主或三方放款（渠道本地电销）.bpmn20.xml', 'loan_loan_three', null, '2018-04-02 10:15:11', '2018-04-02 10:15:11', '3', '{\"name\":\"自主或三方放款（渠道/本地电销）\",\"revision\":3,\"description\":null}', '201802011701321753c1n', '20180402101510855w01v', null, '');
INSERT INTO `ACT_RE_MODEL` VALUES ('943c099dea224a19be981310a42f7ca5', '5', '放款审批', 'loan_loan_one', null, '2017-10-25 16:50:04', '2017-10-26 17:39:47', '1', '{\"name\":\"放款审批\",\"revision\":1,\"description\":\"\"}', 'f082148911e845548b93da2ef23f885b', 'bf640cac6ea041dabb3c5055f14e254a', '46ec214932f04ce584a5b74ec7c51de5', '');
INSERT INTO `ACT_RE_MODEL` VALUES ('eb6fa77d34fb4a16b0bb3c37e1b4bb73', '8', '放款审批', 'loan_loan_one', null, '2017-10-27 11:23:08', '2017-11-03 14:15:26', '2', '{\"name\":\"放款审批\",\"revision\":2,\"description\":\"\"}', '430e46dd265440549deaebbc82520454', '1b513292cdbe46fda2063a7cb9421133', '65d907da30e84afba1d0d83c7a1b0c0e', '');
