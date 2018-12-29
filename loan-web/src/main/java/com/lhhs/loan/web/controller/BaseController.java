package com.lhhs.loan.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		dateFormat.setLenient(false);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/** 错误消息 */
	protected static final Map<String, Object> ERROR_MESSAGE = new HashMap<String, Object>();
	
	/** 成功消息 */
	protected static final Map<String, Object> SUCCESS_MESSAGE = new HashMap<String, Object>();
	
	static{
		SUCCESS_MESSAGE.put("retCode",	 "00");
		SUCCESS_MESSAGE.put("retMsg", "操作完成");
		ERROR_MESSAGE.put("retCode", "01");
		ERROR_MESSAGE.put("retMsg", "操作失败");
	}

}
