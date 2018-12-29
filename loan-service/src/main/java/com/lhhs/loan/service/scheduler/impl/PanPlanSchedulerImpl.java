/**
 * Project Name:loan-service
 * File Name:PanPlanSchedulerImpl.java
 * Package Name:com.lhhs.loan.service.scheduler.impl
 * Date:2017年9月27日上午11:02:14
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.scheduler.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.LoanPayPlanMapper;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.NoticeModel;
import com.lhhs.loan.service.NoticeUtils;
import com.lhhs.loan.service.scheduler.PanPlanScheduler;

/**
 * ClassName:PanPlanSchedulerImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年9月27日 上午11:02:14 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class PanPlanSchedulerImpl implements PanPlanScheduler {
	private static final Logger logger = LoggerFactory.getLogger(PanPlanSchedulerImpl.class);
	
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private LoanPayPlanMapper payPlanMapper;
	
	@Override
	@Scheduled(cron = "0 0 0-23/1 * * ? ")
	public void orderStatusTask() {
		int cleanup=loanOrderInfoMapper.updateLoanOrderInfoStatusCleanUp();
		loanOrderInfoMapper.updateLoanTransMainCleanUp();
		int paid=loanOrderInfoMapper.updateLoanOrderInfoStatusPaid();
		int over=loanOrderInfoMapper.updateLoanOrderInfoStatusOver();
		logger.debug("orderStatusTask更新订单表，已结清："+cleanup+",已还款："+paid+",已逾期："+over);
	}
	/**
	 * 逾期、将到期信息通知
	 */
	@Override
	@Scheduled(cron = "0 0 7 * * ? ")
	public void payPlanNotice() {
		logger.debug("逾期、将到期信息通知定时任务开始");
		List<LoanPayPlan> list = payPlanMapper.getPayPlanAndCompanyList();
		if (list!=null&&list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				LoanPayPlan loanPayPlan = list.get(i);
				String orderNo = loanPayPlan.getOrderNo();
				String customerName = loanPayPlan.getCustomerName();
				if (customerName==null) {
					customerName = "";
				}
				BigDecimal repaymentTotal = loanPayPlan.getRepaymentTotal();
				BigDecimal repaymentCapital = loanPayPlan.getRepaymentCapital();
				BigDecimal repaymentInterest = loanPayPlan.getRepaymentInterest();
				Date repaymentCapitalTime = loanPayPlan.getRepaymentCapitalTime();
				Date repaymentInterestTime = loanPayPlan.getRepaymentInterestTime();
				Short period = loanPayPlan.getPeriod();
				BigDecimal paidTotal = loanPayPlan.getPaidTotal();
				BigDecimal paidCapital = loanPayPlan.getPaidCapital();
				BigDecimal paidInterest = loanPayPlan.getPaidInterest();
				String field5 = loanPayPlan.getField5();
				//封装模板查询信息
				LoanOrderInfo loanOrderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
				if (loanOrderInfo!=null) {
					NoticeModel entity = new NoticeModel();
					entity.setModelType("2");//资金类
					entity.setUnionId(loanOrderInfo.getUnionId());
					entity.setCompanyId(loanOrderInfo.getCompanyId());
					entity.setCustomerManager(loanOrderInfo.getCustomerManager());	
					
					Map<String, String> map = new HashMap<>();
					map.put("【报单编号】", orderNo);
					map.put("【期数】", period+"");
					map.put("【用户】", customerName);
					int days = 0;
					if (repaymentCapitalTime==null||repaymentCapitalTime.equals(repaymentInterestTime)) {
						days = daysBetween(repaymentInterestTime);
						map.put("【时间】", DateUtils.Date2String(repaymentInterestTime,"yyyy-MM-dd"));
						map.put("【金额】", repaymentTotal.subtract(paidTotal).toString());
						sendMsg(field5, entity, map, days);
					}else{
							days = daysBetween(repaymentCapitalTime);
							map.put("【时间】", DateUtils.Date2String(repaymentCapitalTime,"yyyy-MM-dd"));
							map.put("【金额】", repaymentCapital.subtract(paidCapital).toString());
							sendMsg(field5, entity, map, days);
							days = daysBetween(repaymentInterestTime);
							map.put("【时间】", DateUtils.Date2String(repaymentInterestTime,"yyyy-MM-dd"));
							map.put("【金额】", repaymentInterest.subtract(paidInterest).toString());
							sendMsg(field5, entity, map, days);
					}
					
				}
			}
		}
	}
	private void sendMsg(String field5, NoticeModel entity, Map<String, String> map, int days) {
		if ("huan".equals(field5)) {//还款计划
			if (days==-1) {//逾期 只提醒一天
				entity.setEnglishName("hkyqtx");
				map.put("【天】", Math.abs(days)+"");
				//发送消息
				NoticeUtils.createMsg(entity, map );
			}else if (days>=0&&days<=2) {//将到期
				entity.setEnglishName("hkjdqtx");
				NoticeUtils.createMsg(entity, map );
			}
		}else if ("fu".equals(field5)) {//待付款计划
			if (days==-1) {//逾期 只提醒一天
				entity.setEnglishName("fkyqtx");
				map.put("【天】", Math.abs(days)+"");
				NoticeUtils.createMsg(entity, map );
			}else if (days>=0&&days<=2) {//将到期
				entity.setEnglishName("fkjdqtx");
				NoticeUtils.createMsg(entity, map );
			}
		}
	}
    public static int daysBetween(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long time1 = calendar.getTimeInMillis();                 
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long time2 = calendar.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
       return Integer.parseInt(String.valueOf(between_days));     
    }  
}

