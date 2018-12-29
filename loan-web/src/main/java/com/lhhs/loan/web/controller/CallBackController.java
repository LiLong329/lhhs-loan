package com.lhhs.loan.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.dao.domain.LoanCallBack;
import com.lhhs.loan.dao.domain.SipAccount;
import com.lhhs.loan.service.CallBackService;
import com.lhhs.loan.service.SipAccountService;

@Controller
@RequestMapping("/callBack")
public class CallBackController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CallBackController.class);
	
	@Autowired
	private CallBackService callBackService;
	@Autowired
	private SipAccountService sipAccountService;
	
	@RequestMapping("/receiveCdrUrl")
	@ResponseBody
	public void receiveCdrUrl(HttpServletRequest request,HttpEntity<String> formEntity){
		String result = formEntity.getBody();
		LOGGER.info("拨打电话回调参数", result);
		LoanCallBack entity  = JSON.parseObject(result,LoanCallBack.class);
		if(null!=entity.getCalleridNumber()){
			SipAccount sipAccount=new SipAccount();
			sipAccount.setSipnumber(entity.getCalleridNumber());
			sipAccount=sipAccountService.get(sipAccount);
			if(null!=sipAccount){
				entity.setUserId(sipAccount.getUserId());
				entity.setDeptId(sipAccount.getDeptId());
				entity.setCompanyId(sipAccount.getCompanyId());
				entity.setUnionId(sipAccount.getUnionId());
				entity.setCreateTime(new Date());
				callBackService.save(entity);
			}
		}else{
			LOGGER.info("拨打电话回调参数sip账号异常", result);
		}
	}
	
}
