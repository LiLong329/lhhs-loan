package com.lhhs.loan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.utils.SpringContextHolder;
import com.lhhs.loan.common.utils.YunPianUtil;
import com.lhhs.loan.dao.domain.NoticeConfig;
import com.lhhs.loan.dao.domain.NoticeModel;
import com.lhhs.loan.dao.domain.NoticeRecord;
/**
 * 消息通知工具类
 * @author Administrator
 *
 */
public class NoticeUtils {
	
	private static final Logger LOGGER = Logger.getLogger(NoticeUtils.class);
	
	private static NoticeModelService noticeModelService=(NoticeModelService) SpringContextHolder.getBean(NoticeModelService.class);
	private static NoticeConfigService noticeConfigService=(NoticeConfigService) SpringContextHolder.getBean(NoticeConfigService.class);
	private static UserApi userApi=(UserApi) SpringContextHolder.getBean(UserApi.class);
	private static NoticeRecordService noticeRecordService=(NoticeRecordService) SpringContextHolder.getBean(NoticeRecordService.class);
	
    /**
     * 创建消息
     * @param entity
     * @param map 消息内容替换参数集合
     */
    public static void createMsg(NoticeModel entity,Map<String, String> map) {
    	//下一节点审批人
    	String nextAssignee = entity.getReceiver();
    	entity.setModelStatus("1");//已选中
    	String englishName = entity.getEnglishName();
    	//处理相同节点不同情况英文名称
    	englishName = englishName
    			.replace("_jsqr", "_qr")
    			.replace("_qzqr", "_qr")
    			.replace("_cp", "")
    			.replace("xh_qy", "")
    			.replace("xh_qz", "");
//    			.replace("_hq", "")
//    			.replace("_bf", "");
    	entity.setEnglishName(englishName);
    	entity.setReceiver(null);
		//查询已选中的模板
		@SuppressWarnings("unchecked")
		List<NoticeModel> NoticeModels = noticeModelService.queryList(entity);
		if (NoticeModels!=null&&NoticeModels.size()>0) {
			
			//查询邮件和短信的总开关状态
			NoticeConfig param = new NoticeConfig();
			param.setType("1");
			NoticeConfig emailConfig = noticeConfigService.get(param);
			param.setType("2");
			NoticeConfig shortMsgConfig = noticeConfigService.get(param);
			String emailStatus = emailConfig.getStatus();
			String shortMsgStatus = shortMsgConfig.getStatus();
			
			for (NoticeModel noticeModel:NoticeModels) {
				if ("1".equals(noticeModel.getNoticeType())) {//发邮件
					if ("1".equals(emailStatus)) {//邮件总开关开启
						//TODO
					}else {
						LOGGER.debug("邮件接口配置未启用");
					}
				}else if ("2".equals(noticeModel.getNoticeType())) {//发短信
					if ("1".equals(shortMsgStatus)) {//短信总开关开启
						sendMsg(entity, map, nextAssignee, noticeModel);
					}else {
						LOGGER.debug("短信接口配置未启用");
					}
				}else if ("3".equals(noticeModel.getNoticeType())) {//站内消息
					sendMsg(entity, map, nextAssignee, noticeModel);
				}
			}
		}
	}
    /**
     * 发送消息
     * @param entity
     * @param map
     * @param nextAssignee
     * @param noticeModel
     */
	private static void sendMsg(NoticeModel entity, Map<String, String> map, String nextAssignee, NoticeModel noticeModel) {
		String content = noticeModel.getContent();
		for (String key:map.keySet()) {
			content = content.replace(key, map.get(key));
		}
		List<User> userList = null;
		//业务类根据下一节点审批人查询用户
		if ("1".equals(noticeModel.getModelType())) {
			if (StringUtils.isNotEmpty(nextAssignee)) {
				String [] assigneeArray = nextAssignee.split(",");
				userList = userApi.queryListByIds(assigneeArray);	
			}
		}else {
			//资金类按岗位查询员工
			User user = new User();
			user.setUnionId(entity.getUnionId());
			user.setCompanyId(entity.getCompanyId());
			String receiver = noticeModel.getReceiver();
			if (StringUtils.isNotEmpty(receiver)) {
				//去掉客户经理岗位
				receiver = receiver.replace("CustomerManager,", "");
				user.setQuartersIds(receiver.split(","));
				userList = userApi.getByQuarters(user);
				//单独查询客户经理
				User customerManager = userApi.get(entity.getCustomerManager().toString());
				//判断集合中是否包含该用户
				boolean flag = true;
				if (userList.size()>0) {
					for (int i = 0; i < userList.size(); i++) {
						User user2 = userList.get(i);
						Long userId = user2.getUserId();
						if (userId.equals(customerManager.getUserId())) {
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					//报单的客户经理
					userList.add(customerManager);
				}
			}
		}
		List<NoticeRecord> noticeRecords = new ArrayList<NoticeRecord>();
		if (userList!=null&&userList.size()>0) {
			for (User user:userList) {
				NoticeRecord noticeRecord = new NoticeRecord();
				noticeRecord.setField1(noticeModel.getName());//节点名称
				noticeRecord.setContent(content);
				noticeRecord.setReceiverId(user.getUserId().toString());
				noticeRecord.setNoticeType(noticeModel.getNoticeType());
				noticeRecord.setStatus("0");//未读
				noticeRecord.setCreateTime(new Date());
				noticeRecord.setUnionId(user.getUnionId());
				noticeRecord.setCompanyId(user.getCompanyId());
				noticeRecords.add(noticeRecord);
				if ("2".equals(noticeModel.getNoticeType())) {
					//发短信
					if (StringUtils.isNoneEmpty(user.getMobile())) {
						YunPianUtil.sendEncryptSMSMessage(user.getMobile(), content);
					}
				}
			}
			noticeRecordService.saveList(noticeRecords);
		}
	}
}