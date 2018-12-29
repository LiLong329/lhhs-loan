/**
 * Project Name:loan-service
 * File Name:PanPlanSchedulerImpl.java
 * Package Name:com.lhhs.loan.service.scheduler.impl
 * Date:2017年9月27日上午11:02:14
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.scheduler.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.enums.EnumOrderNode;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.NoticeModel;
import com.lhhs.loan.service.CustomerManagerService;
import com.lhhs.loan.service.NoticeUtils;
import com.lhhs.loan.service.scheduler.OrderInfoWorkFlowScheduler;
import com.lhhs.workflow.bs.inf.ActTaskService;
import com.lhhs.workflow.dao.domain.ActVo;

/**
 * 订单启动工作流定时器
 * @see
 */
@Service
public class OrderInfoWorkFlowSchedulerImpl implements OrderInfoWorkFlowScheduler {
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoWorkFlowSchedulerImpl.class);
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private CustomerManagerService customerManagerService;
	@Reference
	private UserApi userApi;
	@Autowired(required = false)
	private ActTaskService actTaskService;
	//每5分钟执行一次
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void startProcess(){
		LoanOrderInfo loanOrderInfo=new LoanOrderInfo();
		Page page=new Page();
		page.setPageSize(500);
		loanOrderInfo.setPage(page);
		List<LoanOrderInfo> list=loanOrderInfoMapper.queryOrderInfoNoProcList(loanOrderInfo);
		
		if(list==null)return;
		for(int i=0;i<list.size();i++){
			LoanOrderInfo temp=list.get(i);
			ActVo parm=new ActVo();
			String assignee="";
			String assigneeName="";
			//防并发处理
			temp.setOrderStatusOld(-1);
			temp.setOrderStatus(Integer.parseInt(EnumOrderNode.CHUPING.getId()));
			int flag=loanOrderInfoMapper.updateByProcInsId(temp);
			if(flag<1)continue;
			//初评状态查询报单人
			if(!StringUtils.isEmpty(temp.getProviderNo())){
				LoanProviderInfo provider =	customerManagerService.selProviderInfo(temp.getProviderNo());
				//备注信息
				if(provider!=null){
				parm.setRemark(provider.getMobileNo());
				assignee=provider.getProviderName();
        		assigneeName=provider.getProviderName();
        		User user = new User();
        		user.setCompanyId(temp.getCompanyId());
        		user.setMobile(provider.getMobileNo());
		        User  loanEmp =  userApi.get(user);
	        	if(loanEmp!=null){
	        		assignee=loanEmp.getUsername();
	        	}
		        }
			}
			if(StringUtils.isEmpty(assignee)){
				assignee=temp.getProviderNo();
			}
			if(StringUtils.isEmpty(assignee)){
				assignee="sys";
			}
			/**
			if(StringUtils.isEmpty(assigneeName)){
				assigneeName=assignee;
			}
			**/
			//报单编号
			parm.setBusinessId(temp.getOrderNo());
			//设置业务归属集团
			parm.setUnionId(temp.getUnionId());
			//设置业务归属公司
			parm.setCompanyId(temp.getCompanyId());
			//设置业务归属事业部或者组
			parm.setDepId(temp.getDepId());
			parm.setUserId(assignee);
			parm.setUserName(assigneeName);
			parm.setTitle(temp.getProductName());
			//设置流程KEY
			String flowName="loan_loan_three";
			//居间电销
			if("1".equals(temp.getBusinessType())&&"1".equals(temp.getCustomerSource())){
				flowName ="loan_loan_six";
			//居间本地
			}else if("1".equals(temp.getBusinessType())&&"0".equals(temp.getCustomerSource())){
				flowName ="loan_loan_five";
			//非居间本地
			}else if("0".equals(temp.getBusinessType())&&"0".equals(temp.getCustomerSource())){
				flowName ="loan_loan_three";
			//非居间电销
			}else if("0".equals(temp.getBusinessType())&&"1".equals(temp.getCustomerSource())){
				flowName ="loan_loan_four";
			}
			parm.setProcDefKey(flowName);
			try{
				//启动流程
				parm=actTaskService.startProcess(parm);
				temp.setProcInsId(parm.getProcInsId());
				temp.setNextTaskName(parm.getNextTaskName());
				temp.setNextTaskDefKey(parm.getNextTaskDefKey());
				temp.setNextAssignee(parm.getNextAssignee());
				temp.setNextAssigneeName(parm.getNextAssigneeName());
				//temp.setOrderStatus(Integer.parseInt(EnumOrderNode.CHUPING.getId()));
				temp.setOrderStatusOld(Integer.parseInt(EnumOrderNode.CHUPING.getId()));
				loanOrderInfoMapper.updateByProcInsId(temp);
				
				//初评消息提醒
				NoticeModel entity = new NoticeModel();
				entity.setEnglishName(EnumOrderNode.CHUPING.getKey());
				entity.setModelType("1");//业务类
				entity.setReceiver(parm.getNextAssignee());
				Map<String, String> map = new HashMap<>();
				map.put("【报单编号】", temp.getOrderNo());
				NoticeUtils.createMsg(entity, map );
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			
		}
	}
}

