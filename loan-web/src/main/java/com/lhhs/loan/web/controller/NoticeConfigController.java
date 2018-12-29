package com.lhhs.loan.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.dao.domain.NoticeConfig;
import com.lhhs.loan.service.NoticeConfigService;
/**

 * 通知信息接口设置
 * @author HB
 */
@Controller
@RequestMapping("/noticeConfig")
public class NoticeConfigController {
	
	@Autowired
	private NoticeConfigService noticeConfigService;
	/**
	 * 设置页面跳转
	 */
	@RequestMapping("/toConfig/{type}")
	public ModelAndView toConfig(@PathVariable("type") String type){
		ModelAndView mav = new ModelAndView("noticeConfig/noticeConfig");
		NoticeConfig param = new NoticeConfig();
		param.setType(type);
		NoticeConfig noticeConfig = noticeConfigService.get(param);
		mav.addObject("noticeConfig", noticeConfig);
		mav.addObject("type", type);
		return mav;
	}
	/**
	 * 保存模板设置信息
	 */
	@ResponseBody
	@RequestMapping("/sava")
	public Map<String, String> sava(NoticeConfig noticeConfig){
		Map<String,String> map = new HashMap<String,String>();
		int code = noticeConfigService.update(noticeConfig);
		if(code>0){
			map.put(SystemConst.retCode, SystemConst.SUCCESS);
		}else{
			map.put(SystemConst.retCode, SystemConst.FAIL);
		}
		return map;
	}
}

