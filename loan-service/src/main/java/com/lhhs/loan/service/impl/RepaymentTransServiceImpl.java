/**
 * Project Name:loan-service
 * File Name:RepaymentTransServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年8月8日上午10:50:04
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.dao.LoanPayPlanMapper;
import com.lhhs.loan.dao.LoanPayRecordMapper;
import com.lhhs.loan.dao.LoanTransMainMapper;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayRecord;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.vo.PayRecordVo;
import com.lhhs.loan.service.RepaymentTransService;
import com.lhhs.loan.service.account.AccountTransactionService;

/**
 * ClassName:RepaymentTransServiceImpl <br/>
 * Function: 执行还款交易服务  <br/>
 * Date:     2017年8月8日 上午10:50:04 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class RepaymentTransServiceImpl implements RepaymentTransService {
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LoanTransMainMapper loanTransMainMapper;
	@Autowired
	private LoanPayPlanMapper loanPayPlanMapper;
	@Autowired
	private AccountTransactionService accountTransactionService;
	@Autowired
	private LoanPayRecordMapper loanPayRecordMapper;
	
	/**
	 * 当期执行还款服务(正常、部分、逾期还款)
	 * @see com.lhhs.loan.service.RepaymentTransService#repaymentHandler(com.lhhs.loan.dao.domain.vo.PayRecordVo)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> repaymentHandler(PayRecordVo entity) {
		Map<String, Object> ret = new HashMap<String,Object>();
		
		if(entity == null || StringUtils.isEmpty(entity.getPayFlag()) || !entity.getPayFlag().equals("0") 
			|| entity.getActualTransTime() == null || StringUtils.isEmpty(entity.getTransMainId())
			|| entity.getPlanId() == null || entity.getPaidTotal() == null || (entity.getPaidTotal().compareTo(new BigDecimal(0)))<=0){
			logger.error("还款数据不正确");
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "还款数据不正确");
			return ret;
		}
		if(entity.getPaidTotal().compareTo((entity.getPaidOverdue().add(entity.getPaidCompensate()))) <= 0){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "还款总额必须大于罚息与违约金之和");
			return ret;
		}
		//查询当期还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(entity.getTransMainId());
		LoanPayPlan payPlan = loanPayPlanMapper.selectByPrimaryKey(entity.getPlanId().toString());
		if(transMain == null || payPlan == null){
			logger.error("还款数据不正确");
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "还款数据不正确");
			return ret;
		}
		
		//应还本时间
		String repaymentCapitalTime = DateUtils.formatDate(payPlan.getRepaymentCapitalTime());
		//应还息时间
		String repaymentInterestTime = DateUtils.formatDate(payPlan.getRepaymentInterestTime());
		
		if(entity.getIsCapita().equals("1") && entity.getIsInterest().equals("1") 
				&& !StringUtils.isEmpty(repaymentInterestTime) && !StringUtils.isEmpty(repaymentCapitalTime)
				&& DateUtils.daysBetween(repaymentInterestTime,repaymentCapitalTime)!=0){
			logger.error("还息日和还本日不是同一天，请先执行还息，再执行还本或提前还款");
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "还息日和还本日不是同一天，请先执行还息，再执行还本或提前还款");
			return ret;
		}
		//实际还款时间
		String payDate = DateUtils.formatDate(entity.getActualTransTime());
		//还款数据：
		LoanPayRecord payObj = new LoanPayRecord();
		payObj.setPlanId(payPlan.getId());//还款计划ID
		payObj.setTransMainId(payPlan.getTransMainId());
		payObj.setPeriod(payPlan.getPeriod());//当前期数
		payObj.setActualTransTime(entity.getActualTransTime());//实际还款时间
		payObj.setPaidOverdue(entity.getPaidOverdue());//逾期罚息
		payObj.setPaidCompensate(entity.getPaidCompensate());//违约金
		payObj.setPaidTotal(entity.getPaidTotal());//页面输入还款总额
		payObj.setTransRemark(entity.getTransRemark());//交易备注
		
		//根据还款日期计算逾期利息，更新到还款计划表(逾期利息一直累加)
		this.overdueCalculationByCapital(entity, true);
		
		if(entity.getIsInterest().equals("1") && entity.getIsCapita().equals("0")){//执行还息
			if(DateUtils.daysBetween(repaymentInterestTime,payDate)<=0){//当期有效还息时间里执行还息
				//实际还息=还款总额(页面输入)-逾期利息(页面输入)-违约金(页面输入)
				payObj.setPaidInterest(entity.getPaidTotal().subtract(entity.getPaidOverdue()).subtract(entity.getPaidCompensate()));
			}else{//当期逾期还息
				//实际还息=还款总额(页面输入)-逾期利息(页面输入)-违约金(页面输入)
				payObj.setPaidInterest(entity.getPaidTotal().subtract(entity.getPaidOverdue()).subtract(entity.getPaidCompensate()));
			}
		}else if(entity.getIsInterest().equals("0") && entity.getIsCapita().equals("1")){//执行还本
			//如果实际还款时间小于应还本时间时，提示是否要提前还款
			if(StringUtils.isEmpty(repaymentCapitalTime)||DateUtils.daysBetween(repaymentCapitalTime,payDate) < 0){
				logger.error("还本时间小于应还本时间，如要提前还本，请执行全部还款");
				ret.put(SystemConst.retCode, SystemConst.FAIL);
				ret.put(SystemConst.retMsg, "还本时间小于应还本时间，如要提前还本，请执行全部还款");
				return ret;
			}
			//实际还本=还款总额(页面输入)-逾期利息(页面输入)-违约金(页面输入)
			payObj.setPaidCapital(entity.getPaidTotal().subtract(entity.getPaidOverdue()).subtract(entity.getPaidCompensate()));
		}else{//执行还息还本(先还息，后还本顺序)
			//如果实际还款时间小于应还本时间时，提示是否要提前还款
			if(StringUtils.isEmpty(repaymentCapitalTime)||DateUtils.daysBetween(repaymentCapitalTime,payDate) < 0){
				logger.error("还本时间小于应还本时间，如要提前还本，请执行全部还款");
				ret.put(SystemConst.retCode, SystemConst.FAIL);
				ret.put(SystemConst.retMsg, "还本时间小于应还本时间，如要提前还本，请执行全部还款");
				return ret;
			}
			//(应还利息-已还利息)大于(还款总额(页面输入)-逾期利息(页面输入)-违约金(页面输入)) 此时只是利息部分还款
			//amount=还款总额(页面输入)-逾期利息(页面输入)-违约金(页面输入)
			BigDecimal amount = entity.getPaidTotal().subtract(entity.getPaidOverdue()).subtract(entity.getPaidCompensate());
			//剩余利息=应还利息-已还利息
			BigDecimal lastInterest = payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest());
			
			if(lastInterest.compareTo(amount)>=0){
				payObj.setPaidInterest(amount);
			}else{
				//剩余金额
				BigDecimal amount1 = amount.subtract(lastInterest);
				//剩余本金=应还本金-已还本金
				BigDecimal lastCapital = payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital());
				if(amount1.compareTo(lastCapital)==1){
					payObj.setPaidCapital(amount1);
					//实际应还息=应还利息-已还利息+还款多余的钱
					payObj.setPaidInterest(lastInterest.add(amount1.subtract(lastCapital)));
				}else{
					//实际应还息=应还利息-已还利息
					payObj.setPaidInterest(lastInterest);
					payObj.setPaidCapital(amount1);
				}
			}
		}
		//执行还款
		String code = accountTransactionService.loanPayTrans(payObj);
		ret.put(SystemConst.retCode, code);
		return ret;
	}

	/**
	 * 执行提前还款服务(全部还款)
	 * @see com.lhhs.loan.service.RepaymentTransService#repaymentAllHandler(com.lhhs.loan.dao.domain.vo.PayRecordVo)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> repaymentAllHandler(PayRecordVo entity) {
		Map<String, Object> ret = new HashMap<String,Object>();
		
		if(entity == null || StringUtils.isEmpty(entity.getPayFlag()) || !entity.getPayFlag().equals("1") 
			|| entity.getActualTransTime() == null || StringUtils.isEmpty(entity.getTransMainId())
			|| entity.getPaidTotal() == null || (entity.getPaidTotal().compareTo(new BigDecimal(0)))<=0){
			logger.error("还款数据不正确");
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "还款数据不正确");
			return ret;
		}
		if(entity.getPaidTotal().compareTo((entity.getPaidOverdue().add(entity.getPaidCompensate()).add(entity.getPaidInterest()))) <= 0){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "还款总额必须大于利息、罚息与违约金之和");
			return ret;
		}
		//重新生成还款计划
		Long planId = this.generatePayPlan(entity);
		if(planId == null || planId.compareTo(0L) == 0 || planId.compareTo(-1L) == 0){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			if(planId != null && planId.compareTo(-1L) == 0){
				ret.put(SystemConst.retMsg, "当前所选还款时间产生了逾期，无法提前还款");
			}else{
				ret.put(SystemConst.retMsg, "还款失败");
			}
			return ret;
		}
		
		//查询当期还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(entity.getTransMainId());
		//查询新的还款计划
		LoanPayPlan payPlan = loanPayPlanMapper.selectByPrimaryKey(planId.toString());
		if(transMain == null || payPlan == null){
			logger.error("还款数据不正确");
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "还款数据不正确");
			return ret;
		}
		//输入利息必须大于等于应收利息
		if(entity.getPaidInterest().compareTo(payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()))==-1){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "利息必须大于等于应还利息");
			return ret;
		}
		
		//还款数据：
		LoanPayRecord payObj = new LoanPayRecord();
		payObj.setPlanId(payPlan.getId());//还款计划ID
		payObj.setTransMainId(payPlan.getTransMainId());
		payObj.setPeriod(payPlan.getPeriod());//当前期数
		payObj.setActualTransTime(entity.getActualTransTime());//实际还款时间
		payObj.setPaidInterest(entity.getPaidInterest());//实还利息
		payObj.setPaidCapital(payPlan.getRepaymentCapital());//实还本金
		payObj.setPaidOverdue(entity.getPaidOverdue());//实还逾期罚息
		payObj.setPaidCompensate(entity.getPaidCompensate());//实还赔偿违约金
		payObj.setPaidTotal(entity.getPaidTotal());//页面输入还款总额
		payObj.setTransRemark(entity.getTransRemark());//交易备注
		//执行还款
		String code = accountTransactionService.loanPayTrans(payObj);
		if(SystemConst.FAIL.equals(code)){
			throw new RuntimeException("还款失败！");
		}else{
			ret.put(SystemConst.retCode, code);
		}
		return ret;
	}

	/**
	 * 还款逾期罚息计算---本金逾期算本金，利息逾期算利息
	 * @see com.lhhs.loan.service.RepaymentTransService#OverdueCalculation(com.lhhs.loan.dao.domain.vo.PayRecordVo, boolean)
	 */
	@Override
	public LoanPayRecord overdueCalculation(PayRecordVo entity,boolean flag) {
		if(entity == null || StringUtils.isEmpty(entity.getPayFlag()) || !entity.getPayFlag().equals("0") 
				|| entity.getActualTransTime() == null || StringUtils.isEmpty(entity.getTransMainId())
				|| entity.getPlanId() == null){
				logger.error("传入数据不正确");
				throw new RuntimeException("传入数据不正确");
			}
		//查询当期还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(entity.getTransMainId());
		LoanPayPlan payPlan = loanPayPlanMapper.selectByPrimaryKey(entity.getPlanId().toString());
		if(transMain == null || payPlan == null){
			logger.error("传入数据不正确，还款计划或放款主记录不存在");
			throw new RuntimeException("传入数据不正确");
		}
		//当没有传还款方式时，自己去处理
		if(entity.getIsCapita() == null && entity.getIsInterest() == null){
			if(payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()).compareTo(new BigDecimal(0)) == 1){
				entity.setIsCapita("1");
			}else{
				entity.setIsCapita("0");
			}
			if(payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()).compareTo(new BigDecimal(0)) == 1){
				entity.setIsInterest("1");
			}else{
				entity.setIsInterest("0");
			}
		}
		//返回对象
		LoanPayRecord retObj = new LoanPayRecord();
		retObj.setTransMainId(entity.getTransMainId());
		retObj.setPlanId(entity.getPlanId());
		retObj.setActualTransTime(entity.getActualTransTime());
		retObj.setRepaymentCapital(payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()));//剩余本金
		retObj.setRepaymentInterest(payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()));//剩余利息
		retObj.setCompensate(payPlan.getCompensate());//违约金(不提前还款时，违约金为0)
		
		//实际还款时间
		String payDate = DateUtils.formatDate(entity.getActualTransTime());
		//应还本时间
		String repaymentCapitalTime = DateUtils.formatDate(payPlan.getRepaymentCapitalTime());
		//应还息时间
		String repaymentInterestTime = DateUtils.formatDate(payPlan.getRepaymentInterestTime());
		//已还本时间
		String hasPayCapitalTime = DateUtils.formatDate(payPlan.getCapitalTime());
		//已还息时间
		String hasPayInterestTime = DateUtils.formatDate(payPlan.getInterestTime());
		
		if(hasPayInterestTime == null && repaymentInterestTime != null){
			hasPayInterestTime = repaymentInterestTime;
		}
		if(hasPayCapitalTime == null && repaymentCapitalTime != null){
			hasPayCapitalTime = repaymentCapitalTime;
		}
		
		if((hasPayInterestTime != null && DateUtils.daysBetween(hasPayInterestTime,payDate)<0)
		   ||(payPlan.getRepaymentCapital().compareTo(new BigDecimal(0))==1 && hasPayCapitalTime != null && DateUtils.daysBetween(hasPayCapitalTime,payDate)<0)){
			//应还款总额=剩余本金+剩余利息+罚息(0)+还款违约金
			if(entity.getIsCapita().equals("1") && entity.getIsInterest().equals("0")){
				retObj.setRepaymentTotal((payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()))
					  .add(payPlan.getCompensate()));
			}else if(entity.getIsCapita().equals("0") && entity.getIsInterest().equals("1")){
				retObj.setRepaymentTotal((payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()))
					  .add(payPlan.getCompensate()));
			}else{
				retObj.setRepaymentTotal((payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()))
					  .add(payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()))
					  .add(payPlan.getCompensate()));
			}
			
			retObj.setOverdueInterestTotal(new BigDecimal(0));//罚息
			
			return retObj;
		}
		//本金逾期罚息
		BigDecimal overdueCapital = new BigDecimal(0);
		int daysCapitalAll = 0;//还款日到还本日天数
		int daysCapital = 0;
		//利息逾期罚息
		BigDecimal overdueInterest = new BigDecimal(0);
		int daysInterestAll = 0;//还款日到还息日天数
		int daysInterest = 0;
		
		//剩余本金
		BigDecimal lastCapital = payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital());
		if(lastCapital.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(hasPayCapitalTime,payDate)>0){
			daysCapital = DateUtils.daysBetween(hasPayCapitalTime, payDate);
			daysCapitalAll = DateUtils.daysBetween(repaymentCapitalTime, payDate);
			//本金罚息=剩余本金×罚息利率×(还款日期-最后一次还本日期)的天数
			overdueCapital = lastCapital.multiply(new BigDecimal(transMain.getOverRate())).multiply(new BigDecimal(daysCapital));
		}
		//剩余利息
		BigDecimal lastInterest = payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest());
		if(lastInterest.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(hasPayInterestTime,payDate)>0){
			daysInterest = DateUtils.daysBetween(hasPayInterestTime, payDate);
			daysInterestAll = DateUtils.daysBetween(repaymentInterestTime, payDate);
			//利息罚息=剩余利息×罚息利率×(还款日期-最后一次还息日期)的天数
			overdueInterest = lastInterest.multiply(new BigDecimal(transMain.getOverRate())).multiply(new BigDecimal(daysInterest));
		}
		//更新还款计划的逾期罚息
		if(flag){
			//逾期罚息总额=原来罚息总额+本次逾期罚息总额
			payPlan.setOverdueInterestTotal(payPlan.getOverdueInterestTotal().add(overdueCapital).add(overdueInterest));
			//逾期本金罚息=原来本金罚息总额+本次本金罚息
			payPlan.setOverdueCapital(payPlan.getOverdueCapital().add(overdueCapital));
			//逾期利息罚息=原来利息罚息总额+本次利息罚息
			payPlan.setOverdueInterest(payPlan.getOverdueInterest().add(overdueInterest));
			//逾期天数(取逾期利息和逾期本金之间的最大值)
			payPlan.setOverdueDays(daysInterestAll>daysCapitalAll?daysInterestAll:daysCapitalAll);
			//计划标识为逾期
			if(payPlan.getOverdueDays() > 0){
				payPlan.setTransType(SystemConst.Status.STATUS89);
			}else{
				payPlan.setTransType(SystemConst.Status.STATUS03);
			}
			
			payPlan.setLastModifyTime(new Date());
			payPlan.setLastUser(entity.getLastUser());
			
			int num = loanPayPlanMapper.updateByPrimaryKeySelective(payPlan);
			if(num == 0){
				logger.error("还款计划表逾期罚息更新失败");
				throw new RuntimeException("还款计划表逾期罚息更新失败");
			}
		}
		//应还款总额=剩余本金+剩余利息+还款违约金+罚息(本金罚息+利息罚息)
		if(entity.getIsCapita().equals("1") && entity.getIsInterest().equals("0")){
			retObj.setRepaymentTotal((payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()))
					.add(payPlan.getCompensate()).add(overdueCapital).add(overdueInterest));
		}else if(entity.getIsCapita().equals("0") && entity.getIsInterest().equals("1")){
			retObj.setRepaymentTotal((payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()))
				  .add(payPlan.getCompensate()).add(overdueCapital).add(overdueInterest));
		}else{
			retObj.setRepaymentTotal((payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()))
				  .add(payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()))
				  .add(payPlan.getCompensate()).add(overdueCapital).add(overdueInterest));
		}
		retObj.setOverdueInterestTotal(overdueCapital.add(overdueInterest));
		return retObj;
	}

	/**
	 * 还款逾期罚息计算---所有逾期--都按照剩余本金进行计算
	 */
	public LoanPayRecord overdueCalculationByCapital(PayRecordVo entity,boolean flag) {
		if(entity == null || StringUtils.isEmpty(entity.getPayFlag()) || !entity.getPayFlag().equals("0") 
				|| entity.getActualTransTime() == null || StringUtils.isEmpty(entity.getTransMainId())
				|| entity.getPlanId() == null){
				logger.error("传入数据不正确");
				throw new RuntimeException("传入数据不正确");
			}
		//查询当期还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(entity.getTransMainId());
		LoanPayPlan payPlan = loanPayPlanMapper.selectByPrimaryKey(entity.getPlanId().toString());
		if(transMain == null || payPlan == null){
			logger.error("传入数据不正确，还款计划或放款主记录不存在");
			throw new RuntimeException("传入数据不正确");
		}
		//当没有传还款方式时，自己去处理
		if(entity.getIsCapita() == null && entity.getIsInterest() == null){
			if(payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()).compareTo(new BigDecimal(0)) == 1){
				entity.setIsCapita("1");
			}else{
				entity.setIsCapita("0");
			}
			if(payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()).compareTo(new BigDecimal(0)) == 1){
				entity.setIsInterest("1");
			}else{
				entity.setIsInterest("0");
			}
		}
		//返回对象
		LoanPayRecord retObj = new LoanPayRecord();
		retObj.setTransMainId(entity.getTransMainId());
		retObj.setPlanId(entity.getPlanId());
		retObj.setActualTransTime(entity.getActualTransTime());
		retObj.setRepaymentCapital(payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()));//剩余本金
		retObj.setRepaymentInterest(payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()));//剩余利息
		retObj.setCompensate(payPlan.getCompensate());//违约金(不提前还款时，违约金为0)
		
		//实际还款时间
		String payDate = DateUtils.formatDate(entity.getActualTransTime());
		//应还本时间
		String repaymentCapitalTime = DateUtils.formatDate(payPlan.getRepaymentCapitalTime());
		//应还息时间
		String repaymentInterestTime = DateUtils.formatDate(payPlan.getRepaymentInterestTime());
		//已还本时间
		String hasPayCapitalTime = DateUtils.formatDate(payPlan.getCapitalTime());
		//已还息时间
		String hasPayInterestTime = DateUtils.formatDate(payPlan.getInterestTime());
		//设置还款时间最小值
		if(hasPayInterestTime != null || hasPayCapitalTime != null){
			if(hasPayInterestTime != null && hasPayCapitalTime == null){
				retObj.setBeginTime(hasPayInterestTime);
			}else if(hasPayInterestTime == null && hasPayCapitalTime != null){
				retObj.setBeginTime(hasPayCapitalTime);
			}else{
				if(DateUtils.daysBetween(hasPayInterestTime,hasPayCapitalTime)<0){
					retObj.setBeginTime(hasPayInterestTime);
				}else{
					retObj.setBeginTime(hasPayCapitalTime);
				}
			}
		}else{
			if(DateUtils.daysBetween(payPlan.getInterestStart(),DateUtils.Date2String(new Date()))<0){
				retObj.setBeginTime(DateUtils.Date2String(new Date()));
			}else{
				retObj.setBeginTime(payPlan.getInterestStart());
			}
		}
		//如果还款时间小于最后一次还款时间时，将还款时间设置为最后一次还款时间
		if(retObj.getBeginTime() != null && DateUtils.daysBetween(retObj.getBeginTime(),payDate)<0){
			payDate = retObj.getBeginTime();
			retObj.setActualTransTime(DateUtils.String2Date(payDate));
		}
		
		if(hasPayInterestTime != null && DateUtils.daysBetween(hasPayInterestTime,payDate)<0){
			payDate = hasPayInterestTime;
		}
		if(hasPayCapitalTime != null && DateUtils.daysBetween(hasPayCapitalTime,payDate)<0){
			payDate = hasPayInterestTime;
		}
		
		if(hasPayInterestTime == null && repaymentInterestTime != null){
			hasPayInterestTime = repaymentInterestTime;
		}
		if(hasPayCapitalTime == null && repaymentCapitalTime != null){
			hasPayCapitalTime = repaymentCapitalTime;
		}
		
		//总的剩余本金
		BigDecimal totalLastCapital = transMain.getAmount().subtract(transMain.getPaidCapitalAmount());
		
		//本金逾期--产生本金罚息
		BigDecimal overdueCapital = new BigDecimal(0);
		int daysCapital = 0;
		//利息逾期--产生本金罚息
		BigDecimal overdueInterest = new BigDecimal(0);
		int daysInterest = 0;
		
		//剩余本金
		BigDecimal lastCapital = payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital());
		if(lastCapital.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(hasPayCapitalTime,payDate)>0){
			daysCapital = DateUtils.daysBetween(hasPayCapitalTime, payDate);
			//本金罚息=总的剩余本金×罚息利率×(还款日期-最后一次还本日期)的天数
			overdueCapital = totalLastCapital.multiply(new BigDecimal(transMain.getOverRate())).multiply(new BigDecimal(daysCapital));
		}
		//剩余利息
		BigDecimal lastInterest = payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest());
		if(lastInterest.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(hasPayInterestTime,payDate)>0){
			daysInterest = DateUtils.daysBetween(hasPayInterestTime, payDate);
			//利息罚息=总的剩余本金×罚息利率×(还款日期-最后一次还息日期)的天数
			overdueInterest = totalLastCapital.multiply(new BigDecimal(transMain.getOverRate())).multiply(new BigDecimal(daysInterest));
		}
		//去掉重复计算的金额
		if(payPlan.getCapitalTime() != null && payPlan.getInterestTime() != null){
			if(lastCapital.compareTo(new BigDecimal(0)) == 1 && lastInterest.compareTo(new BigDecimal(0)) == 1 && DateUtils.daysBetween(hasPayCapitalTime, payDate)>0 && DateUtils.daysBetween(hasPayInterestTime, payDate)>0){
				if(DateUtils.daysBetween(hasPayCapitalTime, hasPayInterestTime)>0){
					daysCapital = 0;
					overdueCapital = new BigDecimal(0);
				}else{
					daysInterest = 0;
					overdueInterest = new BigDecimal(0);
				}
			}
		}else if(payPlan.getInterestTime() != null && payPlan.getCapitalTime() == null){
			if(lastCapital.compareTo(new BigDecimal(0)) == 1 && lastInterest.compareTo(new BigDecimal(0)) == 1 && DateUtils.daysBetween(hasPayCapitalTime, payDate)>0 && DateUtils.daysBetween(hasPayInterestTime, payDate)>0){
				daysCapital = 0;
				overdueCapital = new BigDecimal(0);
			}
		}else if(payPlan.getInterestTime() == null && payPlan.getCapitalTime() != null){
			if(lastCapital.compareTo(new BigDecimal(0)) == 1 && lastInterest.compareTo(new BigDecimal(0)) == 1 && DateUtils.daysBetween(hasPayCapitalTime, payDate)>0 && DateUtils.daysBetween(hasPayInterestTime, payDate)>0){
				daysInterest = 0;
				overdueInterest = new BigDecimal(0);
			}
		}else{
			if(lastCapital.compareTo(new BigDecimal(0)) == 1 && lastInterest.compareTo(new BigDecimal(0)) == 1 
					&& DateUtils.daysBetween(hasPayCapitalTime, payDate)>0 
					&& DateUtils.daysBetween(hasPayInterestTime, payDate)>0){
				daysCapital = 0;
				overdueCapital = new BigDecimal(0);
			}
		}
		//逾期天数
		retObj.setOverdueDays(payPlan.getOverdueDays() + (daysInterest>daysCapital?daysInterest:daysCapital));
		
		//更新还款计划的逾期罚息
		if(flag){
			//逾期罚息总额=原来罚息总额+本次逾期罚息总额
			payPlan.setOverdueInterestTotal(payPlan.getOverdueInterestTotal().add(overdueCapital).add(overdueInterest));
			//逾期本金罚息=原来本金罚息总额+本次本金罚息
			payPlan.setOverdueCapital(payPlan.getOverdueCapital().add(overdueCapital));
			//逾期利息罚息=原来利息罚息总额+本次利息罚息
			payPlan.setOverdueInterest(payPlan.getOverdueInterest().add(overdueInterest));
			//逾期天数
			payPlan.setOverdueDays(payPlan.getOverdueDays() + (daysInterest>daysCapital?daysInterest:daysCapital));
			//计划标识为逾期
			if(payPlan.getOverdueDays() > 0){
				payPlan.setTransType(SystemConst.Status.STATUS89);
			}else{
				payPlan.setTransType(SystemConst.Status.STATUS03);
			}
			
			payPlan.setLastModifyTime(new Date());
			payPlan.setLastUser(entity.getLastUser());
			
			int num = loanPayPlanMapper.updateByPrimaryKeySelective(payPlan);
			if(num == 0){
				logger.error("还款计划表逾期罚息更新失败");
				throw new RuntimeException("还款计划表逾期罚息更新失败");
			}
		}
		//应还款总额=剩余本金+剩余利息+还款违约金+罚息(本金罚息+利息罚息)
		if(entity.getIsCapita().equals("1") && entity.getIsInterest().equals("0")){
			retObj.setRepaymentTotal((payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()))
					.add(payPlan.getCompensate()).add(payPlan.getOverdueInterestTotal()).add(overdueCapital).add(overdueInterest).subtract(payPlan.getPaidOverdue()));
		}else if(entity.getIsCapita().equals("0") && entity.getIsInterest().equals("1")){
			retObj.setRepaymentTotal((payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()))
				  .add(payPlan.getCompensate()).add(payPlan.getOverdueInterestTotal()).add(overdueCapital).add(overdueInterest).subtract(payPlan.getPaidOverdue()));
		}else{
			retObj.setRepaymentTotal((payPlan.getRepaymentCapital().subtract(payPlan.getPaidCapital()))
				  .add(payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest()))
				  .add(payPlan.getCompensate()).add(payPlan.getOverdueInterestTotal()).add(overdueCapital).add(overdueInterest).subtract(payPlan.getPaidOverdue()));
		}
		retObj.setOverdueInterestTotal(payPlan.getOverdueInterestTotal().add(overdueCapital).add(overdueInterest).subtract(payPlan.getPaidOverdue()));
		return retObj;
	}

	/**
	 *提前还款时，重新生成新的还款计划（新计划状态标识为提前） <br/>
	 * @see com.lhhs.loan.service.RepaymentTransService#generatePayPlan(com.lhhs.loan.dao.domain.vo.PayRecordVo)
	 */
	@Transactional(rollbackFor = Exception.class)
	public Long generatePayPlan(PayRecordVo entity){
		LoanPayPlan queryObj = new LoanPayPlan();
		queryObj.setTransMainId(entity.getTransMainId());
		queryObj.setStatus(SystemConst.Status.STATUS03);
		//查询还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(entity.getTransMainId());
		List<LoanPayPlan> payPlanList = loanPayPlanMapper.queryList(queryObj);
		
		if(transMain == null || payPlanList == null || payPlanList.size() == 0){
			logger.error("还款数据不正确");
			return 0L;
		}
		LoanPayPlan payPlan = new LoanPayPlan();
		BeanUtils.copyProperties(payPlanList.get(0), payPlan);
		//提前还款--重新生成新的还款计划之前--根据还款时间判断是否产生逾期
		//如果(剩余利息>0且还款时间大于应还息时间) 或  (还款时间大于应还本时间)--视为逾期
		//正常当期剩余利息=当期应还利息-当期已还利息
		BigDecimal tempInt = payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest());
		if((tempInt.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(payPlan.getRepaymentInterestTime(),entity.getActualTransTime()) > 0)
			|| (payPlan.getRepaymentCapitalTime() != null 
			&& DateUtils.daysBetween(payPlan.getRepaymentInterestTime(),payPlan.getRepaymentCapitalTime())>=0
			&& DateUtils.daysBetween(payPlan.getRepaymentCapitalTime(),entity.getActualTransTime()) > 0)){
			logger.error("当前所选还款日产生了逾期，无法提前还款");
			return -1L;
		}
		//剩余违约天数
		int compensateDays = DateUtils.daysBetween(entity.getActualTransTime(),transMain.getOverTime());
		if(DateUtils.daysBetween(DateUtils.Date2String(entity.getActualTransTime()),payPlan.getInterestStart()) > 0){
			compensateDays = DateUtils.daysBetween(payPlan.getInterestStart(),DateUtils.Date2String(transMain.getOverTime()));
		}
		if(compensateDays<0){
			compensateDays = 0;
		}
		//当前实际天数
		int days = DateUtils.daysBetween(payPlan.getInterestStart(), payPlan.getInterestEnd())+1;
		//实际使用天数
		int useDays = DateUtils.daysBetween(payPlan.getInterestStart(), DateUtils.Date2String(entity.getActualTransTime()))+1;
		if(useDays<0){
			useDays = 0;
		}
		//主键自增
		payPlan.setId(null);
		//已还本金=sum(还款记录中已还本金之和)
		//查询总和
		LoanPayRecord queryRecord = new LoanPayRecord();
		queryRecord.setTransMainId(entity.getTransMainId());
		queryRecord = loanPayRecordMapper.queryPaysum(queryRecord);
		BigDecimal paidCapitalTotal = new BigDecimal(0);
		if(queryRecord != null && queryRecord.getPaidCapital() != null){
			paidCapitalTotal = queryRecord.getPaidCapital();
		}
		//剩余本金=借款总额-已还本金
		BigDecimal lastCapital = transMain.getAmount().subtract(paidCapitalTotal);
		payPlan.setRepaymentCapital(lastCapital);
		//应还利息=当期利息/当期实际天数*实际使用天数
		BigDecimal interest = payPlan.getRepaymentInterest().divide(new BigDecimal(days),32, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(useDays));
		payPlan.setRepaymentInterest(interest);
		//应还总额=剩余本金+应还利息
		payPlan.setRepaymentTotal(lastCapital.add(interest));
		payPlan.setRepaymentCapitalTime(entity.getActualTransTime());//应还本时间
		payPlan.setRepaymentInterestTime(entity.getActualTransTime());//应还息时间
		payPlan.setOverdueDays(0);
		payPlan.setOverdueInterestTotal(new BigDecimal(0));
		payPlan.setOverdueCapital(new BigDecimal(0));
		payPlan.setOverdueInterest(new BigDecimal(0));
		//还款违约金=剩余本金×提前还款违约率×剩余天数
		payPlan.setCompensate(lastCapital.multiply(new BigDecimal(transMain.getBreachRate())).multiply(new BigDecimal(compensateDays)));
		payPlan.setTransType(SystemConst.Status.STATUS88);
		payPlan.setStatus(SystemConst.Status.STATUS03);
		payPlan.setInterestEnd(DateUtils.Time2String(entity.getActualTransTime()));
		payPlan.setCreateUser(entity.getCreateUser());
		payPlan.setCreateTime(new Date());
		payPlan.setLastModifyTime(new Date());
		payPlan.setLastUser(entity.getLastUser());
		
		int upNum = 0;
		for(LoanPayPlan temp : payPlanList){
			temp.setStatus(SystemConst.Status.STATUS99);
			temp.setLastModifyTime(new Date());
			temp.setLastUser(entity.getLastUser());
			upNum += loanPayPlanMapper.updateByPrimaryKeySelective(temp);
		}
		int inNum = loanPayPlanMapper.insertSelective(payPlan);
		if(upNum == payPlanList.size() && inNum == 1){
			if(payPlan.getId() == null){
				throw new RuntimeException("还款失败");
			}
			return payPlan.getId();
		}else{
			logger.error("提前还款时，重新生成新的还款计划失败");
			return 0L;
		}
	}

	/**
	 * 提前还款违约金计算、还款总额计算<br/>
	 * @see com.lhhs.loan.service.RepaymentTransService#compensateCalculation(com.lhhs.loan.dao.domain.vo.PayRecordVo)
	 */
	@Override
	public LoanPayRecord compensateCalculation(PayRecordVo entity) {
		if(entity == null || StringUtils.isEmpty(entity.getPayFlag()) || !entity.getPayFlag().equals("1") 
				|| entity.getActualTransTime() == null || StringUtils.isEmpty(entity.getTransMainId())){
				logger.error("传入数据不正确");
				throw new RuntimeException("传入数据不正确");
		}
		LoanPayPlan queryObj = new LoanPayPlan();
		queryObj.setTransMainId(entity.getTransMainId());
		queryObj.setStatus(SystemConst.Status.STATUS03);
		//查询还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(entity.getTransMainId());
		List<LoanPayPlan> payPlanList = loanPayPlanMapper.queryList(queryObj);
		
		if(transMain == null || payPlanList == null || payPlanList.size() == 0){
			logger.error("还款数据不正确");
			throw new RuntimeException("还款数据不正确");
		}
		//返回对象
		LoanPayRecord retObj = new LoanPayRecord();
		
		LoanPayPlan payPlan = new LoanPayPlan();
		BeanUtils.copyProperties(payPlanList.get(0), payPlan);
		
		//设置计息期间终止日到interestTime字段使用
	    //retObj.setInterestTime(DateUtils.String2Date(payPlan.getInterestEnd()));
		
		//提前还款--违约金计算--根据还款时间判断是否产生逾期
		//如果(剩余利息>0且还款时间大于应还息时间) 或  (还款时间大于应还本时间)--视为逾期
		//正常当期剩余利息=当期应还利息-当期已还利息
		BigDecimal tempInterest = payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest());
		if((tempInterest.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(payPlan.getRepaymentInterestTime(),entity.getActualTransTime()) > 0)
			|| (payPlan.getRepaymentCapitalTime() != null 
					&& DateUtils.daysBetween(payPlan.getRepaymentInterestTime(),payPlan.getRepaymentCapitalTime())>=0
			&& DateUtils.daysBetween(payPlan.getRepaymentCapitalTime(),entity.getActualTransTime()) > 0)){
			logger.error("当前所选还款日产生了逾期，无法提前还款");
			retObj.setOverdueDays(1);//到页面提示逾期
		}
		//剩余违约天数
		int compensateDays = DateUtils.daysBetween(entity.getActualTransTime(),transMain.getOverTime());
		if(DateUtils.daysBetween(DateUtils.Date2String(entity.getActualTransTime()),payPlan.getInterestStart()) > 0){
			compensateDays = DateUtils.daysBetween(payPlan.getInterestStart(),DateUtils.Date2String(transMain.getOverTime()));
		}
		if(compensateDays<0){
			compensateDays = 0;
		}
		//当前利息期间实际天数
		int days = DateUtils.daysBetween(payPlan.getInterestStart(), payPlan.getInterestEnd())+1;
		//当前利息实际使用天数
		int useDays = DateUtils.daysBetween(payPlan.getInterestStart(), DateUtils.Date2String(entity.getActualTransTime()))+1;
		
		if(useDays<0){
			useDays = 0;
		}
		
		retObj.setUseDays(useDays);
		retObj.setCompensateDays(compensateDays);
		retObj.setInterestStart(DateUtils.Date2String(DateUtils.String2Date(payPlan.getInterestStart())));
		retObj.setInterestEnd(DateUtils.Date2String(DateUtils.String2Date(payPlan.getInterestEnd())));
		retObj.setTransMainId(entity.getTransMainId());
		retObj.setActualTransTime(entity.getActualTransTime());
		//罚息(正常提前还款罚息为0)
		retObj.setOverdueInterestTotal(payPlan.getOverdueInterestTotal());
		
		//已还本金=sum(还款记录中已还本金之和)
		//查询总和
		LoanPayRecord queryRecord = new LoanPayRecord();
		queryRecord.setTransMainId(entity.getTransMainId());
		queryRecord = loanPayRecordMapper.queryPaysum(queryRecord);
		BigDecimal paidCapitalTotal = new BigDecimal(0);
		if(queryRecord != null && queryRecord.getPaidCapital() != null){
			paidCapitalTotal = queryRecord.getPaidCapital();
		}
		//剩余本金=借款总额-已还本金
		BigDecimal lastCapital = transMain.getAmount().subtract(paidCapitalTotal);
		retObj.setRepaymentCapital(lastCapital);
		//应还利息=当期利息/当期实际天数*实际使用天数-本期已还利息
		BigDecimal interest = (payPlan.getRepaymentInterest().divide(new BigDecimal(days),32, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(useDays))).subtract(payPlan.getPaidInterest());
		retObj.setRepaymentInterest(interest);
		//还款违约金=剩余本金×提前还款违约率×剩余天数
		BigDecimal compensate = lastCapital.multiply(new BigDecimal(transMain.getBreachRate())).multiply(new BigDecimal(compensateDays));
		retObj.setCompensate(compensate);
		//应还总额=剩余本金+应还利息+还款违约金
		retObj.setRepaymentTotal(lastCapital.add(interest).add(compensate));
		//剩余本金
		retObj.setRepaymentCapital(lastCapital);
		//剩余利息
		retObj.setRepaymentInterest(interest);
				
		return retObj;
	}

	/**
	 * 部分还本预期计算
	 */
	@Override
	public Map<String, Object> payCapitalCalculation(PayRecordVo entity) {
		Map<String,Object> ret = new HashMap<String,Object>();
		if(entity == null || StringUtils.isEmpty(entity.getTransMainId())){
			throw new RuntimeException("参数有误");
		}
		//查询主记录
		LoanTransMain mainQuery = new LoanTransMain();
		mainQuery.setTransMainId(entity.getTransMainId());
		LoanTransMain mainResult = loanTransMainMapper.getByEntity(mainQuery);
		//查询未还还款计划
		LoanPayPlan planQuery = new LoanPayPlan();
		planQuery.setTransMainId(entity.getTransMainId());
		planQuery.setStatus(SystemConst.Status.STATUS03);
		List<LoanPayPlan> palnResult = loanPayPlanMapper.queryList(planQuery);
		if(mainResult == null || palnResult == null || palnResult.size() == 0){
			throw new RuntimeException("参数有误");
		}
		
		
		BigDecimal lastCapital = mainResult.getAmount().subtract(mainResult.getPaidCapitalAmount());
		entity.setRepaymentCapital(lastCapital);
		List<LoanPayPlan> payPlanList = new ArrayList<LoanPayPlan>();
		//当期之后的期数的计算比例
		BigDecimal rate = (lastCapital.subtract(entity.getPaidCapital())).divide(lastCapital, 32, BigDecimal.ROUND_HALF_UP);
		if(rate.compareTo(new BigDecimal(0)) == -1){
			rate = new BigDecimal(0);
		}
		for(int i = 0;i<palnResult.size();i++){
			LoanPayPlan temp = new LoanPayPlan();
			BeanUtils.copyProperties(palnResult.get(i), temp);
			if(i == 0){
				entity.setInterestStart(temp.getInterestStart());
				if(temp.getCapitalTime() != null){
					entity.setInterestStart(DateUtils.Date2String(temp.getCapitalTime()));
				}
				entity.setInterestEnd(temp.getInterestEnd());
				//设置还款时间
				if(entity.getActualTransTime() == null){
					if(DateUtils.daysBetween(entity.getInterestStart(), DateUtils.Date2String(new Date()))<0){
						entity.setActualTransTime(DateUtils.String2Date(entity.getInterestStart()));
					}else{
						entity.setActualTransTime(new Date());
					}
				}
				//是否逾期计算
				BigDecimal tempInt = temp.getRepaymentInterest().subtract(temp.getPaidInterest());
				if((tempInt.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(temp.getRepaymentInterestTime(),entity.getActualTransTime()) > 0)
					|| (temp.getRepaymentCapitalTime() != null 
					&& DateUtils.daysBetween(temp.getRepaymentInterestTime(),temp.getRepaymentCapitalTime())>=0
					&& DateUtils.daysBetween(temp.getRepaymentCapitalTime(),entity.getActualTransTime()) > 0)){
					logger.error("当前所选还款日产生了逾期，无法提前部分还本");
					entity.setOverdueDays(1);//到页面提示逾期
				}
				
				//首次还款计划生成时的首次每期利息
				BigDecimal firstInterest = temp.getRepaymentInterest();
				if(!StringUtils.isEmpty(temp.getField4())){
					firstInterest = new BigDecimal(temp.getField4());
				}
				//当期计息期间的实际天数
				int days = DateUtils.daysBetween(temp.getInterestStart(), temp.getInterestEnd())+1;
				//剩余天数
				int lastDays = DateUtils.daysBetween(DateUtils.Date2String(entity.getActualTransTime()),temp.getInterestEnd());
				//当期应该减掉的利息
				BigDecimal tempInterest = entity.getPaidCapital().multiply(new BigDecimal(lastDays)).multiply(firstInterest).divide(mainResult.getAmount().multiply(new BigDecimal(days)), 32, BigDecimal.ROUND_HALF_UP);
				//应还利息
				BigDecimal repaymentInterest = temp.getRepaymentInterest().subtract(tempInterest);
				//当期已还本金
				BigDecimal paidCapital=new BigDecimal(0);
				//当期应还本金
				BigDecimal repaymentCapital_=new BigDecimal(0);
				
				if(temp.getPaidCapital()!=null){
					paidCapital=temp.getPaidCapital();
				}
				if(temp.getRepaymentCapital()!=null)repaymentCapital_=temp.getRepaymentCapital();
				temp.setRepaymentInterest(repaymentInterest);
				//本金大于0并且应还本金小于(已还本金+部分还本)
				if(entity.getPaidCapital().compareTo(new BigDecimal(0)) == 1
						&&repaymentCapital_.compareTo(paidCapital.add(entity.getPaidCapital()))==-1){
						temp.setRepaymentCapital(entity.getPaidCapital().add(temp.getRepaymentCapital()));
						temp.setRepaymentCapitalTime(entity.getActualTransTime());
				}
				temp.setRepaymentTotal(temp.getRepaymentCapital().add(temp.getRepaymentInterest()));
				payPlanList.add(temp);
			}else{
				if(lastCapital.subtract(entity.getPaidCapital()).compareTo(new BigDecimal(0)) == 1){
					temp.setRepaymentTotal(temp.getRepaymentTotal().multiply(rate));
					temp.setRepaymentCapital(temp.getRepaymentCapital().multiply(rate));
					temp.setRepaymentInterest(temp.getRepaymentInterest().multiply(rate));
					payPlanList.add(temp);
				}
			}
		}
		ret.put("transMain", mainResult);
		ret.put("payPlanList", payPlanList);
		ret.put("prePay", entity);
		return ret;
	}

	/**
	 * 执行部分还本
	 * @see com.lhhs.loan.service.RepaymentTransService#repaymentCapitalHandler(com.lhhs.loan.dao.domain.vo.PayRecordVo)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> repaymentCapitalHandler(PayRecordVo entity) {
		Map<String,Object> ret = new HashMap<String,Object>();
		
		//部分还本预期计算
		Map<String,Object> map = this.payCapitalCalculation(entity);
		PayRecordVo prePay = (PayRecordVo) map.get("prePay");
		List<LoanPayPlan> payPlanList = (List<LoanPayPlan>) map.get("payPlanList");
		if(prePay.getOverdueDays() != null && prePay.getOverdueDays().intValue() == 1){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg,"产生了逾期，无法提前部分还本");
			return ret;
		}
		if(prePay.getRepaymentCapital().compareTo(entity.getPaidCapital()) == -1){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg,"还款本金不能多于剩余本金");
			return ret;
		}
		//查询未还还款计划
		LoanPayPlan planQuery = new LoanPayPlan();
		planQuery.setTransMainId(entity.getTransMainId());
		planQuery.setStatus(SystemConst.Status.STATUS03);
		List<LoanPayPlan> palnResult = loanPayPlanMapper.queryList(planQuery);
		//更新还款计划
		int editNum = 0;
		for(LoanPayPlan temp : palnResult){
			temp.setStatus(SystemConst.Status.STATUS99);
			temp.setLastModifyTime(new Date());
			temp.setLastUser(entity.getLastUser());
			editNum += loanPayPlanMapper.updateByPrimaryKeySelective(temp);
		}
		//插入新的还款计划
		int inNum = 0;
		//当前待还款计划记录
		LoanPayPlan plan = null;
		for(int i=0;i<payPlanList.size();i++){
			LoanPayPlan temp = payPlanList.get(i);
			temp.setId(null);
			temp.setLastModifyTime(new Date());
			temp.setLastUser(entity.getLastUser());
			inNum += loanPayPlanMapper.insertSelective(temp);
			if(i == 0){
				plan = temp;
			}
		}
		if(plan.getId() == null){
			logger.debug("插入新的还款计划失败,planId未生成");
			throw new RuntimeException("还款计划修改失败");
		}
		//还款数据：
		LoanPayRecord payObj = new LoanPayRecord();
		payObj.setPlanId(plan.getId());//还款计划ID
		payObj.setTransMainId(entity.getTransMainId());
		payObj.setPeriod(plan.getPeriod());//当前期数
		payObj.setActualTransTime(entity.getActualTransTime());//实际还款时间
		payObj.setPaidCapital(entity.getPaidCapital());//实还本金
		payObj.setPaidTotal(entity.getPaidCapital());//页面输入还款总额
		payObj.setTransRemark(entity.getTransRemark());//交易备注
		//执行还款
		String code = accountTransactionService.loanPayTrans(payObj);
		if(editNum == palnResult.size() && inNum == payPlanList.size() && code.equals(SystemConst.SUCCESS)){
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		}else{
			throw new RuntimeException("还款失败");
		}
		return ret;
	}
}

