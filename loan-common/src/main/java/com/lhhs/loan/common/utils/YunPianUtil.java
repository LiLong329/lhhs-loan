package com.lhhs.loan.common.utils;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class YunPianUtil {

	private static Logger logger = LoggerFactory.getLogger(YunPianUtil.class);
	
	private static String sendSMSUrl = "https://sms.yunpian.com/v1/sms/send.json";
	private static String apikey = "5d027049f1b01dcdfd95b8a1e125bb8f";
	
	/**
	 * 智能匹配模板发送
	 * 云片网短信
	 * @param mobilePhone
	 * @param smsContent
	 * @return
	 */
	public static Boolean sendEncryptSMSMessage(String mobilePhone,String smsContent){
		logger.info("发送普通短信.........[开始]" + mobilePhone + "内容:" + smsContent);
		//String sendSMSUrl = "https://sms.yunpian.com/v1/sms/send.json";
		Map<String,String> paramMap = new HashMap<String,String>();
		try {
			paramMap.put("apikey", apikey);
			paramMap.put("text", smsContent);
			paramMap.put("mobile", mobilePhone);
			//String returnValue = HttpClientUtil.sendPostRequest(this.sendSMSUrl, paramMap, "UTF-8");
			String returnValue = HttpClientUtil.sendHttpPost(sendSMSUrl, paramMap);
			logger.info("短信发送结果:" + returnValue);
			Map<String,Object> map = JSON.parseObject(returnValue);
			Integer code = (Integer) map.get("code");
			logger.info("发送模板短信.........[完成]" + mobilePhone);
			if(code.intValue()==0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}