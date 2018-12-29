/**
 * Project Name:loan-service
 * File Name:CustomerManagerServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月28日上午11:45:24
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.GetUniqueNoUtil;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInOutInfoMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanCapitalInfoMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.LoanPayPlanCompanyMapper;
import com.lhhs.loan.dao.LoanPayRecordCompanyMapper;
import com.lhhs.loan.dao.LoanTransMainMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CustomerManagerService;
import com.lhhs.loan.service.PaymanyTransService;
import com.lhhs.loan.service.account.AccountTransactionService;

/**
 * ClassName:CustomerManagerServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年7月28日 上午11:45:24 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
@Service("paymanyTransService")
public class PaymanyTransServiceImpl implements PaymanyTransService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerManagerServiceImpl.class);
	@Autowired
	private LoanPayPlanCompanyMapper loanPayPlanCompanyMapper;
	@Autowired
	private LoanTransMainMapper loanTransMainMapper;
	@Autowired
	private AccountTransactionService accountTransactionService;
	@Autowired
	private LoanPayRecordCompanyMapper loanPayRecordCompanyMapper;
	@Autowired
	private LoanCustomerInfoMapper loanCustomerInfoMapper;
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	@Autowired
	private LoanAccountInOutInfoMapper loanAccountInOutInfoMapper;
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	@Autowired
	private LoanCapitalInfoMapper loanCapitalInfoMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> executePayService(LoanPayRecordCompanyVo payEntity) {
		try {
			boolean flag=accountInOutInfoinsertData(payEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 校验页面传过来的参数PayFlag:0 当期还款 1:全部还款
		if (payEntity == null|| payEntity.getActualTransTime() == null || StringUtils.isEmpty(payEntity.getTransMainId())
				|| payEntity.getPlanId() == null || payEntity.getPaidTotal() == null
				|| (payEntity.getPaidTotal().compareTo(new BigDecimal(0))) <= 0
				|| StringUtils.isEmpty(payEntity.getPayFlag()) || !payEntity.getPayFlag().equals("0")
				) {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u4ed8\u6b3e\u53c2\u6570\u6709\u8bef");
			return resultMap;
		}
		// 获取对应LoanPayPlanCompany和LoanTransMain对象
		LoanPayPlanCompany payPlanCompany = loanPayPlanCompanyMapper.selectByPrimaryKey(payEntity.getPlanId().toString());
		LoanTransMain transMian = loanTransMainMapper.selectByPrimaryKey(payEntity.getTransMainId());
		// 判断付款对象是否都存在
		if (payPlanCompany == null || transMian == null) {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u4ed8\u6b3e\u53c2\u6570\u6709\u8bef");
			return resultMap;
		}
		LoanPayRecordCompany payRecordCompany = new LoanPayRecordCompany();
		payRecordCompany.setPlanId(payPlanCompany.getId());//付款计划ID
		payRecordCompany.setTransMainId(payPlanCompany.getTransMainId());//TransMainId
		payRecordCompany.setPeriod(payPlanCompany.getPeriod());// 当前付款期数
		payRecordCompany.setActualTransTime(payEntity.getActualTransTime());//实际还款时间
		payRecordCompany.setPaidCompensate(payEntity.getPaidCompensate());//违约金
		payRecordCompany.setPaidTotal(payEntity.getPaidTotal());
		payRecordCompany.setPaidCapital(new BigDecimal(0.00));
		payRecordCompany.setOrderNo(payPlanCompany.getOrderNo());//报单
		payRecordCompany.setCustomerId(payPlanCompany.getCustomerId());//出借款人
		payRecordCompany.setCustomerName(payPlanCompany.getCustomerName());//出借款人姓名
		payRecordCompany.setAccountId(payPlanCompany.getAccountId());//出借人账户
		payRecordCompany.setRepaymentCapital(payPlanCompany.getRepaymentCapital());
		payRecordCompany.setRepaymentCapitalTime(payPlanCompany.getRepaymentCapitalTime());
		payRecordCompany.setRepaymentInterest(payPlanCompany.getRepaymentInterest());
		payRecordCompany.setRepaymentInterestTime(payPlanCompany.getRepaymentInterestTime());
		payRecordCompany.setPaidOverdue(payEntity.getPaidOverdue());
		
		//应付本时间
		String repaymentCapitalTime = DateUtils.formatDate(payPlanCompany.getRepaymentCapitalTime());
		//应付息时间
		String repaymentInterestTime = DateUtils.formatDate(payPlanCompany.getRepaymentInterestTime());
		//实际还款时间(页面所选时间)
		String payDate = DateUtils.formatDate(payEntity.getActualTransTime());
		//同时付息付本是判断 是否到了付本日 未到付本日的话 终止执行
		if(payEntity.getIsCapita().equals("1") && payEntity.getIsInterest().equals("1") //同时付息付本
				&& !StringUtils.isEmpty(repaymentInterestTime) && !StringUtils.isEmpty(repaymentCapitalTime)
				&& DateUtils.daysBetween(repaymentInterestTime,repaymentCapitalTime)!=0){
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "付息日和付本日不是同一天，请先执行付息，再执行付本或提前付款");
			return resultMap;
		}
		//判断是否逾期，计算逾期利息，并且更新付款计划表
		this.isOverdue(payEntity, true);
		//组装付款
		if (payEntity.getIsInterest().equals("1") && payEntity.getIsCapita().equals("0")) {//付息
				payRecordCompany.setPaidInterest(payEntity.getPaidTotal().subtract(payEntity.getPaidOverdue()).subtract(payEntity.getPaidCompensate()));
		} else if (payEntity.getIsInterest().equals("0") && payEntity.getIsCapita().equals("1")) {//付本
			    //未到付本日只能全部付本
			    if (StringUtils.isEmpty(repaymentCapitalTime)||DateUtils.daysBetween(repaymentCapitalTime,payDate)<0) {// 判断是否是提前还本
				logger.error("未到付本时间，如要提前付本，请执行全部付款");
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "还未到付本时间，如要提前付本，请执行全部付款");
				return resultMap;
			} else if (DateUtils.daysBetween(repaymentCapitalTime,payDate) > 0) {//逾期
				payRecordCompany.setPaidCapital(payEntity.getPaidTotal().subtract(payEntity.getPaidOverdue()).subtract(payEntity.getPaidCompensate()));
			} else if (DateUtils.daysBetween(repaymentCapitalTime,payDate) == 0) {//正常
				payRecordCompany.setPaidCapital(payEntity.getPaidTotal().subtract(payEntity.getPaidOverdue()).subtract(payEntity.getPaidCompensate()));
			}
		} else {//付息付本
			if (DateUtils.daysBetween(repaymentCapitalTime,payDate)<0) {//提前付本
				logger.error("未到付本时间，如要提前付本，请执行全部付款");
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "还未到付本时间，如要提前付本，请执行全部付款或只付息");
				return resultMap;
			}
			//计算剩余还款金额
			//页面输入收款金额减去页面输入应收贴息金额
			BigDecimal amount = payEntity.getPaidTotal().subtract(payEntity.getPaidOverdue()).subtract(payEntity.getPaidCompensate());;
			//剩余未付利息=应付利息-已付部门或全部部利息
			BigDecimal lastInterest = payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest());
			//对比 剩余未还利息和页面输入收款金额
			if(lastInterest.compareTo(amount)>=0){//优先付剩余未付利息
				payRecordCompany.setPaidInterest(amount);
			}else{
				//如果剩余未付利息小于 
				BigDecimal amount1 = amount.subtract(lastInterest);
				//计算出剩余未付本金
				BigDecimal lastCapital = payPlanCompany.getRepaymentCapital().subtract(payPlanCompany.getPaidCapital());
				if(amount1.compareTo(lastCapital)==1){
					//已还本金
					payRecordCompany.setPaidCapital(amount1);
					//实际应还息=应还利息-已还利息+还款多余的钱
					payRecordCompany.setPaidInterest(lastInterest.add(amount1.subtract(lastCapital)));
				}else{
					//实际应付息=应还利息-已还利息
					payRecordCompany.setPaidInterest(lastInterest);
					payRecordCompany.setPaidCapital(amount1);
				}
			}
		}
	    String retCode=accountTransactionService.loanPayCompanyTrans(payRecordCompany);
		resultMap.put(SystemConst.retCode,retCode);
		return resultMap;
	}

	
	/**
	 * 执行全部付款
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.PaymanyTransService#executePayAll(com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Map<String, Object> executePayAllService(LoanPayRecordCompanyVo payEntity) {
		try {
			boolean flag=accountInOutInfoinsertData(payEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Map<String, Object> result = new HashMap<String,Object>();
		if(payEntity == null || StringUtils.isEmpty(payEntity.getPayFlag()) || !payEntity.getPayFlag().equals("1") 
			|| payEntity.getActualTransTime() == null || StringUtils.isEmpty(payEntity.getTransMainId())
			|| payEntity.getPaidTotal() == null || (payEntity.getPaidTotal().compareTo(new BigDecimal(0)))<=0){
			logger.error("付款数据不正确");
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "付款数据不正确");
			return result;
		}
		//根据传入参数重新生成还款计划和
		Long planId = this.createPayPlanRecord(payEntity);
		if(planId == null || planId.compareTo(0L) == 0 || planId.compareTo(-1L) == 0){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			if(planId != null && planId.compareTo(-1L) == 0){
				result.put(SystemConst.retMsg, "当前所选付款时间产生了逾期，无法提前付款!");
			}else{
				result.put(SystemConst.retMsg, "还款失败");
			}
			return result;
		}
		//查询当期还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(payEntity.getTransMainId());
		//查询新的还款计划
		LoanPayPlanCompany payPlanCompany = loanPayPlanCompanyMapper.selectByPrimaryKey(planId.toString());
		if(transMain == null || payPlanCompany == null){
			logger.error("付款数据不正确");
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "付款数据不正确");
			return result;
		}
		
		
		if(payEntity.getPaidTotal().compareTo((payEntity.getPaidOverdue().add(payEntity.getPaidCompensate()).add(payEntity.getPaidInterest()))) <= 0){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "还款总额必须大于利息、罚息与违约金之和");
			return result;
		}
		
		//当利息为负值时，输入利息必须大于等于应收利息
		if(payEntity.getPaidInterest().compareTo(payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest()))==-1){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "利息必须大于等于应收利息");
			return result;
		}
		//付款实体类对象：
		LoanPayRecordCompany recordCompany = new LoanPayRecordCompany();
		recordCompany.setPlanId(payPlanCompany.getId());//付款计划ID
		recordCompany.setTransMainId(payPlanCompany.getTransMainId());
		recordCompany.setPeriod(payPlanCompany.getPeriod());//当前期数
		
		recordCompany.setActualTransTime(payEntity.getActualTransTime());//实际付款时间
		recordCompany.setPaidTotal(payEntity.getPaidTotal());
		recordCompany.setPaidCapital(payPlanCompany.getRepaymentCapital());//实付本金
		recordCompany.setPaidInterest(payEntity.getPaidInterest());//实付利息(頁面取值)
		recordCompany.setPaidOverdue(payEntity.getPaidOverdue());//实付逾期罚息(頁面取值)
		recordCompany.setPaidCompensate(payEntity.getPaidCompensate());//实付赔偿违约金(頁面取值)
		String retCode=accountTransactionService.loanPayCompanyTrans(recordCompany);
		result.put(SystemConst.retCode,retCode);
		return result;
	}


	
	
	/**
	 * 待付款-执行放款 生成提现申请数据
	 * @param payEntity
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	private boolean accountInOutInfoinsertData(LoanPayRecordCompanyVo payEntity) {
		// 获取对应LoanPayPlanCompany和LoanTransMain对象
		LoanPayPlanCompany payPlanCompany = loanPayPlanCompanyMapper.selectByPrimaryKey(payEntity.getPlanId().toString());
		LoanTransMain transMian = loanTransMainMapper.selectByPrimaryKey(payEntity.getTransMainId());
		LoanCustomerInfo loanCustomerInfo=loanCustomerInfoMapper.selectByCustomerId(transMian.getBorrowerId());//借款人
		// 判断付款对象是否都存在
		if (payPlanCompany == null || transMian == null) {
			return false;
		}
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		LoanAccountInOutInfo loanAccountInOutInfo=new LoanAccountInOutInfo();
		loanAccountInOutInfo.setCreateUser(user.getStaffName());
		loanAccountInOutInfo.setUnionId(user.getUnionId());
		loanAccountInOutInfo.setCompanyId(user.getCompanyId());
//		loanAccountInOutInfo.setTransNo(SystemConst.TransType.TYPEID1006 + GetUniqueNoUtil.getOrderNo());
		loanAccountInOutInfo.setStatus(SystemConst.Status.STATUS03);
		loanAccountInOutInfo.setCreateTime(new Date());
		loanAccountInOutInfo.setTransType(SystemConst.TransType.TYPEID1006);
		loanAccountInOutInfo.setCustomerId(payPlanCompany.getCustomerId());
		loanAccountInOutInfo.setCustomerName(payPlanCompany.getCustomerName());
		loanAccountInOutInfo.setAccountId(payPlanCompany.getAccountId());
		loanAccountInOutInfo.setOrderNo(transMian.getBusinessId());
//		loanAccountInOutInfo.setActualPayTime(new Date());
		LoanAccountInfo  account =loanAccountInfoMapper.selectByOwnerId(payPlanCompany.getCustomerId());
		if (null!=account) {
			loanAccountInOutInfo.setMobile(account.getMobile());
			loanAccountInOutInfo.setCertificateNo(account.getCertificateNo());
			loanAccountInOutInfo.setAccountHolder(account.getAccountHolder());
		}
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("orderNo", transMian.getBusinessId());
		LoanCapitalInfo capitalInfo=loanCapitalInfoMapper.selectLoanCapitalInfoByBankCardIdAndOrderno(map);
		if(null!=capitalInfo){
			loanAccountInOutInfo.setCustomerType(capitalInfo.getCustomerType());
			loanAccountInOutInfo.setCustomerNature(capitalInfo.getCustomerNature());
			loanAccountInOutInfo.setBankCardNo(capitalInfo.getBankcardId());
			loanAccountInOutInfo.setBankName(capitalInfo.getBankName());
		}
		
		try {
			    if(!StringUtils.isEmpty(payEntity.getPaidOverdue())&&payEntity.getPaidOverdue().compareTo(BigDecimal.ZERO)==1){//逾期贴息paidOverdue
					if(SystemConst.TransType.TYPEID1001.equals(transMian.getTransType())){
						loanAccountInOutInfo.setField1(SystemConst.SubjectInfo.SUBJECTID_INVEST_OVERDUE_INTEREST_IN);//回款逾期罚息
					}else{
						loanAccountInOutInfo.setField1(SystemConst.SubjectInfo.SUBJECTID_FINANCING_OVERDUE_INTEREST_IN);//理财回款逾期罚息
					}
					loanAccountInOutInfo.setTransNo(SystemConst.TransType.TYPEID1006 + GetUniqueNoUtil.getOrderNo());
					loanAccountInOutInfo.setAmount(payEntity.getPaidOverdue());
					loanAccountInOutInfo.setActualPayTime(new Date());
					loanAccountInOutInfoMapper.insertSelective(loanAccountInOutInfo);
				}
				if(!StringUtils.isEmpty(payEntity.getPaidCompensate())&&payEntity.getPaidCompensate().compareTo(BigDecimal.ZERO)==1){//收款违约赔偿金paidCompensate
					if(SystemConst.TransType.TYPEID1001.equals(transMian.getTransType())){
						loanAccountInOutInfo.setField1(SystemConst.SubjectInfo.SUBJECTID_INVEST_COMPENSATE_IN);//收入违约偿还金
					}else{
						loanAccountInOutInfo.setField1(SystemConst.SubjectInfo.SUBJECTID_FINANCING_COMPENSATE_IN);//理财收入违约偿还金
					}
					loanAccountInOutInfo.setTransNo(SystemConst.TransType.TYPEID1006 + GetUniqueNoUtil.getOrderNo());
					loanAccountInOutInfo.setAmount(payEntity.getPaidCompensate());
					loanAccountInOutInfo.setActualPayTime(new Date());
					loanAccountInOutInfoMapper.insertSelective(loanAccountInOutInfo);
				}
				if("1".equals(payEntity.getIsCapita())&&payPlanCompany.getRepaymentCapital().compareTo(BigDecimal.ZERO)==1){//收款违约赔偿金paidCompensate 执行付本 isCapita
					if(SystemConst.TransType.TYPEID1001.equals(transMian.getTransType())){
						loanAccountInOutInfo.setField1(SystemConst.SubjectInfo.SUBJECTID_INVEST_CAPITAL_IN);//回款本金
					}else{
						loanAccountInOutInfo.setField1(SystemConst.SubjectInfo.SUBJECTID_FINANCING_CAPITAL_IN);//理财回款本金
					}
					loanAccountInOutInfo.setTransNo(SystemConst.TransType.TYPEID1006 + GetUniqueNoUtil.getOrderNo());
					loanAccountInOutInfo.setAmount(payPlanCompany.getRepaymentCapital());
					loanAccountInOutInfo.setActualPayTime(new Date());
					loanAccountInOutInfoMapper.insertSelective(loanAccountInOutInfo);
				}
				if("1".equals(payEntity.getIsInterest())&&payPlanCompany.getRepaymentInterest().compareTo(BigDecimal.ZERO)==1){//收款违约赔偿金paidCompensate 执行付息isInterest
					if(SystemConst.TransType.TYPEID1001.equals(transMian.getTransType())){
						loanAccountInOutInfo.setField1(SystemConst.SubjectInfo.SUBJECTID_INVEST_INTEREST_IN);//回款利息
					}else{
						loanAccountInOutInfo.setField1(SystemConst.SubjectInfo.SUBJECTID_FINANCING_INTEREST_IN);//理财回款利息
					}
					loanAccountInOutInfo.setTransNo(SystemConst.TransType.TYPEID1006 + GetUniqueNoUtil.getOrderNo());
					loanAccountInOutInfo.setAmount(payPlanCompany.getRepaymentInterest());
					loanAccountInOutInfo.setActualPayTime(new Date());
					loanAccountInOutInfoMapper.insertSelective(loanAccountInOutInfo);
				}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
/**
 * 计算逾期利息
 * TODO 简单描述该方法的实现功能（可选）.
 * @see com.lhhs.loan.service.PaymanyTransService#isOverdue(com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo, boolean)
 */
	@Override
	public LoanPayRecordCompany isOverdue(LoanPayRecordCompanyVo payEntity,boolean flag) {
		
		if(payEntity == null || StringUtils.isEmpty(payEntity.getPayFlag()) || !payEntity.getPayFlag().equals("0") 
				|| payEntity.getActualTransTime() == null || StringUtils.isEmpty(payEntity.getTransMainId())
				|| payEntity.getPlanId() == null){
				logger.error("传入数据不正确");
				throw new RuntimeException("传入数据不正确");
			}
		//获取付款记录
		LoanPayPlanCompany payPlanCompany = loanPayPlanCompanyMapper.selectByPrimaryKey(payEntity.getPlanId().toString());
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(payEntity.getTransMainId());
		if (transMain == null || payPlanCompany == null) {
			logger.error("传入数据不正确，付款计划或放款主记录不存在");
			throw new RuntimeException("传入数据不正确,付款计划不存在");
		}
		
		LoanPayRecordCompany payRecord = new LoanPayRecordCompany();
		payRecord.setTransMainId(payEntity.getTransMainId());
		payRecord.setPlanId(payEntity.getPlanId());
		payRecord.setActualTransTime(payEntity.getActualTransTime());
		payRecord.setPaidCompensate(payPlanCompany.getCompensate());//违约金(不提前还款时，违约金为0)
		payRecord.setRepaymentCapital(payPlanCompany.getRepaymentCapital().subtract(payPlanCompany.getPaidCapital()));//剩余本金
		payRecord.setRepaymentInterest(payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest()));//剩余利息
	
		LoanPayRecordCompany queryRecord = new LoanPayRecordCompany();
		queryRecord.setTransMainId(payEntity.getTransMainId());
		queryRecord.setAccountId(payEntity.getAccountId());
		queryRecord = loanPayRecordCompanyMapper.queryPaysum(queryRecord);
		if(queryRecord != null && queryRecord.getPaidAllTotal() != null){
			payRecord.setPaidAllTotal(queryRecord.getPaidAllTotal());
		}
		
		//当前还款时间
		String actualTransTime = DateUtils.formatDate(payEntity.getActualTransTime());

		//应还本时间
		String repaymentCapitalTime = DateUtils.formatDate(payPlanCompany.getRepaymentCapitalTime());
		//应还利息时间
		String repaymentInterestTime = DateUtils.formatDate(payPlanCompany.getRepaymentInterestTime());
		//已付本时间
		String capitalTime = DateUtils.formatDate(payPlanCompany.getCapitalTime());
		//已付息时间
		String interestTime = DateUtils.formatDate(payPlanCompany.getInterestTime());

		if (capitalTime == null && repaymentCapitalTime != null) {
			capitalTime = repaymentCapitalTime;
		}
		if (interestTime == null && repaymentInterestTime != null) {
			interestTime = repaymentInterestTime;
		}
		//默认0没有逾期
		int overFlag=0;
		if(capitalTime!=null && DateUtils.daysBetween(capitalTime,actualTransTime)>0){
			overFlag=1;
		}
		if(interestTime!=null &&  DateUtils.daysBetween(interestTime,actualTransTime)>0){
			overFlag=1;
		}
		//无逾期场景
		if (overFlag<1) {
			payRecord.setRepaymentTotal((payPlanCompany.getRepaymentCapital().subtract(payPlanCompany.getPaidCapital()))
					.add(payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest())));
			payRecord.setOverdueInterestTotal(new BigDecimal(0.00));
			return payRecord;
		}
		
		/**
		if ((capitalTime!=null && DateUtils.daysBetween(capitalTime,actualTransTime)<0 
				&& payPlanCompany.getRepaymentCapital().compareTo(new BigDecimal(0))==1)
				&&(interestTime!=null &&  DateUtils.daysBetween(interestTime,actualTransTime)<0 )) {
			payRecord.setRepaymentTotal((payPlanCompany.getRepaymentCapital().subtract(payPlanCompany.getPaidCapital()))
					.add(payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest())));
			payRecord.setOverdueInterestTotal(new BigDecimal(0.00));
			return payRecord;
		}
		**/
		//已计算本金罚息
		BigDecimal paidOverdueCapital=new BigDecimal(0);
		if(payPlanCompany.getOverdueCapital()!=null){
			paidOverdueCapital=payPlanCompany.getOverdueCapital();
		}
		//本金逾期罚息
		BigDecimal overdueCapital = new BigDecimal(0);
		//本金逾期天数
		int daysCapital = 0;
		//利息逾期罚息
		BigDecimal overdueInterest = new BigDecimal(0);
		//利息逾期天数
		int daysInterest = 0;
		//已还本金总额
		BigDecimal paidCapitalTotal = new BigDecimal(0);
		if(queryRecord!=null&&queryRecord.getPaidCapital()!=null){
			paidCapitalTotal=queryRecord.getPaidCapital();
		}
		//查询还款计划汇总应还本金和利息
		LoanPayPlanCompany plan_qury=new LoanPayPlanCompany();
		plan_qury.setTransMainId(payPlanCompany.getTransMainId());
		plan_qury.setAccountId(payPlanCompany.getAccountId());
		LoanPayPlanCompany plan_total=loanPayPlanCompanyMapper.queryPayPlansum(plan_qury);
		
		//未付本金 
		BigDecimal lastCapital = plan_total.getRepaymentCapital().subtract(paidCapitalTotal);
		//未付利息
		BigDecimal lastInterest = payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest());
		if (lastCapital.compareTo(new BigDecimal(0)) == 1) {
			if(capitalTime!=null){
				daysCapital = DateUtils.daysBetween(capitalTime, actualTransTime);
			}
			if(daysCapital<0){
				daysCapital=0;
			}
			if(interestTime!=null&&lastInterest.compareTo(new BigDecimal(0)) == 1){
				daysInterest = DateUtils.daysBetween(interestTime, actualTransTime);
			}
			if(daysCapital>0&&daysInterest>0&&paidOverdueCapital.compareTo(new BigDecimal(0))!=1){
				daysInterest = DateUtils.daysBetween(interestTime, capitalTime);
			}else if(daysCapital>0&&daysInterest>0&&paidOverdueCapital.compareTo(new BigDecimal(0))==1){
				int days=DateUtils.daysBetween(interestTime,capitalTime);
				if(days<0){
					daysCapital = DateUtils.daysBetween(interestTime, actualTransTime);
				}
			}
			// 利息罚息=剩余利息×罚息利率×(还款日期-最后一次还息日期)的天数
			overdueInterest = lastCapital.multiply(new BigDecimal(transMain.getInvestOverRate()))
								.multiply(new BigDecimal(daysInterest));
			//本金罚息=剩余本金×罚息利率×(还款日期-最后一次还本日期)的天数
			overdueCapital = lastCapital.multiply(new BigDecimal(transMain.getInvestOverRate()))
					.multiply(new BigDecimal(daysCapital));
			
		}
		/**
		//未付利息
		BigDecimal lastInterest = payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest());
		if (lastInterest.compareTo(new BigDecimal(0)) == 1) {
			daysInterest = DateUtils.daysBetween(interestTime, actualTransTime);
			// 利息罚息=剩余利息×罚息利率×(还款日期-最后一次还息日期)的天数
			overdueInterest = lastInterest.multiply(new BigDecimal(transMain.getInvestOverRate()))
					.multiply(new BigDecimal(daysInterest));
		}
		**/
		BigDecimal repaymentCapital=payPlanCompany.getRepaymentCapital().subtract(payPlanCompany.getPaidCapital());
		if(repaymentCapital.compareTo(new BigDecimal(0))==-1){
			repaymentCapital=new BigDecimal(0);
		}
		BigDecimal repaymentInterest=payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest());
		if(repaymentInterest.compareTo(new BigDecimal(0))==-1){
			repaymentInterest=new BigDecimal(0);
		}
		//应还本金
		payRecord.setRepaymentCapital(repaymentCapital);
		//应还利息
		payRecord.setRepaymentInterest(repaymentInterest);
		//应付金额
		payRecord.setRepaymentTotal((payPlanCompany.getRepaymentCapital().subtract(payPlanCompany.getPaidCapital()))
				.add(payPlanCompany.getRepaymentInterest().subtract(payPlanCompany.getPaidInterest()))
				.add(payPlanCompany.getCompensate()).add(overdueCapital).add(overdueInterest));
		//应付罚金=利息罚息+本金罚息
		payRecord.setOverdueInterestTotal(overdueCapital.add(overdueInterest));
		payRecord.setOverdueDays((daysCapital >= daysInterest) ? daysCapital : daysInterest);
		
		//更新付款计划表逾期罚息
		if (flag) {
			//逾期天数(取本金和利息中逾期的最大值)
			payPlanCompany.setOverdueDays((daysCapital >= daysInterest) ? daysCapital : daysInterest);
			//逾期标示
			if(payPlanCompany.getOverdueDays() > 0){
				payPlanCompany.setTransType(SystemConst.Status.STATUS89);
			}else{
				payPlanCompany.setTransType(SystemConst.Status.STATUS03);
			}
			// 逾期罚息总额=原来罚息总额+本次逾期罚息总额
			payPlanCompany.setOverdueInterestTotal(
					payPlanCompany.getOverdueInterestTotal().add(overdueCapital).add(overdueInterest));
			// 逾期本金罚息=原来本金罚息总额+本次本金罚息
			payPlanCompany.setOverdueCapital(payPlanCompany.getOverdueCapital().add(overdueCapital));
			// 逾期利息罚息=原来利息罚息总额+本次利息罚息
			payPlanCompany.setOverdueInterest(payPlanCompany.getOverdueInterest().add(overdueInterest));
			
			//最后操作时间
			payPlanCompany.setLastModifyTime(new Date());
			int count= loanPayPlanCompanyMapper.updateByPrimaryKeySelective(payPlanCompany);
			if (count <= 0) {
				throw new RuntimeException("逾期贴息计算后付款计划表更新失败");
			}
		}
		return payRecord;
	}

    /**
     * 提前付款违约金
     * TODO 简单描述该方法的实现功能（可选）.
     * @see com.lhhs.loan.service.PaymanyTransService#fineCompute(java.lang.String, java.lang.String, java.lang.String)
     */
	@Override
	public LoanPayRecordCompany fineCompute(LoanPayRecordCompanyVo payEntity) {
		LoanPayRecordCompany recordCompany= new LoanPayRecordCompany();
		if(payEntity == null || StringUtils.isEmpty(payEntity.getPayFlag()) || !payEntity.getPayFlag().equals("1") 
				|| payEntity.getActualTransTime() == null || StringUtils.isEmpty(payEntity.getTransMainId())){
				logger.error("参数传入错误不正确");
				throw new RuntimeException("传入数据不正确");
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("transMainId",payEntity.getTransMainId());
		param.put("accountId",payEntity.getAccountId());
		param.put("status",SystemConst.Status.STATUS03);
		//查询还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(payEntity.getTransMainId());
		List<LoanPayPlanCompany> payList = loanPayPlanCompanyMapper.queryAll(param);
		if(transMain == null || payList == null || payList.size() == 0){
			logger.error("付款数据不正确");
			throw new RuntimeException("付款数据不正确");
		}
		//生成一个 付款计划实体并赋值
		LoanPayPlanCompany payPlan = new LoanPayPlanCompany();
		BeanUtils.copyProperties(payList.get(0), payPlan);
		
		/*BigDecimal tempInterest = payPlan.getRepaymentInterest().subtract(payPlan.getPaidInterest());
		if((tempInterest.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(payPlan.getRepaymentInterestTime(),payEntity.getActualTransTime()) > 0)
				|| (payPlan.getRepaymentCapitalTime() != null && DateUtils.daysBetween(payPlan.getRepaymentCapitalTime(),payEntity.getActualTransTime()) > 0)){
				logger.error("当前所选还款日产生了逾期，无法提前付款");
				recordCompany.setOverdueDays(1);//到页面提示逾期不能付款
				throw new RuntimeException("当前所选还款日产生了逾期，无法提前付款");
		}*/
		
		int compensateDays = DateUtils.daysBetween(payEntity.getActualTransTime(),transMain.getOverTime());
		
		if(DateUtils.daysBetween(DateUtils.Date2String(payEntity.getActualTransTime()),payPlan.getInterestStart()) > 0){
			compensateDays = DateUtils.daysBetween(payPlan.getInterestStart(),DateUtils.Date2String(transMain.getOverTime()));
		}
		if(compensateDays<0){
			compensateDays = 0;
		}
		//当前实际天数
		int days = DateUtils.daysBetween(payPlan.getInterestStart(), payPlan.getInterestEnd())+1;
		//实际使用天数
		int useDays = DateUtils.daysBetween(payPlan.getInterestStart(), DateUtils.Date2String(payEntity.getActualTransTime()))+1;
		if(useDays<0){useDays = 0;}
		//返回带有违约金的对象
		recordCompany.setTransMainId(payEntity.getTransMainId());
		recordCompany.setActualTransTime(payEntity.getActualTransTime());
		recordCompany.setCompensateDays(compensateDays);
		recordCompany.setInterestStart(payPlan.getInterestStart());
		recordCompany.setInterestEnd(payPlan.getInterestEnd());
		recordCompany.setUseDays(useDays);
		
		//次投资人已经付本金
		LoanPayRecordCompany queryRecord = new LoanPayRecordCompany();
		queryRecord.setTransMainId(payEntity.getTransMainId());
		queryRecord.setAccountId(payEntity.getAccountId());
		queryRecord = loanPayRecordCompanyMapper.queryPaysum(queryRecord);
		BigDecimal paidCapitalTotal = new BigDecimal(0);
		if(queryRecord != null && queryRecord.getPaidCapital() != null){
			paidCapitalTotal = queryRecord.getPaidCapital();
		}
		LoanPayPlanCompany trans=new LoanPayPlanCompany();
		trans.setTransMainId(payEntity.getTransMainId());
		trans.setAccountId(payEntity.getAccountId());
		trans=loanPayPlanCompanyMapper.queryPayPlansum(trans);
		BigDecimal capitalTotal = new BigDecimal(0);
		if(trans != null && trans.getRepaymentCapital()!= null){
			capitalTotal = trans.getRepaymentCapital();
		}
		//剩余未付本金=借款总额-已还本金
		BigDecimal lastCapital = capitalTotal.subtract(paidCapitalTotal);
		recordCompany.setRepaymentCapital(lastCapital);
		recordCompany.setOverdueInterestTotal(payPlan.getOverdueInterestTotal());
		//应还利息=本期已还利息-当期利息/当期实际天数*实际使用天数
		//应还利息=当期利息/当期实际天数*实际使用天数
		BigDecimal interest = (payPlan.getRepaymentInterest().divide(new BigDecimal(days),32,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(useDays))).subtract(payPlan.getPaidInterest());
		recordCompany.setRepaymentInterest(interest);
		//还款违约金=剩余本金×提前还款违约率
		BigDecimal compensate = lastCapital.multiply(new BigDecimal(transMain.getInvestBreachRate())).multiply(new BigDecimal(compensateDays));
		recordCompany.setPaidCompensate(compensate);
		//应还总额=剩余本金+应还利息+还款违约金
		recordCompany.setRepaymentTotal(lastCapital.add(interest).add(compensate));
		//剩余本金
		recordCompany.setRepaymentCapital(lastCapital);
		//剩余利息
		recordCompany.setRepaymentInterest(interest);
		
		return recordCompany;
	}
   /**
    * 重新生成付款计划 作废之前计划
    * TODO 简单描述该方法的实现功能（可选）.
    * @see com.lhhs.loan.service.PaymanyTransService#createPayPlanRecord(com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo)
    */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long createPayPlanRecord(LoanPayRecordCompanyVo payEntity) {
		//组装
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("transMainId",payEntity.getTransMainId());
		param.put("accountId",payEntity.getAccountId());
		param.put("status",SystemConst.Status.STATUS03);
		//查询还款数据
		LoanTransMain transMain = loanTransMainMapper.selectByPrimaryKey(payEntity.getTransMainId());
		List<LoanPayPlanCompany> payList = loanPayPlanCompanyMapper.queryAll(param);
		
		if(transMain == null || payList == null || payList.size() == 0){
			logger.error("付款数据不正确");
			return 0L;
		}
		LoanPayPlanCompany plan = new LoanPayPlanCompany();
		BeanUtils.copyProperties(payList.get(0), plan);
		
		BigDecimal tempInt = plan.getRepaymentInterest().subtract(plan.getPaidInterest());
		if((tempInt.compareTo(new BigDecimal(0))==1 && DateUtils.daysBetween(plan.getRepaymentInterestTime(),payEntity.getActualTransTime()) > 0)
			|| (plan.getRepaymentCapitalTime() != null && DateUtils.daysBetween(plan.getRepaymentCapitalTime(),payEntity.getActualTransTime()) > 0)){
			logger.error("当前所选还款日产生了逾期，无法提前还款");
			return -1L;
		}
		
		//正常应该使用实际天数
		int days = DateUtils.daysBetween(plan.getInterestStart(), plan.getInterestEnd());
		//事实上确实使用使用天数
		int useDays = DateUtils.daysBetween(plan.getInterestStart(), DateUtils.Date2String(payEntity.getActualTransTime()));
		if(useDays<0){
			useDays = 0;
		}
		plan.setId(null);
		//剩余的未付本金:应付款总额-已付本金
		//已还本金=sum(还款记录中已还本金之和)
		//查询总和
		LoanPayRecordCompany queryRecord = new LoanPayRecordCompany();
		queryRecord.setTransMainId(payEntity.getTransMainId());
		queryRecord.setAccountId(payEntity.getAccountId());
		queryRecord = loanPayRecordCompanyMapper.queryPaysum(queryRecord);
		BigDecimal paidCapitalTotal = new BigDecimal(0);
		if(queryRecord != null && queryRecord.getPaidCapital() != null){
			paidCapitalTotal = queryRecord.getPaidCapital();
		}
		//该投资人投资总额
		LoanPayPlanCompany trans=new LoanPayPlanCompany();
		trans.setTransMainId(payEntity.getTransMainId());
		trans.setAccountId(payEntity.getAccountId());
		trans=loanPayPlanCompanyMapper.queryPayPlansum(trans);
		BigDecimal capitalTotal = new BigDecimal(0);
		if(trans != null && trans.getRepaymentCapital()!= null){
			capitalTotal = trans.getRepaymentCapital();
		}
		
		BigDecimal lastCapital =capitalTotal.subtract(paidCapitalTotal);
		plan.setRepaymentCapital(lastCapital);//应还本金
		//应还利息=本期已还利息-当期利息/当期实际天数*实际使用天数
		BigDecimal interest = plan.getPaidInterest().subtract(plan.getRepaymentInterest().divide(new BigDecimal(days),32,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(useDays))).subtract(plan.getPaidInterest());;
		plan.setRepaymentInterest(interest);
		//应还总额=剩余本金+应还利息
		plan.setRepaymentTotal(lastCapital.add(interest));
		plan.setRepaymentCapitalTime(payEntity.getActualTransTime());//应还本时间
		plan.setRepaymentInterestTime(payEntity.getActualTransTime());//应还息时间
		plan.setOverdueDays(0);
		plan.setOverdueInterestTotal(new BigDecimal(0));
		plan.setOverdueCapital(new BigDecimal(0));
		plan.setOverdueInterest(new BigDecimal(0));
		//还款违约金=剩余本金×提前还款违约率
		plan.setCompensate(lastCapital.multiply(new BigDecimal(transMain.getBreachRate())));
		plan.setTransType(SystemConst.Status.STATUS88);
		plan.setStatus(SystemConst.Status.STATUS03);
		plan.setInterestEnd(DateUtils.Time2String(payEntity.getActualTransTime()));
		plan.setCreateUser(payEntity.getCreateUser());
		plan.setCreateTime(new Date());
		plan.setLastModifyTime(new Date());
		plan.setLastUser(payEntity.getLastUser());
		int countOne = loanPayPlanCompanyMapper.insertSelective(plan);
		int countTwo = 0;
		if(countOne>=1){
			for(LoanPayPlanCompany temp : payList){
				temp.setStatus(SystemConst.Status.STATUS99);//无效
				int count = loanPayPlanCompanyMapper.updateByPrimaryKeySelective(temp);
				if(count>=1){
					countTwo++;
				}
			}
		}
		if(countTwo == payList.size() && countOne == 1){
			return plan.getId();
		}else{
			return 0L;
		}

	}

	/**
	 * 待付款计划-提前部分还款 重新生成付款计划
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.PaymanyTransService#getPayPortion(com.lhhs.loan.dao.domain.LoanPayPlanCompany)
	 */
	@Override
	public Map<String, Object> getPayPortion(LoanPayRecordCompanyVo payEntity) {
	    Map<String, Object> result = new HashMap<String,Object>();
		if(payEntity == null ||payEntity.getActualTransTime() == null
		   || StringUtils.isEmpty(payEntity.getTransMainId())){
			logger.error("部分还本主要数据不能为空");
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "部分还本主要数据不能为空");
			return result;
		}
	    Map<String, Object> paramsMap=new HashMap<String,Object>();
	    paramsMap.put("status",SystemConst.Status.STATUS03);//有效状态
	    paramsMap.put("transMainId",payEntity.getTransMainId());
	    paramsMap.put("customerId",payEntity.getCustomerId());
	    paramsMap.put("executePayPortion","executePayPortion");
		List<LoanPayPlanCompany> list=loanPayPlanCompanyMapper.querypayplanCompany(paramsMap);
		
		if(list==null){
			logger.error("待付款数据为空");
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "待付款数据为空");
			return result;
		}
		BigDecimal advanceAmount=payEntity.getAdvanceAmount();//提前部分还本金额
		BigDecimal investAmount=payEntity.getInvestAmount();//剩余本金
		BigDecimal b1 = advanceAmount.divide(investAmount,4,BigDecimal.ROUND_HALF_UP);
		for(int i=0;i<list.size();i++){
			LoanPayPlanCompany payPlanCompany=list.get(i);
			BigDecimal interest=payPlanCompany.getRepaymentInterest();//应还利息
			if(i==0){//当前期数重新计算利息
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date actualTransTime = payEntity.getActualTransTime();//还款时间
					Date startTime = sdf1.parse(payPlanCompany.getInterestStart());//本期计息开始日期
					Date endTime = sdf1.parse(payPlanCompany.getInterestEnd());//本期计息结束日期
					payEntity.setInterestStart(payPlanCompany.getInterestStart());
					if(payPlanCompany.getCapitalTime()!=null){
						payEntity.setInterestStart(DateUtils.Date2String(payPlanCompany.getCapitalTime()));
					}
					payEntity.setInterestEnd(payPlanCompany.getInterestEnd());
					if(actualTransTime.after(endTime)||actualTransTime.before(startTime)){
						logger.error("还款时间超出本期计息时间范围");
						result.put(SystemConst.retCode, SystemConst.FAIL);
						result.put(SystemConst.retMsg, "还款时间超出本期计息时间范围,请重新操作!");
						return result;
					}
					if(payPlanCompany.getCapitalTime()!=null&&payPlanCompany.getRepaymentCapital()!=null){//实际还本时间不为空&&应还本金不为空 说明是多次提前还本
						if(actualTransTime.before(payPlanCompany.getCapitalTime())){
							logger.error("本次还款时间应大于上次提前还款时间"+sdf1.format(payPlanCompany.getRepaymentCapitalTime()));
							result.put(SystemConst.retCode, SystemConst.FAIL);
							result.put(SystemConst.retMsg, "本次还款时间应大于上次提前还款时间"+sdf1.format(payPlanCompany.getCapitalTime())+",请重新操作!");
							return result;
						}
					}
					int dateTotal=DateUtils.daysBetween(startTime, endTime)+1;//本期还息总天数
					int dates=DateUtils.daysBetween(actualTransTime, endTime);//计算还款日期到本期计息结束日期天数
					int aa=dateTotal-dates;//计算还本金额变更后应还利息天数
					BigDecimal dateTotalSed = new BigDecimal(dateTotal);
					BigDecimal repaymentInterest=b1.multiply(payPlanCompany.getRepaymentInterest()).setScale(4,BigDecimal.ROUND_HALF_UP);
					repaymentInterest=repaymentInterest.multiply(new BigDecimal(dates)).setScale(4,BigDecimal.ROUND_HALF_UP).divide(dateTotalSed,4,BigDecimal.ROUND_HALF_UP).setScale(4,BigDecimal.ROUND_HALF_UP);
					payPlanCompany.setRepaymentInterest(payPlanCompany.getRepaymentInterest().subtract(repaymentInterest));//提前部分还本后本期应还利息
										
					//当期已还本金
					BigDecimal paidCapital=new BigDecimal(0);
					//当期应还本金
					BigDecimal repaymentCapital_=new BigDecimal(0);
					
					if(payPlanCompany.getPaidCapital()!=null){
						paidCapital=payPlanCompany.getPaidCapital();
					}
					if(payPlanCompany.getRepaymentCapital()!=null)repaymentCapital_=payPlanCompany.getRepaymentCapital();
					//如果应还本金小于（已还本金加上提前还本）
					if(repaymentCapital_.compareTo(paidCapital.add(advanceAmount))==-1){
						payPlanCompany.setRepaymentCapital(advanceAmount.add(payPlanCompany.getRepaymentCapitalSed()));//应该本金
						payPlanCompany.setRepaymentCapitalTime(actualTransTime);//应还本金时间
					}
					payPlanCompany.setRepaymentTotal(payPlanCompany.getRepaymentCapital().add(payPlanCompany.getRepaymentInterest()));//应收总额
				} catch (ParseException e) {
					e.printStackTrace();
				}//格式化放款时间
			}else{
				//重新计算部分还本之后 每期应还的利息
				payPlanCompany.setRepaymentInterest(investAmount.subtract(advanceAmount).multiply(interest).divide(investAmount,4,BigDecimal.ROUND_HALF_UP));
				if(i==list.size()-1){
					payPlanCompany.setRepaymentCapital(payPlanCompany.getRepaymentCapital().subtract(advanceAmount));
				}
				payPlanCompany.setRepaymentTotal(payPlanCompany.getRepaymentCapital().add(payPlanCompany.getRepaymentInterest()));//应收总额
				
				
			}
			
		}
		result.put("list", list);
		result.put("prePay", payEntity);
		result.put(SystemConst.retCode, SystemConst.SUCCESS);
		result.put(SystemConst.retMsg, "处理成功!");
		return result;
	}
	

	/**
	 * 部分付本
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.PaymanyTransService#executePayAll(com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo)
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String, Object> executePayPortion(LoanPayRecordCompanyVo payEntity) {
	    Map<String, Object> result = new HashMap<String,Object>();
	    result=this.getPayPortion(payEntity);
	    if(result.get("list")!=null){
	    	List<LoanPayPlanCompany> list=(List<LoanPayPlanCompany>) result.get("list");
	    	for(int i=0;i<list.size();i++){
	    		int resultInt=loanPayPlanCompanyMapper.updateByPrimaryKeySelective(list.get(i));
	    		if(resultInt!=1){
	    			logger.error("部分还本更新剩余期数数据失败");
	    			result.put(SystemConst.retCode, SystemConst.FAIL);
	    			result.put(SystemConst.retMsg, "部分还本更新剩余期数数据失败");
	    			return result;
	    		}
	    	}
	    	result.put(SystemConst.retCode, SystemConst.SUCCESS);
	    }else{
	    	logger.error("部分还本数据为空");
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "部分还本数据为空，操作失败");
			return result;
	    }
		//调用付款借口
		List<LoanPayPlanCompany> list=(List<LoanPayPlanCompany>) result.get("list");
		String retCode = this.loanPayCompanyTrans(list.get(0),payEntity);
		if(retCode.equals(SystemConst.FAIL)){
			logger.error("付款接口异常!");
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "付款接口异常!");
		}
		return result;
	}
	
	/**
	 * 
	 * loanPayCompanyTrans:部分还本--调用付款借口
	 * @author suncj
	 * @return
	 * @since JDK 1.8
	 */
	public String loanPayCompanyTrans(LoanPayPlanCompany payPlanCompany,LoanPayRecordCompanyVo payEntity){
		LoanEmp  loanEmp = CommonUtils.getEmpFromSession();
		//付款实体类对象：
		LoanPayRecordCompany recordCompany = new LoanPayRecordCompany();
		recordCompany.setPlanId(payPlanCompany.getId());//付款计划ID
		recordCompany.setTransMainId(payPlanCompany.getTransMainId());
		recordCompany.setActualTransTime(payEntity.getActualTransTime());//部分还本-实际还款时间
		recordCompany.setPaidCapital(payEntity.getAdvanceAmount());//部分还本--实际还本金额
		recordCompany.setPaidTotal(payEntity.getAdvanceAmount());//已还总额
		recordCompany.setLastUser(loanEmp.getLeStaffName());
		recordCompany.setLastModifyTime(new Date());
		recordCompany.setUnionId(loanEmp.getCompanyId());
		recordCompany.setCompanyId(loanEmp.getBranchCompanyId());
		return accountTransactionService.loanPayCompanyTrans(recordCompany);
	}
	
	public static void main(String[] args) {
//		BigDecimal b1 = new BigDecimal(0.1425);
//		BigDecimal b2 = new BigDecimal(1.2365);
//		BigDecimal b3 = new BigDecimal(100.0001); 
//		System.out.println("1111111111=============="+b1.divide(b2,4,BigDecimal.ROUND_HALF_UP).multiply(b3).setScale(4,BigDecimal.ROUND_HALF_UP));  
//		System.out.println("2222222222=============="+b1.divide(b2,4,BigDecimal.ROUND_HALF_UP));   
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endTime;
		try {
			endTime = sdf1.parse("2017-10-22 23:59:59");
			Date startTime = sdf1.parse("2017-09-23 00:00:00");
			int dates=DateUtils.daysBetween(startTime, endTime)+1;
			System.out.println("11111========"+dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
