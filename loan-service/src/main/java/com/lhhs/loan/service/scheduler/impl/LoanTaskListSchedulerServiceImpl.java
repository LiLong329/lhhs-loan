/**
 * Project Name:loan-service
 * File Name:LoanTaskListServiceImpl.java
 * Package Name:com.lhhs.loan.service.scheduler.impl
 * Date:2017年10月25日下午5:58:09
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.scheduler.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.dao.domain.LoanTaskList;
import com.lhhs.loan.service.scheduler.LoanTaskListSchedulerService;
import com.lhhs.loan.service.scheduler.LoanTaskListService;
import com.lhhs.loan.service.transport.OrderInfoService;
import com.lhhs.workflow.bs.inf.ActCommentService;
import com.lhhs.workflow.dao.domain.TaskVo;

/**
 * 定时任务列表
 */
//@Service
public class LoanTaskListSchedulerServiceImpl  implements LoanTaskListSchedulerService {
	private static final Logger logger = LoggerFactory.getLogger(LoanTaskListSchedulerServiceImpl.class);
	@Autowired(required = false)
	private OrderInfoService orderInfoService;
	@Autowired(required = false)
	private ActCommentService actCommentService;
	@Autowired(required = false)
	private LoanTaskListService loanTaskListService;
	/**
	 * 
	 * TODO 更新审批节点及报单状态至碰碰旺
	 * 
	 * @see com.lhhs.loan.service.transport.OrderInfoService#updateApproval(com.lhhs.loan.dao.domain.LoanOrderInfo,
	 *      com.lhhs.loan.dao.domain.LoanOrderInfoDetail,
	 *      com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs)
	 */
	//更新审批节点及报单状态至碰碰旺
	//每2分钟执行一次
//	@Scheduled(cron = "0 0/2 * * * ? ")
//	public void taskLhhs()  {
//		//查询定时任务，上次执行时间
//		LoanTaskList loanTaskList=new LoanTaskList();
//		loanTaskList.setTaskName("loan_loan_one");
//		loanTaskList=loanTaskListService.get(loanTaskList);
//		//如果为空或者为状态不为正常跳过
//		if(loanTaskList==null ||(!"03".equals(loanTaskList.getStatus())&&!"04".equals(loanTaskList.getStatus()))){
//			return ;
//		}
//		String status=loanTaskList.getStatus();
//		Date LastModifyTime=new Date();
//		int minute=DateUtils.minuteSBetween(loanTaskList.getLastModifyTime(),new Date());
//		// 解锁超过30分钟的状态
//		if("04".equals(status)&&minute>10){
//			//更新记录
//			loanTaskList.setStatus("03");
//			loanTaskList.setLastModifyTime(LastModifyTime);
//			loanTaskListService.update(loanTaskList);
//			return;
//		}else if(!"03".equals(status)){
//			return;
//		}
//		//查询定时任务，上次执行时间，查询审批记录取已变化的订单
//		Date begin_date=loanTaskList.getBeginDate();
//		TaskVo taskvo=new TaskVo();
//		taskvo.setBeginDate(begin_date);
//		taskvo.setProcDefKey("loan_loan_one");
//		//排除会签
//		taskvo.setNoPassList("5");
//		taskvo.setNoTaskDefKeyList("bujian_cp,bujian_cpxh_qy,bujian_cpxh_qz");
//		List<TaskVo> list=actCommentService.queryChangeBusinessList(taskvo);
//		if(list==null||list.size()<1){
//			return;
//		}
//		
//		loanTaskList.setOldLastModifyTime(loanTaskList.getLastModifyTime());
//		loanTaskList.setLastModifyTime(LastModifyTime);
//		loanTaskList.setStatus("04");
//		//更新记录
//		int flag=loanTaskListService.update(loanTaskList);
//		
//		if(flag<1)return;
//		for(TaskVo vo :list){
//			try {
//				orderInfoService.updateApproval(vo.getBusinessId(),vo.getProcInsId());
//			} catch (Exception e) {
//				logger.error("同步碰碰旺订单状态报错：订单号:"+vo.getBusinessId()+",流程号："+vo.getProcInsId());
//				
//			}
//		}
//		loanTaskList.setBeginDate(new Date());
//		loanTaskList.setEndDate(new Date());
//		loanTaskList.setLastModifyTime(new Date());
//		loanTaskList.setOldLastModifyTime(null);
//		loanTaskList.setStatus("03");
//		//更新记录
//		flag=loanTaskListService.update(loanTaskList);
//	}


}

