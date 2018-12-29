/**
 * Project Name:loan-service
 * File Name:AccountTransactionServiceImpl.java
 * Package Name:com.lhhs.loan.service.account.impl
 * Date:2017年8月2日上午11:15:23
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.account.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.lhhs.loan.common.shared.constant.SystemConst.RepaymentMethod;
import com.lhhs.loan.common.shared.enums.Direction;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.GetUniqueNoUtil;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInOutInfoMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanAccountsSubjectAmountMapper;
import com.lhhs.loan.dao.LoanAccountsSubjectInfoMapper;
import com.lhhs.loan.dao.LoanAccountsTotalMapper;
import com.lhhs.loan.dao.LoanAccountsTransMapper;
import com.lhhs.loan.dao.LoanAdvanceMapper;
import com.lhhs.loan.dao.LoanAdvanceRecordMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.LoanFeesPlanMapper;
import com.lhhs.loan.dao.LoanFeesRecordMapper;
import com.lhhs.loan.dao.LoanFreezeMainMapper;
import com.lhhs.loan.dao.LoanPayPlanCompanyMapper;
import com.lhhs.loan.dao.LoanPayPlanCompanyTempMapper;
import com.lhhs.loan.dao.LoanPayPlanMapper;
import com.lhhs.loan.dao.LoanPayPlanTempMapper;
import com.lhhs.loan.dao.LoanPayRecordCompanyMapper;
import com.lhhs.loan.dao.LoanPayRecordMapper;
import com.lhhs.loan.dao.LoanRecordMapper;
import com.lhhs.loan.dao.LoanRecordTempMapper;
import com.lhhs.loan.dao.LoanTransMainMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.LoanAdvance;
import com.lhhs.loan.dao.domain.LoanAdvanceRecord;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanFeesPlan;
import com.lhhs.loan.dao.domain.LoanFeesRecord;
import com.lhhs.loan.dao.domain.LoanFreezeMain;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanPayPlanCompanyTemp;
import com.lhhs.loan.dao.domain.LoanPayPlanTemp;
import com.lhhs.loan.dao.domain.LoanPayRecord;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;
import com.lhhs.loan.dao.domain.LoanRecord;
import com.lhhs.loan.dao.domain.LoanRecordTemp;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo;
import com.lhhs.loan.service.account.AccountCoreTransService;
import com.lhhs.loan.service.account.AccountTransactionService;

/**
 * 交易场景实现 <br/>
 * Date:     2017年8月2日 上午11:15:23 <br/>
 * @author   dongfei
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	//垫付提前天数
	private static int AdvanceDay=0;
	//账户原子交易服务
	@Autowired
	private AccountCoreTransService accountCoreTransService;
	//交易记录Mapper
	@Autowired
	private LoanAccountsTransMapper loanAccountsTransMapper;
	//科目信息表
	@Autowired
	private LoanAccountsSubjectInfoMapper  loanAccountsSubjectInfoMapper;
	//账号信息 Mapper
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	//账户总账  Mapper
	@Autowired
	private LoanAccountsSubjectAmountMapper loanAccountsSubjectAmountMapper;
	//账户余额汇总 Mapper
	@Autowired
	private LoanAccountsTotalMapper loanAccountsTotalMapper;
	//放款主表Mapper
	@Autowired
	private LoanTransMainMapper loanTransMainMapper;
	//放款记录Mapper
	@Autowired
	private LoanRecordMapper loanRecordMapper;
	
	//还款计划Mapper
	@Autowired
	private LoanPayPlanMapper loanPayPlanMapper;
	//还款记录Mapper
	@Autowired
	private LoanPayRecordMapper loanPayRecordMapper;
	//应付计划Mapper
	@Autowired
	private LoanPayPlanCompanyMapper loanPayPlanCompanyMapper;
	//付款记录Mapper
	@Autowired
	private LoanPayRecordCompanyMapper loanPayRecordCompanyMapper;
	//放款记录临时Mapper
	@Autowired
	private LoanRecordTempMapper loanRecordTempMapper;
	//还款计划临时Mapper
	@Autowired
	private LoanPayPlanTempMapper loanPayPlanTempMapper;
	//应付款计划临时Mapper
	@Autowired
	private LoanPayPlanCompanyTempMapper loanPayPlanCompanyTempMapper;
	//收入支出计划Mapper
	@Autowired
	private LoanFeesPlanMapper loanFeesPlanMapper;
	//收入支出记录Mapper
	@Autowired
	private LoanFeesRecordMapper loanFeesRecordMapper;
	//冻结解冻Mapper
	@Autowired
	private LoanFreezeMainMapper loanFreezeMainMapper;
	//垫付
	@Autowired
	private LoanAdvanceMapper loanAdvanceMapper;
	//垫付记录
	@Autowired
	private LoanAdvanceRecordMapper loanAdvanceRecordMapper;
	
	@Autowired
	private LoanAccountInOutInfoMapper loanAccountInOutInfoMapper;
	
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	@Autowired
	private LoanCustomerInfoMapper loanCustomerInfoMapper;
	
	@Override
	@Transactional(readOnly = false)
	public LoanTransMain loanTransPre(LoanTransMain loanTransMain) {
		if(loanTransMain==null||StringUtils.isEmpty(loanTransMain.getBusinessId())
				||StringUtils.isEmpty(loanTransMain.getTransType())){
			throw new RuntimeException("放款主要数据不能为空！");
		}
	    String transType = loanTransMain.getTransType();//交易类型
	    
	    if (SystemConst.TransType.TYPEID1007.equals(transType)) {//固定理财
	    	loanTransMain.setIsPayPlanCompanyTemp(SystemConst.IsPayPlanCompanyTemp.YES);
	    	loanTransMain.setListLoanPayPlanCompanyTemp(generatePayPlanCompanyTemp(loanTransMain));
		}else{
			BigDecimal sumLoanAmount = new BigDecimal(0);//计算放款总金额
			//获取借款人信息
			List<LoanRecordTemp> loanRecordTempList = loanTransMain.getListLoanRecordTemp();
			for (int i = 0; i < loanRecordTempList.size(); i++) {
				LoanRecordTemp loanRecordTemp = loanRecordTempList.get(i);
				BigDecimal loanAmount = loanRecordTemp.getLoanAmount();//放款金额
				if (loanAmount!=null) {
					sumLoanAmount = sumLoanAmount.add(loanAmount);
				}
			}
			BigDecimal amount = loanTransMain.getAmount();//借款金额
			if (amount.compareTo(sumLoanAmount)<0 ) {
				throw new RuntimeException("放款金额必须大于等于投资人金额!");
			}else {
				loanTransMain.setAmount(sumLoanAmount);
				List<LoanPayPlanTemp> payPlanTemps= generateLoanPayPlanTemp(loanTransMain);
				List<LoanRecordTemp> loanRecordTemps= generateLoanRecordTemp(loanTransMain);
				List<LoanPayPlanCompanyTemp> payPlanCompanyTemps= generatePayPlanCompanyTemp(loanTransMain);
				List<LoanFeesPlan> feesPlans= generateLoanFeesPlan(loanTransMain);
				
				loanTransMain.setListLoanPayPlanTemp(payPlanTemps);
				loanTransMain.setListLoanRecordTemp(loanRecordTemps);
				loanTransMain.setListLoanPayPlanCompanyTemp( payPlanCompanyTemps);
				loanTransMain.setListFeesPlan_in(feesPlans);
				loanTransMain.setListFeesPlan_out(feesPlans);
			}
		}
		return loanTransMain;
	}

	/**
	 * 
	 * loanTrans:放款、固定理财转账交易<br/>

	 * @author dongfei
	 * @param loanTransMain 放款交易实体
	 * @return
	 * @since JDK 1.8
	 */
	@Override
	@Transactional(readOnly = false)
	public String loanTrans(LoanTransMain loanTransMain) {
		if(loanTransMain==null||StringUtils.isEmpty(loanTransMain.getBusinessId())
				|| StringUtils.isEmpty(loanTransMain.getBorrowerId())||loanTransMain.getAmount()==null
				||StringUtils.isEmpty(loanTransMain.getTransType())||loanTransMain.getLendingTime()==null
				||loanTransMain.getTerm()==null||StringUtils.isEmpty(loanTransMain.getTermUnit())){
			throw new RuntimeException("放款主要数据不能为空！");
			
		}
		if(StringUtils.isEmpty(loanTransMain.getCompanyId())
				|| StringUtils.isEmpty(loanTransMain.getUnionId())){
			throw new RuntimeException("放款或者理财业务归属公司和集团不能为空！");
			
		}
		String recode=SystemConst.FAIL;
		//业务编号
		String businessId=loanTransMain.getBusinessId();
		//理财、借款金额
		BigDecimal borrowerAmount=loanTransMain.getAmount();
		
		loanTransMain.setBorrowerAmount(borrowerAmount);
		/**
	     * 实际放款金额、理财金额
	     */
	    BigDecimal amount=loanTransMain.getAmount();
	    /**
	     * 交易类型
	     */
	    String transType=loanTransMain.getTransType();
	    String isPayPlanTemp=loanTransMain.getIsPayPlanTemp();//是否生成还款计划
	    String isLoanRecordTemp=loanTransMain.getIsLoanRecordTemp();//是否生成放款记录
	    String isPayPlanCompanyTemp=loanTransMain.getIsPayPlanCompanyTemp();//是否生成待付款计划
	    
	    /**逾期贷款利率**/
	    String overRate="0.00";
	    /**债务利率**/
	    String debtRate="0.00";;
	    /**还款违约金率**/
	    String breachRate="0.00";;
	    /**付款违约金率**/
	    String investBreachRate="0.00";;
	    /**逾期付款利率**/
	    String investOverRate="0.00";
	    /**
	     * 
	    LoanTransMain loanTransMain_query=new LoanTransMain();
	    loanTransMain_query.setBusinessId(loanTransMain.getBusinessId());
	    loanTransMain_query.setStatusList("03,88,89,90");
	    int count=loanTransMainMapper.queryCount(loanTransMain_query);
	    if(count>0){
	    	throw new RuntimeException("业务编号:"+loanTransMain.getBusinessId()+"已放款成功，不能重复放款！");
	    }
	    */
	    //如果是投资放款
	    if(SystemConst.TransType.TYPEID1001.equals(transType)){
		    LoanRecord loanRecord_query=new LoanRecord();
		    loanRecord_query.setOrderNo(loanTransMain.getBusinessId());
		    LoanRecord loanRecord=loanTransMainMapper.queryLoanRecordTempSum(loanRecord_query);
		    if(loanRecord!=null&&loanRecord.getLoanAmount()!=null){
		    	amount=loanRecord.getLoanAmount();
		    }
		    if(amount.compareTo(borrowerAmount)==1){
		    	throw new RuntimeException("放款金额不能大于借款金额！");
		    }
		    loanTransMain.setAmount(amount);
	    }

	    if(!StringUtils.isEmpty(loanTransMain.getOverRate())){
	    	overRate=(Double.parseDouble(loanTransMain.getOverRate())/100)+"";
	    }
	    if(!StringUtils.isEmpty(loanTransMain.getDebtRate())){
	    	debtRate=(Double.parseDouble(loanTransMain.getDebtRate())/100)+"";
	    }
	    
	    if(!StringUtils.isEmpty(loanTransMain.getBreachRate())){
	    	breachRate=(Double.parseDouble(loanTransMain.getBreachRate())/100)+"";
	    }
	    
	    if(!StringUtils.isEmpty(loanTransMain.getInvestBreachRate())){
	    	investBreachRate=(Double.parseDouble(loanTransMain.getInvestBreachRate())/100)+"";
	    }
	    
	    if(!StringUtils.isEmpty(loanTransMain.getInvestOverRate())){
	    	investOverRate=(Double.parseDouble(loanTransMain.getInvestOverRate())/100)+"";
	    }
	    loanTransMain.setOverRate(overRate);
	    loanTransMain.setDebtRate(debtRate);
	    loanTransMain.setBreachRate(breachRate);
	    loanTransMain.setInvestBreachRate(investBreachRate);
	    loanTransMain.setInvestOverRate(investOverRate);
	    
	    Date lendingTime=loanTransMain.getLendingTime();
	    int term=loanTransMain.getTerm();
	    String termUnit =loanTransMain.getTermUnit();
	    Date over_date=DateUtils.getlendingOverTime(lendingTime, term, termUnit);
	    
	    //loanTransMain.setLendingTime(lendingTime);
	    //借款截止日
	    loanTransMain.setOverTime(over_date);
	    //放款主表ID
	    String transMainId=GetUniqueNoUtil.getCustNo();
	    //放款ID
	    loanTransMain.setTransMainId(transMainId);
	    //插入放款主表，状态为草稿
	    loanTransMain.setStatus(SystemConst.Status.STATUS01);
	    loanTransMain.setCleanUpStatus(SystemConst.Status.STATUS01);
	    loanTransMain.setCreateTime(new Date());
	    loanTransMain.setLastModifyTime(new Date());
	    loanTransMain.setPaidPeriod((short)0);
	    loanTransMain.setInvestPeriod((short)0);
	    
	    int trans_flag=loanTransMainMapper.insertSelective(loanTransMain);
	    if(trans_flag<1){
	    	throw new RuntimeException("放款或者固定理财生产主数据失败！");
	    }
	    	
	    int plan_count=loanTransMainMapper.insertLoanPayPlan(loanTransMain);
	    int plan_company_count=loanTransMainMapper.insertLoanPayPlanCompany(loanTransMain);
	    loanTransMainMapper.updateLoanFeesPlan(loanTransMain);
	    int record_count=loanTransMainMapper.insertLoanRecord(loanTransMain);
	    //更新付款计划field3客户性质
	    loanTransMainMapper.updateLoanPayPlanCompany(loanTransMain);
	    if(SystemConst.TransType.TYPEID1001.equals(transType)){
	    	if(SystemConst.IsFlag.YES.equals(isPayPlanTemp)&&plan_count<1){
	    		throw new RuntimeException("插入还款计划记录失败！");
	    	}
	    	if(SystemConst.IsFlag.YES.equals(isPayPlanCompanyTemp)&&plan_company_count<1){
	    		throw new RuntimeException("插入付款款计划记录失败！");
	    	}
	    	if(SystemConst.IsFlag.YES.equals(isLoanRecordTemp)&&record_count<1){
	    		throw new RuntimeException("插入放款记录失败！");
	    	}
	    	
	    }else{
	    	if(SystemConst.IsFlag.YES.equals(isPayPlanCompanyTemp)&&plan_company_count<1){
	    		throw new RuntimeException("插入付款款计划记录失败！");
	    	}
	    }
	    
	    /**
	     * 放款记录列表
	     */
	    LoanRecord query_record=new LoanRecord();
	    query_record.setTransMainId(transMainId);
	    query_record.setStatus(SystemConst.Status.STATUS03);
	    List<LoanRecord> listLoanRecord=loanRecordMapper.queryList(query_record);
	    
	    //投资放款暂时去掉冻结解冻。
	    if(!SystemConst.TransType.TYPEID1001.equals(transType)){
		    //解冻冻结金额
		    LoanAccountsTrans tran_freeze=new LoanAccountsTrans();
		    //业务申请号
		    tran_freeze.setOrderNo(businessId);
		    tran_freeze.setLastUser(loanTransMain.getLastUser());

		    String freeze_flag=accountFreezeOutTrans(tran_freeze);
		    if(SystemConst.FAIL.equals(freeze_flag)){
		    	throw new RuntimeException("解冻失败，回滚插入。");
		    }
	    }

	  //借款人、投资人临时对象
	    LoanAccountsTrans act_trans_temp=new LoanAccountsTrans();
	    //交易类型
	    act_trans_temp.setTransType(loanTransMain.getTransType());
	    act_trans_temp.setLastUser(loanTransMain.getLastUser());
    	//业务申请号
	    act_trans_temp.setOrderNo(businessId);
    	//业务申请号
	    act_trans_temp.setBusinessId(businessId);
    	//放款主表ID
	    act_trans_temp.setTransMainId(transMainId);
    	
    	//备注
	    act_trans_temp.setTransRemark(loanTransMain.getSetRemark());
    	//设置业务省市
	    act_trans_temp.setProvienceNo(loanTransMain.getProductNo());
	    act_trans_temp.setProvienceName(loanTransMain.getProvienceName());
	    act_trans_temp.setCityNo(loanTransMain.getCityNo());
	    act_trans_temp.setCityName(loanTransMain.getCityName());
	    act_trans_temp.setCompanyId(loanTransMain.getCompanyId());
	    act_trans_temp.setUnionId(loanTransMain.getUnionId());
    	//借款金额
	    act_trans_temp.setAmount(amount);
	    //投资放款
    	if(SystemConst.TransType.TYPEID1001.equals(loanTransMain.getTransType())){
    		loanTransMain.setPeriodTotal((short)plan_count);
		    //放款人出账
		    for(int i=0;i<listLoanRecord.size();i++){
		    	LoanRecord record=listLoanRecord.get(i);
		    	LoanAccountsTrans act_trans=new LoanAccountsTrans();
	    		//复制loanPayPlan属性
	    		BeanUtils.copyProperties(act_trans_temp,act_trans);
		    	//借款人：
		    	act_trans.setOpponentAccountId(loanTransMain.getBorrowerAccountId());
		    	act_trans.setOpponentAccountName(loanTransMain.getBorrower());
		    	act_trans.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_INVEST_LOAN_OUT);
	    		act_trans.setBusinessType(SystemConst.BusinessType.INVEST);
		    	
		    	//放款金额
		    	act_trans.setAmount(record.getLoanAmount());
		    	//放款时间
		    	act_trans.setTransTime(record.getLoanTime());
		    	//放款账号
		    	act_trans.setAccountId(record.getAccountId());
		    	//放款人
		    	act_trans.setOwnerId(record.getLenderId());
		    	//放款人姓名
		    	act_trans.setOwnerName(record.getLenderName());
		    	//调用记账接口
		    	String trans_code=accountChangeTrans(act_trans);
		    	if(SystemConst.FAIL.equals(trans_code)){
		    		throw new RuntimeException(record.getLenderId()+"：投资人放款失败！");
		    	}
		    }
    	//固定理财放款
    	}else if(SystemConst.TransType.TYPEID1007.equals(loanTransMain.getTransType())){
    		loanTransMain.setPeriodTotal((short)plan_company_count);
    		LoanAccountsTrans trans_insert=new LoanAccountsTrans();
    		//复制loanPayPlan属性
    		BeanUtils.copyProperties(act_trans_temp,trans_insert);
    		trans_insert.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_FINANCING_LOAN_OUT);
    		trans_insert.setBusinessType(SystemConst.BusinessType.FINANCING);
    		//放款人：
    		trans_insert.setAccountId(loanTransMain.getPayerAccountId());
    		trans_insert.setAccountName(loanTransMain.getPayerName());
    		//调用记账接口
	    	String trans_code=accountChangeTrans(trans_insert);
	    	if(SystemConst.FAIL.equals(trans_code)){
	    		throw new RuntimeException(trans_insert.getAccountId()+"：投资人放款失败！");
	    	}
    	}
	    //借款人入账
	    LoanAccountsTrans act_trans_borrow=new LoanAccountsTrans();
	  //复制loanPayPlan属性
		BeanUtils.copyProperties(act_trans_temp,act_trans_borrow);
    	//收款账号
    	act_trans_borrow.setAccountId(loanTransMain.getBorrowerAccountId());
    	//收款款人
    	act_trans_borrow.setOwnerId(loanTransMain.getBorrowerId());
    	//收款人姓名
    	act_trans_borrow.setOwnerName(loanTransMain.getBorrower());
    	//根据业务类型设置放款科目
    	//投资放款
    	if(SystemConst.TransType.TYPEID1001.equals(loanTransMain.getTransType())){
    		act_trans_borrow.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_BORROWER_LOAN_IN);
    		act_trans_borrow.setBusinessType(SystemConst.BusinessType.INVEST);
    	//固定理财放款
    	}else if(SystemConst.TransType.TYPEID1007.equals(loanTransMain.getTransType())){
    		act_trans_borrow.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_FINANCING_LOAN_IN);
    		act_trans_borrow.setBusinessType(SystemConst.BusinessType.FINANCING);
    		act_trans_borrow.setOpponentAccountId(loanTransMain.getPayerAccountId());
    		act_trans_borrow.setOpponentAccountName(loanTransMain.getPayerName());
    	}
    	//调用记账接口
    	String trans_code=accountChangeTrans(act_trans_borrow);
    	if(SystemConst.FAIL.equals(trans_code)){
    		throw new RuntimeException("业务编号："+businessId+"放款失败！");
    	}
    	//更新放款主表状态有效
    	LoanTransMain loanTransMain_upate=new LoanTransMain();
    	loanTransMain_upate.setTransMainId(transMainId);
    	loanTransMain_upate.setStatus(SystemConst.Status.STATUS03);
    	loanTransMain_upate.setCleanUpStatus(SystemConst.Status.STATUS03);
    	loanTransMain_upate.setPeriodTotal(loanTransMain.getPeriodTotal());
    	loanTransMain_upate.setLastModifyTime(loanTransMain.getLastModifyTime());
    	trans_flag=loanTransMainMapper.updateByPrimaryKeySelective(loanTransMain_upate);
    	if(trans_flag>0){
    		recode=SystemConst.SUCCESS;
    	}else{
    		throw new RuntimeException("业务编号："+businessId+"放款失败！");
    	}
    	
		return recode;
	}
	/**
	 * 
	 * loanTrans:转账 <br/>

	 * @author dongfei
	 * @param LoanTransAccounts 转账
	 * @return
	 * @since JDK 1.8
	 */
	@Override
	@Transactional(readOnly = false)
	public String accountTrans(LoanAccountsTrans loanAccountsTrans) {
		String recode=SystemConst.FAIL;
		if(loanAccountsTrans==null||StringUtils.isEmpty(loanAccountsTrans.getBusinessId())||loanAccountsTrans.getAmount()==null
				||StringUtils.isEmpty(loanAccountsTrans.getAccountId())||StringUtils.isEmpty(loanAccountsTrans.getOpponentAccountId())){
			throw new RuntimeException("转账主要数据不能为空！");
		}
		loanAccountsTrans.setTransType(SystemConst.TransType.TYPEID1008);
		//转出账户
		String accountId=loanAccountsTrans.getAccountId();
		//转出科目
		String subjectId=loanAccountsTrans.getSubjectId();
		//转入账户
	    String opponentAccountId=loanAccountsTrans.getOpponentAccountId();
	    //转入账户科目编号
	    String opponentSubjectId=loanAccountsTrans.getOpponentSubjectId();

	    //检查subjectId科目是出账科目
		LoanAccountsSubjectInfo subject=loanAccountsSubjectInfoMapper.selectByPrimaryKey(subjectId);
		if(subject==null ||StringUtils.isEmpty(subject.getSubjectId())){
			throw new RuntimeException("subjectId:"+subjectId+"科目不存在，请核对。");
		}
		//判断交易方向
		if(!Direction.OUT.getId().equals(subject.getDirection())){
			throw new RuntimeException("转出subjectId:"+subjectId+"-"+subject.getDirection()+"科目不正确！");
		}
		
		//检查opponentSubjectId科目是入账科目
		LoanAccountsSubjectInfo subject_opt=loanAccountsSubjectInfoMapper.selectByPrimaryKey(opponentSubjectId);
		if(subject_opt==null ||StringUtils.isEmpty(subject_opt.getSubjectId())){
			throw new RuntimeException("subjectId:"+subjectId+"科目不存在，请核对。");
		}
		//判断交易方向
		if(!Direction.IN.getId().equals(subject_opt.getDirection())){
			throw new RuntimeException("转入subjectId:"+opponentSubjectId+"-"+subject_opt.getDirection()+"科目不正确！");
		}
		//转出账户冻结解冻
		recode=accountFreezeOutTrans(loanAccountsTrans);
		if(SystemConst.FAIL.equals(recode)){
			throw new RuntimeException("解冻失败！");
		}
		//转出账户扣款
		recode=accountChangeTrans(loanAccountsTrans);
		if(SystemConst.FAIL.equals(recode)){
			throw new RuntimeException("转出交易失败！");
		}
    	LoanAccountsTrans trans_insert=new LoanAccountsTrans();
		//复制loanPayPlan属性
		BeanUtils.copyProperties(loanAccountsTrans,trans_insert);
		trans_insert.setAccountId(opponentAccountId);
		trans_insert.setSubjectId(opponentSubjectId);
		trans_insert.setOpponentAccountId(accountId);
		trans_insert.setOpponentAccountName(loanAccountsTrans.getOpponentAccountName());
		recode=accountChangeTrans(trans_insert);
		if(SystemConst.FAIL.equals(recode)){
			throw new RuntimeException("转入交易失败！");
		}
		//转入账户入账
		return recode;
	}
	/**
	 * 
	 * accountFreezeTrans:冻结、解冻账户余额交易
	 *
	 * @author dongfei
	 * @param loanAccountsTrans 账户交易流水实体，解冻时只需送解冻编号、最后操作人
	 * @return 冻结编号，失败01
	 * @since JDK 1.8
	 */
	@Override
	@Transactional(readOnly = false)
	public String accountFreezeTrans(LoanAccountsTrans loanAccountsTrans) {
		
		String recode=SystemConst.FAIL;
		//交易类型
		String tansType=loanAccountsTrans.getTransType();
		//增量冻结标识
		String updateFlag=loanAccountsTrans.getUpdateFlag();
		LoanFreezeMain freezetrans_main=null;
		LoanFreezeMain freezetrans_upate=null;
		//插入实体
		LoanFreezeMain trans_temp=new LoanFreezeMain();
		//复制loanPayPlan属性
		BeanUtils.copyProperties(loanAccountsTrans,trans_temp);
		// 插入本次增量记录
		String tranId="";
		String transMainId="";
		BigDecimal amountIn=new BigDecimal(0);
	    BigDecimal amountOut=new BigDecimal(0);
	    BigDecimal amount=loanAccountsTrans.getAmount();
	    int flag=0;
		//如果解冻、需要冻结编号
		if(SystemConst.TransType.TYPEID1012.equals(tansType)){
			String oldTransId =loanAccountsTrans.getOldTransId();
			if(StringUtils.isEmpty(loanAccountsTrans.getOldTransId())){
				throw new RuntimeException("解冻时冻结编号不能为空");
			}
			freezetrans_main=loanFreezeMainMapper.selectByPrimaryKey(loanAccountsTrans.getOldTransId());
			if(freezetrans_main==null){
				throw new RuntimeException("根据冻结编号没有查询到冻结信息");
			}
			loanAccountsTrans.setAccountId(freezetrans_main.getAccountId());
			loanAccountsTrans.setSubjectId(freezetrans_main.getSubjectId());
			//如果解冻成功，直接返回
			if(SystemConst.Status.STATUS90.equals(freezetrans_main.getStatus())){
				throw new RuntimeException("业务编号："+loanAccountsTrans.getOrderNo()+"的冻结编号"+loanAccountsTrans.getOldTransId()+"已解冻成功!");
			}
			if(freezetrans_main.getAmountIn()!=null){
				amountIn=freezetrans_main.getAmountIn();
			}
			if(freezetrans_main.getAmountOut()!=null){
				amountOut=freezetrans_main.getAmountOut();
			}
			//解冻金额不能大于冻结金额
		    if(amountIn.subtract(amountOut).compareTo(amount)==-1){
		    	throw new RuntimeException("业务编号："+loanAccountsTrans.getOrderNo()+"的冻结编号"+loanAccountsTrans.getOldTransId()+"解冻金额"+amount+"不能大于剩余冻结金额"+amountIn.subtract(amountOut)+"!");
		    }
		    
			//新建更新实体
			freezetrans_upate=new LoanFreezeMain();
			//冻结编号
			freezetrans_upate.setTransId(oldTransId);
			freezetrans_upate.setAmountOut(amount);
			
			LoanFreezeMain trans_insert=new LoanFreezeMain();
			//复制loanPayPlan属性
			BeanUtils.copyProperties(freezetrans_main,trans_insert);
			trans_insert.setIsFlag(SystemConst.IsFlag.NO);
			tranId=GetUniqueNoUtil.getCustNo();
			trans_insert.setTransId(tranId);
			trans_insert.setAmountIn(new BigDecimal(0));
			trans_insert.setAmountOut(amount);
			trans_insert.setTransType(tansType);
			//主记录ID
			trans_insert.setTransMainId(freezetrans_main.getTransId());
			trans_insert.setCreateUser(loanAccountsTrans.getLastUser());
			trans_insert.setCreateTime(new Date());
			trans_insert.setLastUser(loanAccountsTrans.getLastUser());
			trans_insert.setLastModifyTime(new Date());
			trans_insert.setStatus(SystemConst.Status.STATUS03);
			
			loanFreezeMainMapper.insertSelective(trans_insert);
			loanAccountsTrans.setAmount(amount);
			//执行解冻
			recode=accountChangeTrans(loanAccountsTrans);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("解冻账户变动失败");
			}
			//更新主记录
			freezetrans_upate.setAmountOut(loanAccountsTrans.getAmount());
			freezetrans_upate.setTransId(freezetrans_main.getTransId());
			freezetrans_upate.setLastModifyTime(freezetrans_main.getLastModifyTime());
			freezetrans_upate.setLastUser(loanAccountsTrans.getLastUser());
			
			flag=loanFreezeMainMapper.updateAmount(freezetrans_upate);
			if(flag<1){
				throw new RuntimeException("更新冻结主记录失败");
			}

		//冻结
		}else if(SystemConst.TransType.TYPEID1011.equals(tansType)){
			
			if(StringUtils.isEmpty(loanAccountsTrans.getOrderNo())){
				throw new RuntimeException("冻结时业务编号不能为空!");
			}
			LoanFreezeMain trans_query=new LoanFreezeMain();
			trans_query.setBusinessId(loanAccountsTrans.getOrderNo());
			trans_query.setAccountId(loanAccountsTrans.getAccountId());
			trans_query.setIsFlag(SystemConst.IsFlag.YES);
			trans_query.setStatus(SystemConst.Status.STATUS03);
			
			List<LoanFreezeMain> trans_query_list=loanFreezeMainMapper.queryList(trans_query);
			//同一个业务单号、同一账户，如果不是增量只能冻结一次
			if(!SystemConst.IsFlag.YES.equals(updateFlag)&&trans_query_list!=null&&trans_query_list.size()>0){
				
				throw new RuntimeException("OrderNo"+loanAccountsTrans.getOrderNo()+",accountId"+loanAccountsTrans.getAccountId()+",同一个业务单号、同一账户只能冻结一次,请先解冻!");
				
			}else{
				
				//更新本条记录
				if(trans_query_list!=null&&trans_query_list.size()>0){
					freezetrans_main=trans_query_list.get(0);
					loanAccountsTrans.setAccountId(freezetrans_main.getAccountId());
					loanAccountsTrans.setSubjectId(freezetrans_main.getSubjectId());
					transMainId=freezetrans_main.getTransId();
					LoanFreezeMain trans_insert=new LoanFreezeMain();
					//复制loanPayPlan属性
					BeanUtils.copyProperties(freezetrans_main,trans_insert);
					trans_insert.setIsFlag(SystemConst.IsFlag.NO);
					tranId=GetUniqueNoUtil.getCustNo();
					trans_insert.setTransId(tranId);
					trans_insert.setAmountIn(loanAccountsTrans.getAmount());
					//解冻金额为0
					trans_insert.setAmountOut(new BigDecimal(0));
					//主记录ID
					trans_insert.setTransMainId(freezetrans_main.getTransId());
					trans_insert.setCreateUser(loanAccountsTrans.getLastUser());
					trans_insert.setCreateTime(new Date());
					trans_insert.setLastUser(loanAccountsTrans.getLastUser());
					trans_insert.setLastModifyTime(new Date());
					trans_insert.setStatus(SystemConst.Status.STATUS03);
					flag=loanFreezeMainMapper.insertSelective(trans_insert);
					if(flag<1){
						throw new RuntimeException("插入冻结主记录失败");
					}
					//新建更新实体
					freezetrans_upate=new LoanFreezeMain();
					//更新主记录
					freezetrans_upate.setAmountIn(loanAccountsTrans.getAmount());
					freezetrans_upate.setAmountOut(new BigDecimal(0));
					freezetrans_upate.setTransId(freezetrans_main.getTransId());
					freezetrans_upate.setLastModifyTime(freezetrans_main.getLastModifyTime());
					flag=loanFreezeMainMapper.updateAmount(freezetrans_upate);
					if(flag<1){
						throw new RuntimeException("插入冻结记录失败");
					}
					
				}else{
					LoanFreezeMain trans_insert=new LoanFreezeMain();
					//复制loanPayPlan属性
					BeanUtils.copyProperties(trans_temp,trans_insert);
					trans_insert.setIsFlag(SystemConst.IsFlag.YES);
					trans_insert.setAmountIn(loanAccountsTrans.getAmount());
					//解冻金额为0
					trans_insert.setAmountOut(new BigDecimal(0));
					trans_insert.setBusinessId(loanAccountsTrans.getOrderNo());
					trans_insert.setTransMainId("0");
					transMainId=GetUniqueNoUtil.getCustNo();
					trans_insert.setTransId(transMainId);
					trans_insert.setStatus(SystemConst.Status.STATUS03);
					trans_insert.setCreateUser(loanAccountsTrans.getLastUser());
					trans_insert.setCreateTime(new Date());
					trans_insert.setLastUser(loanAccountsTrans.getLastUser());
					trans_insert.setLastModifyTime(new Date());
					//插入主记录
					loanFreezeMainMapper.insertSelective(trans_insert);
					
					LoanFreezeMain trans_insert_child=new LoanFreezeMain();
					//复制loanPayPlan属性
					BeanUtils.copyProperties(trans_insert,trans_insert_child);
					trans_insert_child.setIsFlag(SystemConst.IsFlag.NO);
					trans_insert_child.setTransMainId(transMainId);
					tranId=GetUniqueNoUtil.getCustNo();
					trans_insert_child.setTransId(tranId);
					trans_insert_child.setStatus(SystemConst.Status.STATUS03);
					trans_insert_child.setCreateUser(loanAccountsTrans.getLastUser());
					trans_insert_child.setCreateTime(new Date());
					trans_insert_child.setLastUser(loanAccountsTrans.getLastUser());
					trans_insert_child.setLastModifyTime(new Date());
					//插入子记录
					loanFreezeMainMapper.insertSelective(trans_insert_child);
					
				}
				//冻结主ID
				loanAccountsTrans.setTransMainId(transMainId);
				//冻结记录ID
				loanAccountsTrans.setBusinessId(tranId);
				recode=accountChangeTrans(loanAccountsTrans);
			}
			
			
		}
		
		return recode;
	}

	/**
	 * 
	 * accountFreezeTrans:同一业务批量冻结账户余额交易
	 *
	 * @author dongfei
	 * @param loanAccountsTrans 账户交易流水实体
	 * @return 成功：00，失败：01
	 * @since JDK 1.8
	 */
	@Transactional(readOnly = false)
	public String  accountFreezeInTrans(List<LoanAccountsTrans> list){
		String recode=SystemConst.FAIL;
		if(list==null||list.size()<1 ||StringUtils.isEmpty(list.get(0).getOrderNo())){
			throw new RuntimeException("冻结列表为空、或者业务申请编号为空，不能执行该交易!");
		}
		for(int i=0;i<list.size();i++){
			LoanAccountsTrans trans=list.get(i);
			trans.setTransType(SystemConst.TransType.TYPEID1011);
			recode=accountFreezeTrans(trans);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("批量冻结失败，请重试!");
			}
			
		}
		return recode;
	}

	/**
	 * 
	 * accountFreezeTrans:根据业务申请号批量解冻
	 *
	 * @author dongfei
	 * @param loanAccountsTrans 账户交易流水实体
	 * @return 成功：00，失败：01
	 * @since JDK 1.8
	 */
	@Transactional(readOnly = false)
	public String  accountFreezeOutTrans(LoanAccountsTrans trans){
		String recode=SystemConst.FAIL;
		//批量解冻，业务申请号不能为空
		if(trans==null ||StringUtils.isEmpty(trans.getOrderNo())){
			throw new RuntimeException("解冻，业务申请号不能为空!");
		}
		LoanFreezeMain trans_query=new LoanFreezeMain();
		trans_query.setBusinessId(trans.getOrderNo());
		trans_query.setTransType(SystemConst.TransType.TYPEID1011);
		trans_query.setIsFlag(SystemConst.IsFlag.YES);
		trans_query.setStatus(SystemConst.Status.STATUS03);
		
		List<LoanFreezeMain> trans_query_list=loanFreezeMainMapper.queryList(trans_query);
		
		if(trans_query_list==null||trans_query_list.size()<1){
			throw new RuntimeException("OrderNo"+trans.getOrderNo()+",无待解记录!");
		}
		
		for(int i=0;i<trans_query_list.size();i++){
			LoanFreezeMain trans_main=trans_query_list.get(i);
			LoanAccountsTrans trans_temp=new LoanAccountsTrans();
			//复制loanPayPlan属性
			BeanUtils.copyProperties(trans,trans_temp);
			trans_temp.setTransType(SystemConst.TransType.TYPEID1012);
			trans_temp.setOldTransId(trans_main.getTransId());
			BigDecimal amount=trans_main.getAmountIn();
			if(trans_main.getAmountOut()!=null){
				amount=amount.subtract(trans_main.getAmountOut());
			}
			trans_temp.setOrderNo(trans_main.getBusinessId());
			trans_temp.setAccountId(trans_main.getAccountId());
			trans_temp.setAmount(amount);
			recode=accountFreezeTrans(trans_temp);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("批量冻结失败，请重试!");
			}else{
				//更新解冻状态为完成
				LoanFreezeMain trans_main_update=new LoanFreezeMain();
				trans_main_update.setTransId(trans_main.getTransId());
				trans_main_update.setStatus(SystemConst.Status.STATUS90);
				int flag=loanFreezeMainMapper.updateByPrimaryKeySelective(trans_main_update);
				
			}
			
			recode=SystemConst.SUCCESS;
			
		}
		return recode;
		
	}
	/**
	 * 
	 * loanPayTrans:还款交易
	 *
	 * @author dongfei
	 * @param loanPayRecord
	 * @return
	 * @since JDK 1.8
	 */
	@Override
	@Transactional(readOnly = false)
	public String loanPayTrans(LoanPayRecord loanPayRecord) {
		String recode=SystemConst.FAIL;	
		if(loanPayRecord==null||StringUtils.isEmpty(loanPayRecord.getPlanId())
				||StringUtils.isEmpty(loanPayRecord.getTransMainId())||loanPayRecord.getPaidTotal()==null){
			throw new RuntimeException("还款主要数据不能为空！");
		}
		
		//还款计划ID
		Long planId=loanPayRecord.getPlanId();
		//当前期数
		Short period=-1;
	    //还款本金
	    BigDecimal paidCapital=new BigDecimal(0);
	    if(loanPayRecord.getPaidCapital()!=null){
	    	paidCapital=loanPayRecord.getPaidCapital();
	    }
	    //还款利息
	    BigDecimal paidInterest=loanPayRecord.getPaidInterest();
	    //逾期罚息
	    BigDecimal paidOverdue=loanPayRecord.getPaidOverdue();
	    //违约金
	    BigDecimal paidCompensate=loanPayRecord.getPaidCompensate();
	    
	    BigDecimal paid_total=new BigDecimal(0);
	    if(paidCapital!=null&&paidCapital.compareTo(new BigDecimal(0))!=-1){
	    	paid_total=paid_total.add(paidCapital);
	    }else if(paidCapital!=null&&paidCapital.compareTo(new BigDecimal(0))==-1){
	    	throw new RuntimeException("还款本金不能小于0！");
	    }
	    if(paidInterest!=null){
	    	paid_total=paid_total.add(paidInterest);
	    }
	    if(paidOverdue!=null){
	    	paid_total=paid_total.add(paidOverdue);
	    }
	    if(paidCompensate!=null){
	    	paid_total=paid_total.add(paidCompensate);
	    }
	    
	    //还款时间
	    Date actualTransTime=loanPayRecord.getActualTransTime();
	    //已还本金总额
	    BigDecimal paidCapital_total=new BigDecimal(0);
	    //应还本金总额
	    BigDecimal repaymentCapital=new BigDecimal(0);
	    //已还款利息总额
	    BigDecimal paidInterest_total=new BigDecimal(0);
	    //已还逾期罚息
	    BigDecimal paidOverdue_total=new BigDecimal(0);
	    //已还违约金
	    BigDecimal paidCompensate_total=new BigDecimal(0);
	    //已收息差
	    BigDecimal interestSpread_total=new BigDecimal(0);
	   
	    //应付利息总额
	    BigDecimal repaymentInterest_total=new BigDecimal(0);
	    //息差
	    BigDecimal interest_spread=new BigDecimal(0);
	    //剩余利息
	    BigDecimal interest_surplus=new BigDecimal(0);
	    //在途金额
	    BigDecimal ontheway_amount=new BigDecimal(0);
	    
		//查询还款计划
		LoanPayPlan loanPayPlan=loanPayPlanMapper.selectByPrimaryKey(planId.toString());
		period=loanPayPlan.getPeriod();
		if(loanPayPlan==null||StringUtils.isEmpty(loanPayPlan.getId())){
			throw new RuntimeException("没查询到需要还款的记录!");
		}
		String transMainId=loanPayPlan.getTransMainId();
		//查询放款账务主记录
		LoanTransMain loanTransMain =loanTransMainMapper.selectByPrimaryKey(transMainId);
		if(loanTransMain==null||StringUtils.isEmpty(loanTransMain.getTransMainId())){
			throw new RuntimeException("没查询到放款主记录!");
		}
		//总期数
//		Short period_total=loanTransMain.getPeriodTotal();
		//借款到期日
		Date over_date=loanTransMain.getOverTime();
		//查询未还的还款计划首条还款计划 如果点前期有未还的，不能进行还款
		

		//本期应还本金总额
		if(loanPayPlan.getRepaymentCapital()!=null){
			repaymentCapital=loanPayPlan.getRepaymentCapital();
		}
		
		Date repayment_capital_time =loanPayPlan.getRepaymentCapitalTime();
		Date repayment_interest_time=loanPayPlan.getRepaymentInterestTime();
		int overdueDays=0;
		
		if(repayment_capital_time!=null){
			overdueDays=DateUtils.daysBetween(repayment_capital_time, actualTransTime);
		}
		if(repayment_interest_time!=null){
			int dayNum_temp=DateUtils.daysBetween(repayment_interest_time, actualTransTime);
			if(dayNum_temp>overdueDays)overdueDays=dayNum_temp;
		}
		
		//还款计划类型
		String plan_type=loanPayPlan.getTransType();
		if(overdueDays>0){
			plan_type=SystemConst.Status.STATUS89;
		}
		if(overdueDays<0)overdueDays=0;
		
		LoanPayRecord loanPayRecord_query=new LoanPayRecord();
		//查询还款记录
		loanPayRecord_query.setTransMainId(transMainId);
		loanPayRecord_query.setPeriod(period);
		loanPayRecord_query.setAccountId(loanTransMain.getBorrowerAccountId());
		//已还本金、利息金额汇总
		LoanPayRecord payRecord=loanPayRecordMapper.queryPaysum(loanPayRecord_query);
		if(payRecord!=null){
			//已还利息总额
			if(payRecord.getPaidInterest()!=null)
				paidInterest_total=payRecord.getPaidInterest();
			//已还本金总额
			if(payRecord.getPaidCapital()!=null)
				paidCapital_total=payRecord.getPaidCapital();
			//已还逾期罚息
			if(payRecord.getPaidOverdue()!=null)
			paidOverdue_total=payRecord.getPaidOverdue();
			//已还违约金
			if(payRecord.getPaidCompensate()!=null)
			paidCompensate_total=payRecord.getPaidCompensate();
			//已收息差
			if(payRecord.getInterestSpread()!=null)
				interestSpread_total=payRecord.getInterestSpread();
			
		}
		if(paidCapital.compareTo(new BigDecimal(0))==1
				&&repaymentCapital.compareTo(paidCapital.add(paidCapital_total))==-1){
			throw new RuntimeException("还本金额不能大于剩余应还本金!");
	    }
		/**
		//因为有提前部分还本，对多还本金不处理
		if(paidCapital_total.add(paidCapital).compareTo(repaymentCapital)==1){
			//本次还款多余的本金
			BigDecimal overpaidCapital=paidCapital_total.add(paidCapital).subtract(repaymentCapital);
			//多余的本金放到利息中
			paidInterest=paidInterest.add(overpaidCapital);
			//本次应还本金=本次还款本金-本次还款多余的本金
			paidCapital=paidCapital.subtract(overpaidCapital);
		}
		**/
		//查询付款计划
		LoanPayPlanCompany loanPayPlanCompany_query=new LoanPayPlanCompany();
		
		loanPayPlanCompany_query.setTransMainId(transMainId);
		loanPayPlanCompany_query.setPeriod(period);
		//查询付款计划列表 具体某一期应付本金、应付利息、应付总额
		LoanPayPlanCompany payPlanCompany=loanPayPlanCompanyMapper.queryPayPlansum(loanPayPlanCompany_query);
		if(payPlanCompany!=null&&payPlanCompany.getRepaymentInterest()!=null){
			repaymentInterest_total=payPlanCompany.getRepaymentInterest();
		}
		
		
		//待插入还款记录对象
		LoanPayRecord loanPayRecord_insert=new LoanPayRecord();
		String tranId=GetUniqueNoUtil.getCustNo();
		//复制loanPayPlan属性
		BeanUtils.copyProperties(loanPayPlan,loanPayRecord_insert);
		loanPayRecord_insert.setActualTransTime(loanPayRecord.getActualTransTime());
		//设置操作人员信息
		loanPayRecord_insert.setLastUser(loanPayRecord.getLastUser());
		loanPayRecord_insert.setLastModifyTime(new Date());
		loanPayRecord_insert.setCreateTime(new Date());
		loanPayRecord_insert.setCreateUser(loanPayRecord.getLastUser());
		loanPayRecord_insert.setOverdueDays(overdueDays);
		//还款类型
		loanPayRecord_insert.setTransType(plan_type);
		//记录状态 正常
		loanPayRecord_insert.setStatus(SystemConst.Status.STATUS03);
		loanPayRecord_insert.setId(tranId);
		loanPayRecord_insert.setPlanId(planId);
		loanPayRecord_insert.setTransMainId(transMainId);
		//设置公司、集团
		loanPayRecord_insert.setCompanyId(loanTransMain.getCompanyId());
		loanPayRecord_insert.setUnionId(loanTransMain.getUnionId());
		//息差
		loanPayRecord_insert.setInterestSpread(interest_spread);
		//还本金
		loanPayRecord_insert.setPaidCapital(paidCapital);
		//利息
		loanPayRecord_insert.setPaidInterest(paidInterest);
		//逾期
		loanPayRecord_insert.setPaidOverdue(paidOverdue);
		//违约
		loanPayRecord_insert.setPaidCompensate(paidCompensate);
		//已还总额
		loanPayRecord_insert.setPaidTotal(paid_total);
		//如果为还款计划状态为提前还款、并且时间还款时间在借款截至日期之前。
		if(SystemConst.Status.STATUS88.equals(plan_type)&&DateUtils.daysBetween(actualTransTime,over_date)>0){
			//息差=本次还款利息
			if(interestSpread_total.compareTo(new BigDecimal(0))==1){
				interest_spread=interestSpread_total.multiply(new BigDecimal(-1));
				//剩余应付利息0
				interest_surplus=interestSpread_total;
			}
			//息差=-本期已记息差
			//平台公司出账 本期已记息差
			//在途金额=本金+本次利息+违约金+本期已记息差
		}else{
			if(paidInterest.compareTo(new BigDecimal(0))==1){
				//设置还息时间
				loanPayRecord_insert.setInterestTime(loanPayRecord.getActualTransTime());
				
				//剩余应付利息=应付利息-已还利息
				interest_surplus=repaymentInterest_total.subtract(paidInterest_total);
				//如果剩余应付利息小于等于0，表示应付利息已还完，剩下的为息差
				if(interest_surplus.compareTo(new BigDecimal(0))!=1){
					//剩余应付利息0
					interest_surplus=new BigDecimal(0);
					//息差=本次还款利息
					interest_spread=paidInterest;
					//如果剩余应付利息大于本次还款利息
				}else if(interest_surplus.compareTo(paidInterest)!=-1){
					//设置剩余应付利息为本次还款利息，做为记账在途账利息
					interest_surplus=paidInterest;
					//息差=0
					interest_spread=new BigDecimal(0);
					//剩余利息小于本次还款利息
				}else if(interest_surplus.compareTo(paidInterest)==-1){
					//息差=本次还款利息-剩余应付利息
					interest_spread=paidInterest.subtract(interest_surplus);
				}
			}
		}
		//设置本次息差
		loanPayRecord_insert.setInterestSpread(interest_spread);
		//计算在途金额=本次还款总额(利息+本金+逾期利息+违约金)-息差
		ontheway_amount=paid_total.subtract(interest_spread);
		if(paidCapital!=null&&paidCapital.compareTo(new BigDecimal(0))==1){
			//设置还本时间
			loanPayRecord_insert.setCapitalTime(loanPayRecord.getActualTransTime());
		}
		int flag_record=loanPayRecordMapper.insertSelective(loanPayRecord_insert);

		//借款账户记账交易
		if(flag_record>0){
			LoanAccountsTrans trans_temp=new LoanAccountsTrans();
			//复制loanPayPlan属性
			BeanUtils.copyProperties(loanPayRecord_insert,trans_temp);
			trans_temp.setTransType(SystemConst.TransType.TYPEID1002);
			//业务单号
			trans_temp.setOrderNo(loanTransMain.getBusinessId());
			trans_temp.setBusinessType(SystemConst.BusinessType.INVEST);
			//还款记录ID
			trans_temp.setBusinessId(tranId);
			//设置省市
			trans_temp.setProvienceNo(loanTransMain.getProductNo());
			trans_temp.setProvienceName(loanTransMain.getProvienceName());
			trans_temp.setCityNo(loanTransMain.getCityNo());
			trans_temp.setCityName(loanTransMain.getCityName());
			trans_temp.setOwnerId(loanTransMain.getBorrowerId());
			trans_temp.setOwnerName(loanTransMain.getBorrower());
			trans_temp.setField1(loanTransMain.getTransMainId());
			//设置在途金额
			trans_temp.setOnTheWayAmount(ontheway_amount);
			//借款人出账、新增在途金额
			recode=loanActPayTrans(trans_temp,loanTransMain,loanPayRecord);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("借款人出账失败!");
			}

			//记息差
			if(interest_spread!=null&&interest_spread.compareTo(new BigDecimal(0))!=0){
				LoanAccountInfo act_qury=new LoanAccountInfo();
				//设置公司编码
				act_qury.setOwnerId(loanTransMain.getCompanyId());
				act_qury.setAccountType(SystemConst.AccountType.COMPANY);
				act_qury.setStatus(SystemConst.Status.STATUS03);
				List<LoanAccountInfo> list_act=loanAccountInfoMapper.queryList(act_qury);
				if(list_act==null||list_act.size()<1){
					throw new RuntimeException("没查询到公司对应的账号，不能做息差收入，请在公司账户配置!");
				}
				LoanAccountInfo act_company=list_act.get(0);
				LoanAccountsTrans trans_insert=new LoanAccountsTrans();
				//复制loanPayPlan属性
				BeanUtils.copyProperties(trans_temp,trans_insert);
				trans_insert.setOwnerId(act_company.getOwnerId());
				trans_insert.setOwnerName(act_company.getOwnerName());
				trans_insert.setAccountId(act_company.getAccountId());
				trans_insert.setAccountName(act_company.getOwnerName());
				//设置息差科目
				if(interest_spread.compareTo(new BigDecimal(0))==1){
					trans_insert.setAmount(interest_spread);
					trans_insert.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_REPAY_INTEREST_SPREAD_IN);
				//息差退款
				}else if(interest_spread.compareTo(new BigDecimal(0))==-1){
					trans_insert.setAmount(interest_spread.multiply(new BigDecimal(-1)));
					trans_insert.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_REPAY_INTEREST_SPREAD_OUT);
				}
				recode=accountChangeTrans(trans_insert);
				if(SystemConst.FAIL.equals(recode)){
					throw new RuntimeException("入平台息差交易失败!");
				}
			}
			
			//更新还款计划
			//更新已还本金、已还利息
			loanPayPlan.setPaidCapital(paidCapital_total.add(paidCapital));
			loanPayPlan.setPaidInterest(paidInterest_total.add(paidInterest));
			loanPayPlan.setPaidOverdue(paidOverdue_total.add(paidOverdue));
			loanPayPlan.setPaidCompensate(paidCompensate_total.add(paidCompensate));
			loanPayPlan.setInterestSpread(interestSpread_total.add(interest_spread));
			//如果已还本息总额大于等于应还本息总额设置还款状态为完成
			if(loanPayPlan.getRepaymentCapital().compareTo(paidCapital_total.add(paidCapital))!=1
					&&loanPayPlan.getRepaymentInterest().compareTo(paidInterest_total.add(paidInterest))!=1){
				loanPayPlan.setStatus(SystemConst.Status.STATUS90);
				
			}
			//设置还本、还息时间
			if(paidCapital.compareTo(new BigDecimal(0))==1){
				loanPayPlan.setCapitalTime(actualTransTime);
			}
			if(paidInterest.compareTo(new BigDecimal(0))==1){
				loanPayPlan.setInterestTime(actualTransTime);
			}
			flag_record=loanPayPlanMapper.updateByPrimaryKeySelective(loanPayPlan);
			if(flag_record<1){
				throw new RuntimeException("更新还款计划失败!");
			}
			//查询还款记录 已还本金、利息金额
			LoanPayRecord loanPayRecord_query_total=new LoanPayRecord();
			loanPayRecord_query_total.setTransMainId(transMainId);
			//已还本金、利息金额汇总
			LoanPayRecord payRecord_total=loanPayRecordMapper.queryPaysum(loanPayRecord_query_total);
			BigDecimal paidTotal=new BigDecimal(0);
			BigDecimal repaymentTotal=new BigDecimal(0);
			//已付本金总额
			BigDecimal paidCapitalTotal=new BigDecimal(0);
			BigDecimal repaymentCapitalTotal=new BigDecimal(0);
			
			if(payRecord_total!=null&&payRecord_total.getPaidTotal()!=null){
				paidTotal=payRecord_total.getPaidTotal();
			}
			//已付本金总额
			if(payRecord_total!=null&&payRecord_total.getPaidCapital()!=null){
				paidCapitalTotal=payRecord_total.getPaidCapital();
			}
			
			//更新放款主表
			
			//查询还款计划汇总应还本金和利息
			//已还本金+已还利息>=应还本金+利息
			LoanPayPlan plan_qury=new LoanPayPlan();
			plan_qury.setTransMainId(transMainId);
			LoanPayPlan plan_total=loanPayPlanMapper.queryPayPlansum(plan_qury);
			if(plan_total!=null&&plan_total.getRepaymentTotal()!=null){
				repaymentTotal=plan_total.getRepaymentTotal();
			}
			//应付本金总额
			if(plan_total!=null&&plan_total.getRepaymentCapital()!=null){
				repaymentCapitalTotal=plan_total.getRepaymentCapital();
			}
			//更新主表实体
			LoanTransMain loanTransMain_total=new LoanTransMain();
			loanTransMain_total.setLastModifyTime(loanTransMain.getLastModifyTime());
			loanTransMain_total.setTransMainId(transMainId);
			//设置已还本金、利息、罚息、违约金、已收息差
			loanTransMain_total.setPaidCapitalAmount(payRecord_total.getPaidCapital());
			loanTransMain_total.setPaidInterestAmount(payRecord_total.getPaidInterest());
			loanTransMain_total.setPaidOverdueAmount(payRecord_total.getPaidOverdue());
			BigDecimal paidCompensate_=payRecord_total.getPaidCompensate();
			loanTransMain_total.setPaidCompensateAmount(paidCompensate_);
			loanTransMain_total.setInterestSpreadAmount(payRecord_total.getInterestSpread());
			//如果当期结清设置结清期数
			if(SystemConst.Status.STATUS90.equals(loanPayPlan.getStatus())){
				loanTransMain_total.setPaidPeriod(loanPayPlan.getPeriod());
			}
			//实际还款日在贷款截止日之后设置逾期标识
			if(DateUtils.daysBetween(actualTransTime,over_date)<0){
				loanTransMain_total.setIsOverdue(SystemConst.IsFlag.YES);
				//提前还款
			}

			//提前还款
			//结清日期在借款截至日之前已还总额大于应还总额并且，已还本金大于等于借款金额
			//结清和提前还款状态
			if(paidTotal.compareTo(repaymentTotal)!=-1&&paidCapitalTotal.compareTo(loanTransMain.getAmount())!=-1){
				loanTransMain_total.setCleanUpTime(actualTransTime);
				//结清日期在借款截止日之后 逾期结清
				if(DateUtils.daysBetween(actualTransTime,over_date)<0){
					loanTransMain_total.setCleanUpStatus(SystemConst.Status.STATUS89);
					loanPayRecord_insert.setTransType(SystemConst.Status.STATUS89);
					//提前还款
				}else if(DateUtils.daysBetween(actualTransTime,over_date)>0){
					loanTransMain_total.setCleanUpStatus(SystemConst.Status.STATUS88);
					loanPayRecord_insert.setTransType(SystemConst.Status.STATUS88);
					//等于正常还款
				}else{
					loanTransMain_total.setCleanUpStatus(SystemConst.Status.STATUS90);
				}
				
			}
			loanPayRecordMapper.updateByPrimaryKeySelective(loanPayRecord_insert);
			flag_record=loanTransMainMapper.updateByPrimaryKeySelective(loanTransMain_total);
			if(flag_record<1){
				throw new RuntimeException("更新放款主记录失败");
			}
			
			recode=SystemConst.SUCCESS;
	
		}
		

		return recode;
	}


	@Override
	@Transactional(readOnly = false)
	public String loanPayCompanyTrans(LoanPayRecordCompany loanPayRecord) {
		String recode=SystemConst.FAIL;
		if(loanPayRecord==null||StringUtils.isEmpty(loanPayRecord.getPlanId())||loanPayRecord.getPaidTotal()==null){
			throw new RuntimeException("付款主要数据不能为空！");
		}
		
		//还款计划ID
		Long planId=loanPayRecord.getPlanId();
		//当前期数
		Short period=-1;
		//本次还款总额
	    //BigDecimal paid_amount=new BigDecimal(0);
		//应还本金总额
	    BigDecimal repaymentCapital=new BigDecimal(0);
	    //还款本金
		BigDecimal paidCapital=new BigDecimal(0);
		//还款利息
	    BigDecimal paidInterest=new BigDecimal(0);
		//逾期罚息
	    BigDecimal paidOverdue=new BigDecimal(0);
		//违约金
	    BigDecimal paidCompensate=new BigDecimal(0);
	    //本次还款总额
	    BigDecimal paid_total=new BigDecimal(0);
	    
	    //还款时间
	    Date actualTransTime=loanPayRecord.getActualTransTime();
	    //所有投资人应收总额
	    //BigDecimal repayment_period_all_total=new BigDecimal(0);
	    
	    if(loanPayRecord.getPaidCapital()!=null){
	    	paidCapital=loanPayRecord.getPaidCapital();
	    	paid_total=paid_total.add(loanPayRecord.getPaidCapital());
	    }
	    if(loanPayRecord.getPaidInterest()!=null){
	    	paidInterest=loanPayRecord.getPaidInterest();
	    	paid_total=paid_total.add(loanPayRecord.getPaidInterest());
	    }
	    if(loanPayRecord.getPaidOverdue()!=null){
	    	paidOverdue=loanPayRecord.getPaidOverdue();
	    	paid_total=paid_total.add(loanPayRecord.getPaidOverdue());
	    }
	    if(loanPayRecord.getPaidCompensate()!=null){
	    	paidCompensate=loanPayRecord.getPaidCompensate();
	    	paid_total=paid_total.add(loanPayRecord.getPaidCompensate());
	    }
	    //已还本金总额
	    BigDecimal paidCapital_total=new BigDecimal(0);
	    //已还款利息总额
	    BigDecimal paidInterest_total=new BigDecimal(0);
	    //已还逾期罚息
	    BigDecimal paidOverdue_total=new BigDecimal(0);
	    //已还违约金
	    BigDecimal paidCompensate_total=new BigDecimal(0);
	    //已收罚息息差
	    //BigDecimal interestSpread_total=new BigDecimal(0);
	    //应付利息总额
	    //BigDecimal repaymentInterest_total=new BigDecimal(0);
	    //罚息息差
	    BigDecimal interest_spread=new BigDecimal(0);
		
		//查询付款计划
		LoanPayPlanCompany loanPayPlan=loanPayPlanCompanyMapper.selectByPrimaryKey(planId.toString());
		period=loanPayPlan.getPeriod();
		if(loanPayPlan==null||StringUtils.isEmpty(loanPayPlan.getId())){
			throw new RuntimeException("没查询到需要还款的记录!");
		}
		String transMainId=loanPayPlan.getTransMainId();
		//查询放款账务主记录
		LoanTransMain loanTransMain =loanTransMainMapper.selectByPrimaryKey(transMainId);
		if(loanTransMain==null||StringUtils.isEmpty(loanTransMain.getTransMainId())){
			throw new RuntimeException("没查询到放款主记录!");
		}
		if(loanPayPlan.getRepaymentCapital()!=null){
			repaymentCapital=loanPayPlan.getRepaymentCapital();
		}
		//如果是投资交易付款，校验本次付款总额小于等于已还总额
	    if(SystemConst.TransType.TYPEID1001.equals(loanTransMain.getTransType())){
			BigDecimal amountIn=new BigDecimal(0);
			BigDecimal amountOut=new BigDecimal(0);
			LoanFreezeMain trans_query=new LoanFreezeMain();
			trans_query.setBusinessId(loanTransMain.getBusinessId());
			trans_query.setTransType(SystemConst.TransType.TYPEID1011);
			trans_query.setIsFlag(SystemConst.IsFlag.YES);
			trans_query.setStatus(SystemConst.Status.STATUS03);
			
			List<LoanFreezeMain> trans_query_list=loanFreezeMainMapper.queryList(trans_query);
			
			if(trans_query_list!=null&&trans_query_list.size()>0){
				LoanFreezeMain loanFreezeMain=trans_query_list.get(0);
				 //在途金额=已还本金+已还利息+已还逾期罚息+已还违约金-息差-已付本金-已付利息-已付逾期罚息-已付违约金-罚息息差
				//BigDecimal paidAmount_=loanTransMain.getPaidAmount();
				//BigDecimal interestSpreadAmount_=loanTransMain.getInterestSpreadAmount();
				//BigDecimal investAmount_=loanTransMain.getInvestAmount();
				//BigDecimal overdueInterestSpreadAmount_=loanTransMain.getOverdueInterestSpreadAmount();

				if(loanFreezeMain.getAmountIn()!=null){
					amountIn=loanFreezeMain.getAmountIn();
				}
				if(loanFreezeMain.getAmountOut()!=null){
					amountOut=loanFreezeMain.getAmountOut();
				}
			}

		    BigDecimal ontheway_amount_total=amountIn.subtract(amountOut);
		    //如果是投资交易付款，校验本次付款总额小于等于已还总额
		    if(paid_total.compareTo(ontheway_amount_total)==1){
				throw new RuntimeException("本次已付总额大于借款人已还总额，不能进行付款。");
			}
	    }

	    //总期数
//		Short period_total=loanTransMain.getPeriodTotal();
		//借款到期日
		Date over_date=loanTransMain.getOverTime();
		//查询未还的还款计划首条还款计划 如果点前期有未还的，不能进行还款

		//查询付款计划
		//LoanPayPlanCompany loanPayPlanCompany_query=new LoanPayPlanCompany();
		//loanPayPlanCompany_query.setTransMainId(transMainId);
		//loanPayPlanCompany_query.setPeriod(period);
		//repaymentCapital=loanPayPlan.getRepaymentCapital();
		Date repayment_capital_time =loanPayPlan.getRepaymentCapitalTime();
		Date repayment_interest_time=loanPayPlan.getRepaymentInterestTime();
		int overdueDays=0;
		
		if(repayment_capital_time!=null){
			overdueDays=DateUtils.daysBetween(repayment_capital_time, actualTransTime);
		}
		if(repayment_interest_time!=null){
			int dayNum_temp=DateUtils.daysBetween(repayment_interest_time, actualTransTime);
			if(dayNum_temp>overdueDays)overdueDays=dayNum_temp;
		}
		
		//还款计划类型
		//还款计划类型
		String plan_type=loanPayPlan.getTransType();
		if(overdueDays>0){
			plan_type=SystemConst.Status.STATUS89;
		}
		if(overdueDays<0)overdueDays=0;
		
		LoanPayRecordCompany loanPayRecord_query=new LoanPayRecordCompany();
		//查询还款记录
		loanPayRecord_query.setTransMainId(transMainId);
		loanPayRecord_query.setPeriod(period);
		//投资人的账号ID
		loanPayRecord_query.setAccountId(loanPayPlan.getAccountId());
		//已还本金、利息金额汇总
		LoanPayRecordCompany payRecord=loanPayRecordCompanyMapper.queryPaysum(loanPayRecord_query);
		if(payRecord!=null){
			//已还利息总额
			if(payRecord.getPaidInterest()!=null)
				paidInterest_total=payRecord.getPaidInterest();
			//已还本金总额
			if(payRecord.getPaidCapital()!=null)
				paidCapital_total=payRecord.getPaidCapital();
			//已还逾期罚息
			if(payRecord.getPaidOverdue()!=null)
			paidOverdue_total=payRecord.getPaidOverdue();
			//已还违约金
			if(payRecord.getPaidCompensate()!=null)
			paidCompensate_total=payRecord.getPaidCompensate();
			//已收罚息息差违约金
			//if(payRecord.getInterestSpread()!=null)
			//interestSpread_total=payRecord.getInterestSpread();

		}
		if(paidCapital.compareTo(new BigDecimal(0))==1
				&&repaymentCapital.compareTo(paidCapital.add(paidCapital_total))==-1){
			throw new RuntimeException("还本金额不能大于剩余应还本金!");
	    }
		/**
		//有提前还本，对多还本金不处理
		if(paidCapital_total.add(paidCapital).compareTo(repaymentCapital)==1){
			
			//本次还款多余的本金
			BigDecimal overpaidCapital=paidCapital_total.add(paidCapital).subtract(repaymentCapital);
			//多余的本金放到利息中
			paidInterest=paidInterest.add(overpaidCapital);
			//本次应还本金=本次还款本金-本次还款多余的本金
			paidCapital=paidCapital.subtract(overpaidCapital);
		}
		**/
		//待插入付款记录对象
		LoanPayRecordCompany loanPayRecord_insert=new LoanPayRecordCompany();
		loanPayRecord_insert.setPaidTotal(paid_total);
		String tranId=GetUniqueNoUtil.getCustNo();
		//复制loanPayPlan属性
		BeanUtils.copyProperties(loanPayPlan,loanPayRecord_insert);
		loanPayRecord_insert.setActualTransTime(loanPayRecord.getActualTransTime());
		//设置操作人员信息
		loanPayRecord_insert.setLastUser(loanPayRecord.getLastUser());
		loanPayRecord_insert.setLastModifyTime(new Date());
		loanPayRecord_insert.setCreateTime(new Date());
		loanPayRecord_insert.setCreateUser(loanPayRecord.getLastUser());
		//还款类型
		loanPayRecord_insert.setTransType(plan_type);
		//记录状态 正常
		loanPayRecord_insert.setStatus(SystemConst.Status.STATUS03);
		loanPayRecord_insert.setId(tranId);
		loanPayRecord_insert.setPlanId(planId);
		loanPayRecord_insert.setTransMainId(transMainId);
		//设置公司、集团
		loanPayRecord_insert.setCompanyId(loanTransMain.getCompanyId());
		loanPayRecord_insert.setUnionId(loanTransMain.getUnionId());
		
		//还本金
		loanPayRecord_insert.setPaidCapital(paidCapital);
		//利息
		loanPayRecord_insert.setPaidInterest(paidInterest);
		//逾期
		loanPayRecord_insert.setPaidOverdue(paidOverdue);
		//违约
		loanPayRecord_insert.setPaidCompensate(paidCompensate);
		
		//如果还款利息大于0，表示还息
		if(paidInterest.compareTo(new BigDecimal(0))==1){
			//设置还息时间
			loanPayRecord_insert.setInterestTime(loanPayRecord.getActualTransTime());
		}
		
		if(paidCapital.compareTo(new BigDecimal(0))==1){
			//设置还本时间
			loanPayRecord_insert.setCapitalTime(loanPayRecord.getActualTransTime());
		}

		int flag_record=loanPayRecordCompanyMapper.insertSelective(loanPayRecord_insert);

		//投资账户记账交易
		if(flag_record>0){
			LoanAccountsTrans trans_temp=new LoanAccountsTrans();
			//复制loanPayPlan属性
			BeanUtils.copyProperties(loanPayRecord_insert,trans_temp);
			//设置实际付款时间
			trans_temp.setTransTime(loanPayRecord_insert.getActualTransTime());
			//设置本次释放的在途金额=本次还款总额
			trans_temp.setOnTheWayAmount(paid_total);
			trans_temp.setTransType(SystemConst.TransType.TYPEID1003);
			//业务单号
			trans_temp.setOrderNo(loanTransMain.getBusinessId());
			//根据放款主表的交易类型设置业务类型
			if(SystemConst.TransType.TYPEID1001.equals(loanTransMain.getTransType())){
				trans_temp.setBusinessType(SystemConst.BusinessType.INVEST);
			}else if(SystemConst.TransType.TYPEID1007.equals(loanTransMain.getTransType())){
				trans_temp.setBusinessType(SystemConst.BusinessType.FINANCING);
			}
			
			//还款记录ID
			trans_temp.setBusinessId(tranId);
			//设置省市
			trans_temp.setProvienceNo(loanTransMain.getProductNo());
			trans_temp.setProvienceName(loanTransMain.getProvienceName());
			trans_temp.setCityNo(loanTransMain.getCityNo());
			trans_temp.setCityName(loanTransMain.getCityName());
			trans_temp.setOwnerId(loanPayRecord_insert.getCustomerId());
			trans_temp.setOwnerName(loanPayRecord_insert.getCustomerName());
			
			//投资人入账、释放在途金额
			recode=loanActPayTrans(trans_temp,loanTransMain,loanPayRecord);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("投资人入账、释放在途金额交易失败!");
			}

			//更新还款计划
			//更新已还本金、已还利息
			loanPayPlan.setPaidCapital(paidCapital_total.add(paidCapital));
			loanPayPlan.setPaidInterest(paidInterest_total.add(paidInterest));
			loanPayPlan.setPaidOverdue(paidOverdue_total.add(paidOverdue));
			loanPayPlan.setPaidCompensate(paidCompensate_total.add(paidCompensate));
			//loanPayPlan.setInterestSpread(interestSpread_total.add(interest_spread));
			

			//如果已还本息总额大于等于应还本息总额设置还款状态为完成
			if(loanPayPlan.getRepaymentCapital().compareTo(paidCapital_total.add(paidCapital))!=1
					&&loanPayPlan.getRepaymentInterest().compareTo(paidInterest_total.add(paidInterest))!=1){
				loanPayPlan.setStatus(SystemConst.Status.STATUS90);
				
			}
			//设置还本、还息时间
			if(paidCapital.compareTo(new BigDecimal(0))==1){
				loanPayPlan.setCapitalTime(actualTransTime);
			}
			if(paidInterest.compareTo(new BigDecimal(0))==1){
				loanPayPlan.setInterestTime(actualTransTime);
			}
			loanPayPlanCompanyMapper.updateByPrimaryKeySelective(loanPayPlan);
			
			//查询还款记录 已还本金、利息金额、逾期、违约
			LoanPayRecordCompany loanPayRecord_query_total=new LoanPayRecordCompany();
			loanPayRecord_query_total.setTransMainId(transMainId);
			//已付本金、利息金额汇总
			LoanPayRecordCompany payRecord_total=loanPayRecordCompanyMapper.queryPaysum(loanPayRecord_query_total);
			
			//查询还款记录 已还本金、利息金额、逾期、违约
			LoanPayPlanCompany play_period_query_total=new LoanPayPlanCompany();
			play_period_query_total.setTransMainId(transMainId);
			play_period_query_total.setPeriod(period);
			play_period_query_total.setStatus(SystemConst.Status.STATUS03);
			//查询付款计划列表 剩余条数
			int query_count=loanPayPlanCompanyMapper.queryCount(play_period_query_total);

			BigDecimal paidTotal=new BigDecimal(0);
			
			BigDecimal repaymentTotal=new BigDecimal(0);
			BigDecimal paid_all_total=new BigDecimal(0);
			//已付本金总额
			BigDecimal paidCapitalTotal=new BigDecimal(0);
			BigDecimal repaymentCapitalTotal=new BigDecimal(0);
			//已付本息总额
			if(payRecord_total!=null&&payRecord_total.getPaidTotal()!=null){
				paidTotal=payRecord_total.getPaidTotal();
			}
			//已付本金总额
			if(payRecord_total!=null&&payRecord_total.getPaidCapital()!=null){
				paidCapitalTotal=payRecord_total.getPaidCapital();
			}
			//已付本息、罚息、违约金总额
			if(payRecord_total!=null&&payRecord_total.getPaidAllTotal()!=null){
				paid_all_total=payRecord_total.getPaidAllTotal();
			}
			//更新放款主表
			LoanTransMain loanTransMain_total=new LoanTransMain();
			loanTransMain_total.setLastModifyTime(loanTransMain.getLastModifyTime());
			loanTransMain_total.setTransMainId(transMainId);
			//查询还款计划汇总应还本金和利息
			//已还本金+已还利息>=应还本金+利息
			LoanPayPlanCompany plan_qury=new LoanPayPlanCompany();
			plan_qury.setTransMainId(transMainId);
			LoanPayPlanCompany plan_total=loanPayPlanCompanyMapper.queryPayPlansum(plan_qury);
			//应付本息总额
			if(plan_total!=null&&plan_total.getRepaymentTotal()!=null){
				repaymentTotal=plan_total.getRepaymentTotal();
			}
			//应付本金总额
			if(plan_total!=null&&plan_total.getRepaymentCapital()!=null){
				repaymentCapitalTotal=plan_total.getRepaymentCapital();
			}
			//当前期数等于总期数、当前结清
			//提前还款
			//结清日期在借款截至日之前
			//结清和提前还款状态
			 //在途金额=已还本金+已还利息+已还逾期罚息+已还违约金-息差-已付本金-已付利息-已付逾期罚息-已付违约金
		    //BigDecimal ontheway_amount_total=loanTransMain.getPaidAmount().subtract(loanTransMain.getInterestSpreadAmount()).subtract(loanTransMain.getInvestAmount());
			if(paidTotal.compareTo(repaymentTotal)!=-1&&paidCapitalTotal.compareTo(loanTransMain.getAmount())!=-1){
				//计算息差=在途剩余金额
				//已付清
				//息差=已还总金额-已收息差-已付结清总额=在途剩余金额
				interest_spread=loanTransMain.getPaidAmount().subtract(loanTransMain.getInterestSpreadAmount()).subtract(paid_all_total).subtract(loanTransMain.getOverdueInterestSpreadAmount());
				
				loanTransMain_total.setPayOffTime(actualTransTime);
				//结清日期在借款截止日之后 逾期结清
				if(DateUtils.daysBetween(actualTransTime,over_date)<0){
					loanTransMain_total.setStatus(SystemConst.Status.STATUS89);
					loanPayRecord_insert.setTransType(SystemConst.Status.STATUS89);
					//提前还款
				}else if(DateUtils.daysBetween(actualTransTime,over_date)>0){
					loanTransMain_total.setStatus(SystemConst.Status.STATUS88);
					loanPayRecord_insert.setTransType(SystemConst.Status.STATUS88);
					//等于正常还款
				}else{
					loanTransMain_total.setStatus(SystemConst.Status.STATUS90);
				}
				
			}
			
			//记息差
			if(interest_spread!=null&&interest_spread.compareTo(new BigDecimal(0))==1){
				trans_temp.setInterestSpread(interest_spread);
				trans_temp.setCompanyId(loanTransMain.getCompanyId());
				//记逾期息差
				recode=accountInterestSpreadTrans(trans_temp);
				if(SystemConst.FAIL.equals(recode)){
					throw new RuntimeException("记入公司逾期息差交易失败!");
				}
				//更新付款记录逾期息差值
				loanPayRecord_insert.setInterestSpread(interest_spread);
				//loanPayRecordCompanyMapper.updateByPrimaryKeySelective(loanPayRecord_insert);
				//记在途金额
				trans_temp.setOnTheWayAmount(interest_spread);
				recode=accountOnTheWayTrans(trans_temp,loanTransMain);
				if(SystemConst.FAIL.equals(recode)){
					throw new RuntimeException("投资人入账、释放在途金额交易失败!");
				}
				
				
			}
			//如果当期无未结清记录设置主记录已付期数
			if(query_count==0){
				loanTransMain_total.setInvestPeriod(loanPayPlan.getPeriod());
			}
			//设置已还本金、利息、罚息、违约金、已收息差
			loanTransMain_total.setInvestCapitalAmount(payRecord_total.getPaidCapital());
			loanTransMain_total.setInvestInterestAmount(payRecord_total.getPaidInterest());
			loanTransMain_total.setInvestOverdueAmount(payRecord_total.getPaidOverdue());
			loanTransMain_total.setInvestCompensateAmount(payRecord_total.getPaidCompensate());
			loanTransMain_total.setOverdueInterestSpreadAmount(payRecord_total.getInterestSpread().add(interest_spread));
			
			loanPayRecordCompanyMapper.updateByPrimaryKeySelective(loanPayRecord_insert);
			flag_record=loanTransMainMapper.updateByPrimaryKeySelective(loanTransMain_total);
			if(flag_record<1){
				throw new RuntimeException("更新放款主表失败!");
			}
			recode=SystemConst.SUCCESS;
			
		}

		return recode;
	}
	/**
	 * 还款、付款借款人、投资人记账交易、根据基本交易信息、放款对象，付款/还款记录
	 * @param trans_temp
	 * @param amount
	 * @param subjectId
	 * @return
	 * @since JDK 1.8
	 */
	private String loanActPayTrans(LoanAccountsTrans trans_temp,LoanTransMain loanTransMain,Object loanRecord){
		
		String recode=SystemConst.FAIL;	
		//交易总额
		BigDecimal paid_amount=new BigDecimal(0);
		//在途金额
		BigDecimal ontheway_amount=new BigDecimal(0);
	    //还款本金
	    BigDecimal paidCapital=new BigDecimal(0);
	    //还款利息
	    BigDecimal paidInterest=new BigDecimal(0);
	    //逾期罚息
	    BigDecimal paidOverdue=new BigDecimal(0);
	    //违约金
	    BigDecimal paidCompensate=new BigDecimal(0);
	    //投资付款交易
		if (SystemConst.TransType.TYPEID1003.equals(trans_temp.getTransType())&&loanRecord instanceof LoanPayRecordCompany){
			
			LoanPayRecordCompany loanPayRecord=(LoanPayRecordCompany)loanRecord;
			//还款本金
		    paidCapital=loanPayRecord.getPaidCapital();
		    //还款利息
		    paidInterest=loanPayRecord.getPaidInterest();
		    //逾期罚息
		    paidOverdue=loanPayRecord.getPaidOverdue();
		    //违约金
		    paidCompensate=loanPayRecord.getPaidCompensate();
		    if(paidCapital!=null){
		    	paid_amount=paid_amount.add(paidCapital);
		    }
		    if(paidInterest!=null){
		    	paid_amount=paid_amount.add(paidInterest);
		    }
		    if(paidOverdue!=null){
		    	paid_amount=paid_amount.add(paidOverdue);
		    }
		    if(paidCompensate!=null){
		    	paid_amount=paid_amount.add(paidCompensate);
		    }
		    ontheway_amount=paid_amount;
		    if(SystemConst.BusinessType.INVEST.equals(trans_temp.getBusinessType())){
				//付款总额大于0，释放在途金额，付款总额小于0增加在途金额
				if(ontheway_amount!=null&&ontheway_amount.compareTo(new BigDecimal(0))!=0){
					
					if(ontheway_amount.compareTo(new BigDecimal(0))==1){
						trans_temp.setOnTheWayAmount(ontheway_amount);
						recode=accountOnTheWayTrans(trans_temp,loanTransMain);
					}else{
						LoanAccountsTrans trans_insert=new LoanAccountsTrans();
						//复制loanPayPlan属性
						BeanUtils.copyProperties(trans_temp,trans_insert);
						trans_insert.setAmount(ontheway_amount.multiply(new BigDecimal(-1)));
						trans_insert.setTransType(SystemConst.TransType.TYPEID1011);
						//设置在途科目
						trans_insert.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_TRANSITTOTAL);
						//在途记账需要优化，支持同一单号多次
						trans_insert.setUpdateFlag(SystemConst.IsFlag.YES);
						recode=accountFreezeTrans(trans_insert);
					}
					
				}
		    	//如果还款本金大于0记本金出账
				if(paidCapital!=null&&paidCapital.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_temp,paidCapital,SystemConst.SubjectInfo.SUBJECTID_INVEST_CAPITAL_IN);
				}
				//利息大于0，利息还款
				if(paidInterest!=null&&paidInterest.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_temp,paidInterest,SystemConst.SubjectInfo.SUBJECTID_INVEST_INTEREST_IN);
					//利息小于0，回款利息退款支出
				}else if(paidInterest!=null&&paidInterest.compareTo(new BigDecimal(0))==-1){
					recode=accountChangeTrans(trans_temp,paidInterest.multiply(new BigDecimal(-1)),SystemConst.SubjectInfo.SUBJECTID_INVEST_INTEREST_OUT);
				}
				//记提前还款违约金
				if(paidCompensate!=null&&paidCompensate.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_temp,paidCompensate,SystemConst.SubjectInfo.SUBJECTID_INVEST_COMPENSATE_IN);
				}
				//记逾期罚息
				if(paidOverdue!=null&&paidOverdue.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_temp,paidOverdue,SystemConst.SubjectInfo.SUBJECTID_INVEST_OVERDUE_INTEREST_IN);
				}
			//固定理财交易付款
		    }else if(SystemConst.BusinessType.FINANCING.equals(trans_temp.getBusinessType())){
		    	//公司出账
		    	LoanAccountsTrans trans_insert=new LoanAccountsTrans();
				//复制loanPayPlan属性
				BeanUtils.copyProperties(trans_temp,trans_insert);
				trans_insert.setOwnerId(loanTransMain.getBorrowerId());
				trans_insert.setOwnerName(loanTransMain.getBorrower());
				trans_insert.setAccountId(loanTransMain.getBorrowerAccountId());
				//如果还款本金大于0记本金出账
				if(paidCapital!=null&&paidCapital.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_insert,paidCapital,SystemConst.SubjectInfo.SUBJECTID_FINANCING_CAPITAL_OUT);
				}
				//记利息还款
				if(paidInterest!=null&&paidInterest.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_insert,paidInterest,SystemConst.SubjectInfo.SUBJECTID_FINANCING_INTEREST_OUT);
				//理财利息退款收入
				}else if(paidInterest!=null&&paidInterest.compareTo(new BigDecimal(0))==-1){
					recode=accountChangeTrans(trans_insert,paidInterest.multiply(new BigDecimal(-1)),SystemConst.SubjectInfo.SUBJECTID_FINANCING_INTEREST_REFUND_IN);
				}
				//记提前还款违约金
				if(paidCompensate!=null&&paidCompensate.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_insert,paidCompensate,SystemConst.SubjectInfo.SUBJECTID_FINANCING_COMPENSATE_OUT);
				}
				//记逾期罚息
				if(paidOverdue!=null&&paidOverdue.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_insert,paidOverdue,SystemConst.SubjectInfo.SUBJECTID_FINANCING_OVERDUE_INTEREST_OUT);
				}

		    	//投资人入账
		    	//如果还款本金大于0记本金出账
				if(paidCapital!=null&&paidCapital.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_temp,paidCapital,SystemConst.SubjectInfo.SUBJECTID_FINANCING_CAPITAL_IN);
				}
				//记利息还款
				if(paidInterest!=null&&paidInterest.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_temp,paidInterest,SystemConst.SubjectInfo.SUBJECTID_FINANCING_INTEREST_IN);
					//理财利息退款支出
				}else if(paidInterest!=null&&paidInterest.compareTo(new BigDecimal(0))==-1){
					/**理财利息退款支出**/	
					recode=accountChangeTrans(trans_temp,paidInterest,SystemConst.SubjectInfo.SUBJECTID_FINANCING_INTEREST_REFUND_OUT);
				}
				//记提前还款违约金
				if(paidCompensate!=null&&paidCompensate.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_temp,paidCompensate,SystemConst.SubjectInfo.SUBJECTID_FINANCING_COMPENSATE_IN);
				}
				//记逾期罚息
				if(paidOverdue!=null&&paidOverdue.compareTo(new BigDecimal(0))==1){
					recode=accountChangeTrans(trans_temp,paidOverdue,SystemConst.SubjectInfo.SUBJECTID_FINANCING_OVERDUE_INTEREST_IN);
				}
		    }
			//还款交易
		}else if(SystemConst.TransType.TYPEID1002.equals(trans_temp.getTransType())&&loanRecord instanceof LoanPayRecord){
			ontheway_amount=trans_temp.getOnTheWayAmount();
			LoanPayRecord loanPayRecord=(LoanPayRecord)loanRecord;
			//还款本金
		    paidCapital=loanPayRecord.getPaidCapital();
		    //还款利息
		    paidInterest=loanPayRecord.getPaidInterest();
		    //逾期罚息
		    paidOverdue=loanPayRecord.getPaidOverdue();
		    //违约金
		    paidCompensate=loanPayRecord.getPaidCompensate();
			//如果还款本金大于0记本金出账
			if(paidCapital!=null&&paidCapital.compareTo(new BigDecimal(0))==1){
				recode=accountChangeTrans(trans_temp,paidCapital,SystemConst.SubjectInfo.SUBJECTID_REPAY_CAPITAL_OUT);
			}
			//记利息还款
			if(paidInterest!=null&&paidInterest.compareTo(new BigDecimal(0))==1){
				recode=accountChangeTrans(trans_temp,paidInterest,SystemConst.SubjectInfo.SUBJECTID_REPAY_INTEREST_OUT);
			//利息还款退款收入
			}else if(paidInterest!=null&&paidInterest.compareTo(new BigDecimal(0))==-1){
				recode=accountChangeTrans(trans_temp,paidInterest.multiply(new BigDecimal(-1)),SystemConst.SubjectInfo.SUBJECTID_REPAY_INTEREST_IN);
			}
			//记逾期罚息
			if(paidOverdue!=null&&paidOverdue.compareTo(new BigDecimal(0))==1){
				recode=accountChangeTrans(trans_temp,paidOverdue,SystemConst.SubjectInfo.SUBJECTID_REPAY_OVERDUE_INTEREST_OUT);
			}
			//记提前还款违约金
			if(paidCompensate!=null&&paidCompensate.compareTo(new BigDecimal(0))==1){
				recode=accountChangeTrans(trans_temp,paidCompensate,SystemConst.SubjectInfo.SUBJECTID_REPAY_COMPENSATE_OUT);
			}
			//记在途金额
			if(ontheway_amount!=null&&ontheway_amount.compareTo(new BigDecimal(0))!=0){
				
				LoanAccountsTrans trans_insert=new LoanAccountsTrans();
				//复制loanPayPlan属性
				BeanUtils.copyProperties(trans_temp,trans_insert);
				//如果在途金额大于0新增在途金额
				if(ontheway_amount.compareTo(new BigDecimal(0))==1){
					trans_insert.setAmount(ontheway_amount);
					trans_insert.setTransType(SystemConst.TransType.TYPEID1011);
					//设置在途科目
					trans_insert.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_TRANSITTOTAL);
					//在途记账需要优化，支持同一单号多次
					trans_insert.setUpdateFlag(SystemConst.IsFlag.YES);
					recode=accountFreezeTrans(trans_insert);
					//如果在途金额小于0释放在途金额
				}else{
					trans_insert.setOrderNo(loanTransMain.getBorrowerId());
					trans_insert.setAmount(ontheway_amount.multiply(new BigDecimal(-1)));
					recode=accountOnTheWayTrans(trans_insert,loanTransMain);
				}
				
				
			}
		}

		return recode;
	}
	/**
	 * 根据基本交易信息、交易金额、科目记账
	 * @param trans_temp
	 * @param amount
	 * @param subjectId
	 * @return
	 * @since JDK 1.8
	 */
	private String accountChangeTrans(LoanAccountsTrans trans_temp,BigDecimal amount,String subjectId){
		LoanAccountsTrans trans_insert=new LoanAccountsTrans();
		//复制loanPayPlan属性
		BeanUtils.copyProperties(trans_temp,trans_insert);
		trans_insert.setAmount(amount);
		//设置还款科目
		trans_insert.setSubjectId(subjectId);
		return accountChangeTrans(trans_insert);
	}
	/**
	 * 
	 * 释放在途金额
	 *
	 * @author lenovo
	 * @param trans_temp
	 * @return
	 * @since JDK 1.8
	 */
	private String accountOnTheWayTrans(LoanAccountsTrans trans_temp,LoanTransMain loanTransMain){
		String recode=SystemConst.FAIL;
		BigDecimal ontheway_amount =trans_temp.getOnTheWayAmount();
		//批量解冻，业务申请号不能为空
		if(trans_temp==null ||StringUtils.isEmpty(trans_temp.getOrderNo())){
			throw new RuntimeException("解冻，业务申请号不能为空!");
		}
		LoanFreezeMain trans_query=new LoanFreezeMain();
		trans_query.setBusinessId(trans_temp.getOrderNo());
		trans_query.setTransType(SystemConst.TransType.TYPEID1011);
		trans_query.setIsFlag(SystemConst.IsFlag.YES);
		trans_query.setStatus(SystemConst.Status.STATUS03);
		
		List<LoanFreezeMain> trans_query_list=loanFreezeMainMapper.queryList(trans_query);
		if(trans_query_list==null||trans_query_list.size()<1){
			throw new RuntimeException("OrderNo"+trans_temp.getOrderNo()+",无待解记录!");
		}
		//记在途金额
		if(ontheway_amount!=null&&ontheway_amount.compareTo(new BigDecimal(0))==1){
			LoanAccountsTrans trans_insert=new LoanAccountsTrans();
			//复制loanPayPlan属性
			BeanUtils.copyProperties(trans_temp,trans_insert);
			trans_insert.setOldTransId(trans_query_list.get(0).getTransId());
			trans_insert.setOwnerId(loanTransMain.getBorrowerId());
			trans_insert.setOwnerName(loanTransMain.getBorrower());
			trans_insert.setAccountId(loanTransMain.getBorrowerAccountId());
			trans_insert.setAmount(ontheway_amount);
			trans_insert.setTransType(SystemConst.TransType.TYPEID1012);
			//设置在途科目
			trans_insert.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_TRANSITTOTAL);
			recode=accountFreezeTrans(trans_insert);
			
		}
		if(SystemConst.FAIL.equals(recode)){
			throw new RuntimeException("释放在途金额失败");
		}
		return recode;
	}
	/**
	 * 
	 * 
	 *
	 * @author lenovo
	 * @param trans_temp
	 * @return
	 * @since JDK 1.8
	 */
	private String accountInterestSpreadTrans(LoanAccountsTrans trans_temp){
		String recode=SystemConst.FAIL;
		if(trans_temp==null||StringUtils.isEmpty(trans_temp.getCompanyId())){
			throw new RuntimeException("accountChangeTrans error 动账交易错误：动账账号ID、交易金额不能为空");
			
		}
		BigDecimal interest_spread=trans_temp.getInterestSpread();
		//记息差
		if(interest_spread!=null&&interest_spread.compareTo(new BigDecimal(0))==1){
			LoanAccountInfo act_qury=new LoanAccountInfo();
			//设置公司编码
			act_qury.setOwnerId(trans_temp.getCompanyId());
			act_qury.setAccountType(SystemConst.AccountType.COMPANY);
			act_qury.setStatus(SystemConst.Status.STATUS03);
			List<LoanAccountInfo> list_act=loanAccountInfoMapper.queryList(act_qury);
			if(list_act==null||list_act.size()<1){
				throw new RuntimeException("没查询到公司对应的账号，不能做息差收入，请在公司账户配置!");
			}
			LoanAccountInfo act_company=list_act.get(0);
			LoanAccountsTrans trans_insert=new LoanAccountsTrans();
			//复制loanPayPlan属性
			BeanUtils.copyProperties(trans_temp,trans_insert);
			trans_insert.setOwnerId(act_company.getOwnerId());
			trans_insert.setOwnerName(act_company.getOwnerName());
			trans_insert.setAccountId(act_company.getAccountId());
			trans_insert.setAccountName(act_company.getOwnerName());
			trans_insert.setAmount(interest_spread);
			//设置息差科目
			trans_insert.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_REPAY_INTEREST_SPREAD_IN);
			recode=accountChangeTrans(trans_insert);
		}
		if(SystemConst.FAIL.equals(recode)){
			throw new RuntimeException("息差交易失败");
		}
		return recode;
	}
	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public String loanFeesTrans(LoanFeesRecord record) {
		String recode=SystemConst.FAIL;	
		if(record==null||record.getFeesId()==null ||record.getPaidAmount()==null){
			throw new RuntimeException("收款主要数据:feesId、transMainId、paidAmount不能为空！");
		}
		//已收、或者已支出总额
		BigDecimal paid_amount_total=new BigDecimal(0);
		//计划ID
		Long planId=record.getFeesId();
	    //还款金额
	    BigDecimal paidAmount=record.getPaidAmount();
	    
	    //还款时间
	    Date doTime=record.getDoTime();
	    //查询还款计划
	    LoanFeesPlan loanPayPlan=loanFeesPlanMapper.selectByPrimaryKey(planId.toString());
  		if(loanPayPlan==null||loanPayPlan.getFeesId()==null){
  			throw new RuntimeException("没查询到需要操作的收入支出计划!");
  		}
  		if(StringUtils.isEmpty(loanPayPlan.getTransMainId())){
  			throw new RuntimeException("收入支出计划缺少放款主表的transMainId!");
  		}
  		
  		/**
  		//检查是否已经处理过
  		LoanFeesRecord record_query=new LoanFeesRecord();
	    record_query.setFeesId(record.getFeesId());
	    record_query.setStatus(SystemConst.Status.STATUS03);
	    int record_count=loanFeesRecordMapper.queryCount(record_query);
	    if(record_count>0){
	    	throw new RuntimeException("编号为"+record.getFeesId()+"的收入支出计划已处理，不能重复处理!");
	    }
	    **/
  		//放款主记录ID
  		String transMainId=loanPayPlan.getTransMainId();
  		
		//查询放款账务主记录
		LoanTransMain loanTransMain =loanTransMainMapper.selectByPrimaryKey(transMainId);
		LoanAccountInfo act_qury=new LoanAccountInfo();
		//设置公司编码
		act_qury.setOwnerId(loanTransMain.getCompanyId());
		act_qury.setAccountType(SystemConst.AccountType.COMPANY);
		act_qury.setStatus(SystemConst.Status.STATUS03);
		List<LoanAccountInfo> list_act=loanAccountInfoMapper.queryList(act_qury);
		if(list_act==null||list_act.size()<1){
			throw new RuntimeException("没查询到公司对应的账号，不能做息差收入，请在公司账户配置!");
		}
		//平台公司账户信息
		LoanAccountInfo act_company=list_act.get(0);
		//平台支出-生成提现申请数据
		if(!"1".equals(record.getTransType()) && null!=loanPayPlan.getCardNo()){
			try {
				boolean flag=accountInOutInfoinsertData(record,loanPayPlan,loanTransMain);
				if(!flag){
					return recode;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return recode;
			}
		}
		//待插入收入支出记录
		LoanFeesRecord loanPayRecord_insert=new LoanFeesRecord();
		 //收款、支出
	    String kind_id=loanPayPlan.getTransType();
	    String tranId="";
	    if(SystemConst.InOutCome.IN.equals(kind_id)){
	    	tranId=GetUniqueNoUtil.getCustNo();
	    }else{
	    	tranId=GetUniqueNoUtil.getCustNo();
	    }
		//复制loanPayPlan属性
		BeanUtils.copyProperties(loanPayPlan,loanPayRecord_insert);
		loanPayRecord_insert.setDoTime(doTime);
		//设置操作人员信息
		loanPayRecord_insert.setLastUser(record.getLastUser());
		loanPayRecord_insert.setLastModifyTime(new Date());
		loanPayRecord_insert.setCreateTime(new Date());
		loanPayRecord_insert.setCreateUser(record.getLastUser());
		
		//记录状态失败
		loanPayRecord_insert.setStatus(SystemConst.Status.STATUS90);
		loanPayRecord_insert.setId(tranId);
		loanPayRecord_insert.setFeesId(record.getFeesId());
		loanPayRecord_insert.setTransMainId(transMainId);
		//设置公司、集团
		loanPayRecord_insert.setCompanyId(loanTransMain.getCompanyId());
		loanPayRecord_insert.setUnionId(loanTransMain.getUnionId());
		//还本金
		loanPayRecord_insert.setPaidAmount(paidAmount);
		int flag_record=loanFeesRecordMapper.insert(loanPayRecord_insert);

		//借款账户记账交易
		if(flag_record>0){
			LoanAccountsTrans trans_temp=new LoanAccountsTrans();
			//复制loanPayPlan属性
			BeanUtils.copyProperties(loanPayRecord_insert,trans_temp);
			//业务单号
			trans_temp.setOrderNo(loanTransMain.getBusinessId());
			trans_temp.setBusinessType(SystemConst.BusinessType.INVEST);
			//还款记录ID
			trans_temp.setBusinessId(tranId);
			//设置省市
			trans_temp.setProvienceNo(loanTransMain.getProductNo());
			trans_temp.setProvienceName(loanTransMain.getProvienceName());
			trans_temp.setCityNo(loanTransMain.getCityNo());
			trans_temp.setCityName(loanTransMain.getCityName());
			//trans_temp.setOwnerId(loanTransMain.getBorrowerId());
			//trans_temp.setOwnerName(loanTransMain.getBorrower());
			trans_temp.setAmount(paidAmount);
			trans_temp.setTransTime(doTime);
			if(SystemConst.InOutCome.IN.equals(kind_id)){
				
		    	trans_temp.setTransType(SystemConst.TransType.TYPEID1009);
		    	//借款人出账
				LoanAccountsTrans trans_insert_brow=new LoanAccountsTrans();
				//复制loanPayPlan属性
				BeanUtils.copyProperties(trans_temp,trans_insert_brow);
				trans_insert_brow.setOpponentAccountId(act_company.getAccountId());
				trans_insert_brow.setOpponentAccountName(act_company.getOwnerName());
				trans_insert_brow.setOwnerId(loanTransMain.getBorrowerId());
				trans_insert_brow.setOwnerName(loanTransMain.getBorrower());
				//根据支付方式设置科目
				//分期服务费还款
				if(SystemConst.transWay.MONTH.equals(loanPayPlan.getTransWay())){
					trans_insert_brow.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_REPAY_FEES_MONTH_OUT);
				}else{
					trans_insert_brow.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_REPAY_FEES_OUT);
				}
				recode=accountChangeTrans(trans_insert_brow);
				if(SystemConst.FAIL.equals(recode)){
					throw new RuntimeException("借款人账户"+trans_insert_brow.getAccountId()+"出账失败");
				}
				
				//入公司账
				LoanAccountsTrans trans_insert_c=new LoanAccountsTrans();
				//复制loanPayPlan属性
				BeanUtils.copyProperties(trans_temp,trans_insert_c);
				trans_insert_c.setOwnerId(act_company.getOwnerId());
				trans_insert_c.setOwnerName(act_company.getOwnerName());
				trans_insert_c.setAccountId(act_company.getAccountId());
				trans_insert_c.setAccountName(act_company.getOwnerName());
				trans_insert_c.setOpponentAccountId(loanPayPlan.getAccountId());
				trans_insert_c.setOpponentAccountName(loanTransMain.getBorrower());
				//设置科目
				trans_insert_c.setSubjectId(loanPayPlan.getSubjectId());
				
				recode=accountChangeTrans(trans_insert_c);
				if(SystemConst.FAIL.equals(recode)){
					throw new RuntimeException("公司账户"+trans_insert_c.getAccountId()+"入账失败");
				}
				//支出交易
		    }else{
		    	trans_temp.setTransType(SystemConst.TransType.TYPEID1010);
		    	//出公司账
				LoanAccountsTrans trans_insert_c=new LoanAccountsTrans();
				//复制loanPayPlan属性
				BeanUtils.copyProperties(trans_temp,trans_insert_c);
				trans_insert_c.setOwnerId(act_company.getOwnerId());
				trans_insert_c.setOwnerName(act_company.getOwnerName());
				trans_insert_c.setAccountId(act_company.getAccountId());
				trans_insert_c.setAccountName(act_company.getOwnerName());
				trans_insert_c.setOpponentAccountId(loanPayPlan.getAccountId());
				trans_insert_c.setOpponentAccountName(loanTransMain.getBorrower());
				//设置科目
				trans_insert_c.setSubjectId(loanPayPlan.getSubjectId());
				
				recode=accountChangeTrans(trans_insert_c);
				if(SystemConst.FAIL.equals(recode)){
					throw new RuntimeException("公司账户"+trans_insert_c.getAccountId()+"出账失败");
				}
		    	//如果支出的收款人客户账户不为空 入账处理
				if(!StringUtils.isEmpty(loanPayPlan.getAccountId())){
					//入账对象
					LoanAccountsTrans trans_insert_in=new LoanAccountsTrans();
					//复制loanPayPlan属性
					BeanUtils.copyProperties(trans_temp,trans_insert_in);
					trans_insert_in.setOpponentAccountId(act_company.getAccountId());
					trans_insert_in.setOpponentAccountName(act_company.getOwnerName());
					trans_insert_in.setOwnerId(loanTransMain.getBorrowerId());
					trans_insert_in.setOwnerName(loanTransMain.getBorrower());
					//设置科目
					trans_insert_in.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_REPAY_FEES_IN);
					recode=accountChangeTrans(trans_insert_in);
					if(SystemConst.FAIL.equals(recode)){
						throw new RuntimeException("支出入客户账户"+trans_insert_in.getAccountId()+"失败");
					}
				}
		    	
		    }
			
			//更新还款计划
			loanPayPlan.setStatus(SystemConst.Status.STATUS90);
			loanPayPlan.setDoTime(doTime);
			loanPayPlan.setPaidAmount(paidAmount);
			loanFeesPlanMapper.updateByPrimaryKeySelective(loanPayPlan);
			
			//查询收入支出记录
			LoanFeesRecord loanPayRecord_query=new LoanFeesRecord();
			
			loanPayRecord_query.setTransMainId(transMainId);
			loanPayRecord_query.setTransType(loanPayPlan.getTransType());
			//已还收入、或支出汇总
			LoanFeesRecord payRecord=loanFeesRecordMapper.queryPaysum(loanPayRecord_query);
			
			if(payRecord!=null&&payRecord.getPaidAmount()!=null){
				//已收、已支出总额
				paid_amount_total=payRecord.getPaidAmount();
			}
			//更新放款主表 支出收入、支出费用总额
			LoanTransMain loanTransMain_total=new LoanTransMain();
			loanTransMain_total.setLastModifyTime(loanTransMain.getLastModifyTime());
			loanTransMain_total.setTransMainId(transMainId);
			if(SystemConst.InOutCome.IN.equals(kind_id)){
				loanTransMain_total.setPaidFeesAmount(paid_amount_total);
			}else{
				loanTransMain_total.setOutgoingsFeesAmount(paid_amount_total);
			}
			flag_record=loanTransMainMapper.updateByPrimaryKeySelective(loanTransMain_total);
			
	
		}
		if(flag_record<1){
			throw new RuntimeException("并发原因造成收款或者支出交易失败");
		}

		return SystemConst.SUCCESS;
		
	}

	
	/**
	 * 平台支出-生成提现申请数据
	 * @param payEntity
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	private boolean accountInOutInfoinsertData(LoanFeesRecord record, LoanFeesPlan loanPayPlan,LoanTransMain loanTransMain) {
		if (loanPayPlan == null || loanTransMain == null) {
			return false;
		}
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		LoanAccountInOutInfo loanAccountInOutInfo=new LoanAccountInOutInfo();
		loanAccountInOutInfo.setCreateUser(user.getStaffName());
		loanAccountInOutInfo.setUnionId(user.getUnionId());
		loanAccountInOutInfo.setCompanyId(user.getCompanyId());
		loanAccountInOutInfo.setTransNo(SystemConst.TransType.TYPEID1010 + GetUniqueNoUtil.getOrderNo());
		loanAccountInOutInfo.setStatus(SystemConst.Status.STATUS03);
		loanAccountInOutInfo.setCreateTime(new Date());
		loanAccountInOutInfo.setTransType(SystemConst.TransType.TYPEID1006);
		loanAccountInOutInfo.setAmount(record.getPaidAmount());//支出总额
		loanAccountInOutInfo.setOrderNo(loanTransMain.getBusinessId());
		loanAccountInOutInfo.setField1(record.getSubjectId());
		loanAccountInOutInfo.setBankName(loanPayPlan.getBankName());
		loanAccountInOutInfo.setBankCardNo(loanPayPlan.getCardNo());
		loanAccountInOutInfo.setAccountId(loanPayPlan.getAccountId());
		loanAccountInOutInfo.setActualPayTime(new Date());
		if(!StringUtils.isEmpty(loanPayPlan.getAccountId())&&!StringUtils.isEmpty(loanPayPlan.getCardNo())){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("accountName", loanPayPlan.getAccountName());
			map.put("accountId", loanPayPlan.getAccountId());
			map.put("bankCardNo", loanPayPlan.getCardNo());
			LoanAccountCard card = loanAccountCardMapper.queryCusInfoAndBankInfo(map);
			if (card != null) {
				loanAccountInOutInfo.setBankId(card.getBankId());
				loanAccountInOutInfo.setMobile(card.getMobile());
				loanAccountInOutInfo.setCertificateNo(card.getCertificateNo());
				loanAccountInOutInfo.setAccountHolder(card.getAccountHolder());
				loanAccountInOutInfo.setCustomerNature(card.getCustomerNature());
				loanAccountInOutInfo.setCustomerId(card.getOwnerId());
				loanAccountInOutInfo.setCustomerName(card.getCustomerName());
				loanAccountInOutInfo.setCustomerType(card.getCustomerType());
			}
		}
		try {
			int t=loanAccountInOutInfoMapper.insertSelective(loanAccountInOutInfo);
			if(t<1){
				return false;
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(readOnly = false)
	public String accountInAndOutTrans(LoanAccountsTrans loanAccountsTrans) {
		
		//如果交易为充值
		if(SystemConst.TransType.TYPEID1005.equals(loanAccountsTrans.getTransType())){
			loanAccountsTrans.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_ACCOUNT_IN);
		//交易为提现
		}else if(SystemConst.TransType.TYPEID1006.equals(loanAccountsTrans.getTransType())){
			loanAccountsTrans.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_ACCOUNT_OUT);
			//解冻账户金额
			String recode=accountFreezeOutTrans(loanAccountsTrans);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("业务编号："+loanAccountsTrans.getOrderNo()+"解冻失败失败");
			}
		}
		return accountChangeTrans(loanAccountsTrans);
		
	}
	@Transactional(readOnly = false)
	public String LoanAdvanceTrans(LoanAdvance loanAdvance) {
		String recode=SystemConst.FAIL;	
		if(loanAdvance==null||StringUtils.isEmpty(loanAdvance.getPlanId())
				||loanAdvance.getAdvanceTotal()==null||loanAdvance.getAdvanceTime()==null){
			throw new RuntimeException("垫付还款计划ID、垫付总金额、垫付时间不能为空！");
		}
		
		//还款计划ID
		Long planId=loanAdvance.getPlanId();
		BigDecimal advanceTotal=loanAdvance.getAdvanceTotal();
		
	    //垫付本金
	    BigDecimal advanceCapital=new BigDecimal(0);
	    //垫付利息
	    BigDecimal advanceInterest=new BigDecimal(0);
	    //垫付时间
	    Date advanceTime=loanAdvance.getAdvanceTime();
	    
	    //应垫付本金
	    BigDecimal advanceCapital_=new BigDecimal(0);
	    //应垫付利息
	    BigDecimal advanceInterest_=new BigDecimal(0);
	    
	    if(loanAdvance.getAdvanceCapital()!=null){
	    	advanceCapital=loanAdvance.getAdvanceCapital();
	    }
	    if(loanAdvance.getAdvanceInterest()!=null){
	    	advanceInterest=loanAdvance.getAdvanceInterest();
	    }
		//查询还款计划
		LoanPayPlan loanPayPlan=loanPayPlanMapper.selectByPrimaryKey(planId.toString());
		if(loanPayPlan==null||StringUtils.isEmpty(loanPayPlan.getId())){
			throw new RuntimeException("没查询到需要还款的记录!");
		}
		String transMainId=loanPayPlan.getTransMainId();
		//查询放款账务主记录
		LoanTransMain loanTransMain =loanTransMainMapper.selectByPrimaryKey(transMainId);
		if(loanTransMain==null||StringUtils.isEmpty(loanTransMain.getTransMainId())){
			throw new RuntimeException("没查询到放款主记录!");
		}
		
		
		Date repaymentCapitalTime=loanPayPlan.getRepaymentCapitalTime();
		Date repaymentInterestTime=loanPayPlan.getRepaymentInterestTime();
		
	    BigDecimal repaymentCapital = new BigDecimal(0);//应还本金
	    BigDecimal repaymentInterest = new BigDecimal(0);//应还利息（元）
	    BigDecimal paidCapital= new BigDecimal(0);
	    BigDecimal paidInterest= new BigDecimal(0);
	    if(loanPayPlan.getRepaymentCapital()!=null)repaymentCapital=loanPayPlan.getRepaymentCapital();
	    if(loanPayPlan.getRepaymentInterest()!=null)repaymentInterest=loanPayPlan.getRepaymentInterest();
	    if(loanPayPlan.getPaidCapital()!=null)paidCapital=loanPayPlan.getPaidCapital();
	    if(loanPayPlan.getPaidInterest()!=null)paidInterest=loanPayPlan.getPaidInterest();
	    
	    if(repaymentCapital.compareTo(paidCapital)==1){
	    	advanceCapital_=repaymentCapital.subtract(paidCapital);
	    }
	    if(repaymentInterest.compareTo(paidInterest)==1){
	    	advanceInterest_=repaymentInterest.subtract(paidInterest);
	    }
	    if(advanceCapital.compareTo(new BigDecimal(0))==1 && advanceCapital.compareTo(advanceCapital_)!=0){
	    	throw new RuntimeException("前端计算垫付本金不正确!");
	    }
	    if(advanceInterest.compareTo(new BigDecimal(0))==1 && advanceInterest.compareTo(advanceInterest_)!=0){
	    	throw new RuntimeException("前端计算垫付利息不正确!");
	    }
	    if(advanceCapital.compareTo(new BigDecimal(0))==1 && repaymentCapitalTime!=null &&DateUtils.daysBetween(repaymentCapitalTime, advanceTime)<AdvanceDay){
	    	throw new RuntimeException("没到还本时间，不能提前垫付!");
	    }
	    if(advanceInterest.compareTo(new BigDecimal(0))==1 && repaymentInterestTime!=null &&DateUtils.daysBetween(repaymentInterestTime, advanceTime)<AdvanceDay){
	    	throw new RuntimeException("没到还息时间，不能提前垫付!");
	    }

	    LoanAccountInfo act_qury=new LoanAccountInfo();
		//设置公司编码
		act_qury.setOwnerId(loanTransMain.getCompanyId());
		act_qury.setAccountType(SystemConst.AccountType.COMPANY);
		act_qury.setStatus(SystemConst.Status.STATUS03);
		List<LoanAccountInfo> list_act=loanAccountInfoMapper.queryList(act_qury);
		if(list_act==null||list_act.size()<1){
			throw new RuntimeException("没查询到公司对应的账号，不能做垫付交易，请在配置公司账户!");
		}
		LoanAccountInfo act_company=list_act.get(0); 
		loanAdvance.setAdvanceCustomerId(act_company.getOwnerId());
		loanAdvance.setAdvanceAccountId(act_company.getAccountId());
		loanAdvance.setAdvanceCustomerName(act_company.getOwnerName());
		loanAdvance.setAccountId(loanPayPlan.getAccountId());
		loanAdvance.setCustomerId(loanPayPlan.getCustomerId());
		loanAdvance.setCustomerName(loanPayPlan.getCustomerName());
		loanAdvance.setAdvanceCapital(advanceCapital);
		loanAdvance.setAdvanceInterest(advanceInterest);
		loanAdvance.setAdvanceTotal(advanceTotal);
		loanAdvance.setAdvanceRate(loanTransMain.getDebtRate());
		loanAdvance.setStatus(SystemConst.Status.STATUS03);
		loanAdvance.setPeriod(loanPayPlan.getPeriod());
		loanAdvance.setOrderNo(loanTransMain.getBusinessId());
		String tranId=GetUniqueNoUtil.getCustNo();
		loanAdvance.setAdvanceId(tranId);
		loanAdvance.setTransMainId(transMainId);
		Date create_date=new Date();
		loanAdvance.setCreateTime(create_date);
		loanAdvance.setLastModifyTime(create_date);
		int flag=loanAdvanceMapper.insertSelective(loanAdvance);
		if(flag<1){
			throw new RuntimeException("插入垫付记录失败!");
		}
		//交易临时
		LoanAccountsTrans trans_temp=new LoanAccountsTrans();
		//复制loanAdvance属性
		BeanUtils.copyProperties(loanAdvance,trans_temp);
		trans_temp.setBusinessId(tranId);
		trans_temp.setTransType(SystemConst.TransType.TYPEID1004);
		trans_temp.setTransTime(advanceTime);
		//公司出账
		LoanAccountsTrans trans_insert=new LoanAccountsTrans();
		//复制loanPayPlan属性
		BeanUtils.copyProperties(trans_temp,trans_insert);
		trans_insert.setOwnerId(act_company.getOwnerId());
		trans_insert.setOwnerName(act_company.getOwnerName());
		trans_insert.setAccountId(act_company.getAccountId());
		trans_insert.setAccountName(act_company.getOwnerName());
		//设置垫付本金支出科目
		if(advanceCapital.compareTo(new BigDecimal(0))==1){
			trans_insert.setAmount(advanceCapital);
			recode=accountChangeTrans(trans_insert,advanceCapital,SystemConst.SubjectInfo.SUBJECTID_ADVANCE_CAPITAL_OUT);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("垫付本金支出交易失败!");
			}
		}
		//设置垫付利息支出科目
		if(advanceInterest.compareTo(new BigDecimal(0))==1){
			trans_insert.setAmount(advanceInterest);
			recode=accountChangeTrans(trans_insert,advanceInterest,SystemConst.SubjectInfo.SUBJECTID_ADVANCE_INTEREST_OUT);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("垫付利息支出交易失败!");
			}
		}
		//借款人入账
		LoanAccountsTrans trans_brow=new LoanAccountsTrans();
		//复制loanPayPlan属性
		BeanUtils.copyProperties(trans_temp,trans_brow);
		trans_brow.setOwnerId(loanAdvance.getCustomerId());
		trans_brow.setOwnerName(loanAdvance.getCustomerName());
		trans_brow.setAccountId(loanAdvance.getAccountId());
		trans_brow.setAccountName(loanAdvance.getCustomerName());
		trans_brow.setBusinessId(tranId);
		trans_brow.setOpponentAccountId(act_company.getAccountId());
		trans_brow.setOpponentAccountName(act_company.getOwnerName());
		//设置垫付本金入账科目
		if(advanceCapital.compareTo(new BigDecimal(0))==1){
			trans_insert.setAmount(advanceCapital);
			recode=accountChangeTrans(trans_brow,advanceCapital,SystemConst.SubjectInfo.SUBJECTID_ADVANCE_CAPITAL_IN);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("垫付本金入账交易失败!");
			}
		}
		//设置垫付利息入账科目
		if(advanceInterest.compareTo(new BigDecimal(0))==1){
			trans_insert.setAmount(advanceInterest);
			recode=accountChangeTrans(trans_brow,advanceInterest,SystemConst.SubjectInfo.SUBJECTID_ADVANCE_INTEREST_IN);
			if(SystemConst.FAIL.equals(recode)){
				throw new RuntimeException("垫付利息入账交易失败!");
			}
		}
		//更新还款计划状态为垫付
		
		loanPayPlan.setTransType(SystemConst.Status.STATUS87);
		loanPayPlan.setAdvanceTotal(advanceTotal);
		loanPayPlan.setAdvanceCapital(advanceCapital);
		loanPayPlan.setAdvanceInterest(advanceInterest);
		loanPayPlan.setAdvanceTime(advanceTime);
		loanPayPlanMapper.updateByPrimaryKeySelective(loanPayPlan);
		
		//更新放款账务主表
		LoanAdvance loanAdvance_query=new LoanAdvance();
		loanAdvance_query.setTransMainId(transMainId);
		LoanAdvance loanAdvance_sum=loanAdvanceMapper.queryAdvancesum(loanAdvance_query);
		if(loanAdvance_sum !=null&& loanAdvance_sum.getAdvanceTotal()!=null){
			LoanTransMain loanTransMain_update=new LoanTransMain();
			loanTransMain_update.setTransMainId(transMainId);
			loanTransMain_update.setLastModifyTime(loanTransMain.getLastModifyTime());
			loanTransMain_update.setAdvancedAmount(loanAdvance_sum.getAdvanceTotal());
			flag=loanTransMainMapper.updateByPrimaryKeySelective(loanTransMain_update);
			if(flag<1)throw new RuntimeException("垫付更新放款主数据失败!");
		}
		
		//调用还款交易
		LoanPayRecord loanPayRecord=new LoanPayRecord();
		//复制属性
		BeanUtils.copyProperties(loanAdvance,loanPayRecord);
		loanPayRecord.setPaidTotal(advanceTotal);
		loanPayRecord.setPaidCapital(advanceCapital);
		loanPayRecord.setPaidInterest(advanceInterest);
		loanPayRecord.setActualTransTime(advanceTime);
		loanPayRecord.setTransMainId(transMainId);
		recode=loanPayTrans(loanPayRecord) ;
		if(SystemConst.FAIL.equals(recode)){
			throw new RuntimeException("调用还款交易失败!");
		}
		return SystemConst.SUCCESS;
	}

	@Transactional(readOnly = false)
	public String LoanAdvancePayTrans(LoanAdvanceRecord loanAdvanceRecord) {
		String recode=SystemConst.FAIL;	
		if(loanAdvanceRecord==null||StringUtils.isEmpty(loanAdvanceRecord.getAdvanceId())
				||loanAdvanceRecord.getPaidTotal()==null||loanAdvanceRecord.getPaidAdvanceTime()==null){
			throw new RuntimeException("债务还款还债总金额、还债利息、垫付记录id、还债时间不能为空！");
		}
		//垫付记录ID
		String id=loanAdvanceRecord.getAdvanceId();
		 //还垫付总金额
	    BigDecimal paidTotal=loanAdvanceRecord.getPaidTotal();
	    //还债务利息
	    BigDecimal paidInterest=new BigDecimal(0);
	    if(loanAdvanceRecord.getPaidInterest()!=null){
	    	paidInterest=loanAdvanceRecord.getPaidInterest();
	    }
	  //垫付天数
	  	int overDay=0;
	    //还债时间
	    Date paidAdvanceTime=loanAdvanceRecord.getPaidAdvanceTime();
	    //债务利息总额
	    BigDecimal interest=new BigDecimal(0);
	    //剩余垫付总额
	    BigDecimal remainAdvanceTotal=new BigDecimal(0);
	    //还垫付金额
	    BigDecimal paidCapital=paidTotal.subtract(paidInterest);
	    if(paidInterest.compareTo(new BigDecimal(0))==-1){
	    	throw new RuntimeException("债务利息不能小于0，前端计算有误！");
	    }
	    //查询垫付记录
  		LoanAdvance loanAdvance=loanAdvanceMapper.selectByPrimaryKey(id);
  		Date paidAdvanceTime_=loanAdvance.getPaidAdvanceTime();
  		if(paidAdvanceTime_==null)paidAdvanceTime_=loanAdvance.getAdvanceTime();
	    if(loanAdvance==null ||StringUtils.isEmpty(loanAdvanceRecord.getAdvanceId())){
	    	throw new RuntimeException("根据id没查询到垫付记录："+id+"！");
	    }
	    if(SystemConst.Status.STATUS90.equals(loanAdvanceRecord.getStatus())){
	    	throw new RuntimeException("已做过垫付还款，不能重复还款："+id+"！");
	    }
		if(DateUtils.daysBetween(paidAdvanceTime_, paidAdvanceTime)<0){
			throw new RuntimeException("本次还债时间必须等于或者晚于上次还债时间或者垫付时间:"+DateUtils.Date2String(paidAdvanceTime_));
		}
	    String transMainId=loanAdvanceRecord.getTransMainId();
		//查询放款账务主记录
		LoanTransMain loanTransMain =loanTransMainMapper.selectByPrimaryKey(transMainId);
		if(loanTransMain==null||StringUtils.isEmpty(loanTransMain.getTransMainId())){
			throw new RuntimeException("没查询到放款主记录!");
		}
		//剩余垫付金额
		
		remainAdvanceTotal=loanAdvance.getAdvanceTotal().subtract(loanAdvance.getPaidCapital());
		//本次还债时间
		loanAdvance.setNowPaidAdvanceTime(paidAdvanceTime);
		//计算垫付债务利息
		LoanAdvance resAdvance=advanceAmountCompute(loanAdvance);
		interest=resAdvance.getRepaymentInterest();
		overDay=resAdvance.getOverdueDays();
		/**
		//剩余垫付金额*（本次垫付日期-上一次垫付日期）天数*垫付利率
		
		Date paid_advance_time_=loanAdvance.getPaidAdvanceTime();
		if(paid_advance_time_!=null&&DateUtils.daysBetween(paid_advance_time_, paidAdvanceTime)<0){
			throw new RuntimeException("本次还债时间必须等于或者晚于上次还债时间:"+DateUtils.Date2String(paid_advance_time_));
		}
		//如果已还债时间为空，设置为垫付时间
		if(paid_advance_time_==null)paid_advance_time_=loanAdvance.getAdvanceTime();
		if(remainAdvanceTotal.compareTo(new BigDecimal(0))==1){
			int daynum=DateUtils.daysBetween(paid_advance_time_, paidAdvanceTime);
			String advanceRate=loanAdvance.getAdvanceRate();
			if(StringUtils.isEmpty(advanceRate)||StringUtils.isEmpty(advanceRate.trim())){
				advanceRate="0";
			}
			//新增垫付利息
			BigDecimal interest_add=remainAdvanceTotal.multiply(new BigDecimal(daynum)).multiply(new BigDecimal(advanceRate));
			interest=interest.add(interest_add);
		}
		**/
		//对多还垫付金额（债务本金）处理，把多还的债务本金加到债务利息中
		if(paidCapital.compareTo(new BigDecimal(0))==1
				&&paidCapital.subtract(remainAdvanceTotal).compareTo(new BigDecimal(0))==1){
			//债务利息
		    paidInterest=paidInterest.add(paidCapital.subtract(remainAdvanceTotal));
		    paidCapital=remainAdvanceTotal;
		}
	    
	    //复制属性
	    LoanAdvanceRecord loanAdvanceRecord_insert=new LoanAdvanceRecord();
	  	BeanUtils.copyProperties(loanAdvance,loanAdvanceRecord_insert);
	  	//BeanUtils.copyProperties(loanAdvanceRecord,loanAdvanceRecord_insert);
	  	loanAdvanceRecord_insert.setOverdueDays(overDay);
	  	loanAdvanceRecord_insert.setPaidTotal(paidTotal);
	  	loanAdvanceRecord_insert.setPaidCapital(paidCapital);
	  	loanAdvanceRecord_insert.setPaidInterest(paidInterest);
	  	loanAdvanceRecord_insert.setPaidAdvanceTime(paidAdvanceTime);
	  	String tranId=GetUniqueNoUtil.getCustNo();
	  	loanAdvanceRecord_insert.setId(tranId);
	  	loanAdvanceRecord_insert.setLastModifyTime(new Date());
	  	loanAdvanceRecord_insert.setCreateTime(new Date());
	  	loanAdvanceRecord_insert.setStatus(SystemConst.Status.STATUS03);
	  	if(loanAdvanceRecord.getLeEmpId()!=null){
	  		loanAdvanceRecord_insert.setCreateUser(loanAdvanceRecord.getLeEmpId().toString());
	  		loanAdvanceRecord_insert.setLastUser(loanAdvanceRecord.getLeEmpId().toString());
	  	}
	  	
	    int flag=loanAdvanceRecordMapper.insertSelective(loanAdvanceRecord_insert);
	    if(flag<1){
			throw new RuntimeException("插入债务偿还记录失败!");
		}
	    //交易临时
  		LoanAccountsTrans trans_temp=new LoanAccountsTrans();
  		//复制loanAdvance属性
  		BeanUtils.copyProperties(loanAdvanceRecord_insert,trans_temp);
  		trans_temp.setBusinessId(tranId);
  		trans_temp.setTransType(SystemConst.TransType.TYPEID1014);
  		trans_temp.setTransTime(paidAdvanceTime);
  		//借款人出账
  		LoanAccountsTrans trans_brow=new LoanAccountsTrans();
  		//复制loanPayPlan属性
  		BeanUtils.copyProperties(trans_temp,trans_brow);
  		trans_brow.setOwnerId(loanAdvanceRecord_insert.getCustomerId());
  		trans_brow.setOwnerName(loanAdvanceRecord_insert.getCustomerName());
  		trans_brow.setAccountId(loanAdvanceRecord_insert.getAccountId());
  		trans_brow.setAccountName(loanAdvanceRecord_insert.getCustomerName());
  		trans_brow.setOpponentAccountId(loanAdvance.getAdvanceAccountId());
  		trans_brow.setOpponentAccountName(loanAdvance.getAdvanceCustomerName());
  		//设置垫付本金支出科目
  		if(paidCapital.compareTo(new BigDecimal(0))==1){
  			trans_brow.setAmount(paidCapital);
  			recode=accountChangeTrans(trans_brow,paidCapital,SystemConst.SubjectInfo.SUBJECTID_PAY_ADVANCE_CAPITAL_OUT);
  			if(SystemConst.FAIL.equals(recode)){
  				throw new RuntimeException("垫付本金支出交易失败!");
  			}
  		}
  		//设置垫付利息支出科目
  		if(paidInterest.compareTo(new BigDecimal(0))==1){
  			trans_brow.setAmount(paidInterest);
  			recode=accountChangeTrans(trans_brow,paidInterest,SystemConst.SubjectInfo.SUBJECTID_PAY_ADVANCE_INTEREST_OUT);
  			if(SystemConst.FAIL.equals(recode)){
  				throw new RuntimeException("垫付利息支出交易失败!");
  			}
  		}
  		//公司入账
  		LoanAccountsTrans trans_insert=new LoanAccountsTrans();
  		//复制loanPayPlan属性
  		BeanUtils.copyProperties(trans_temp,trans_insert);
  		trans_insert.setOwnerId(loanAdvance.getAdvanceCustomerId());
  		trans_insert.setOwnerName(loanAdvance.getAdvanceCustomerName());
  		trans_insert.setAccountId(loanAdvance.getAdvanceAccountId());
  		trans_insert.setAccountName(loanAdvance.getAdvanceCustomerName());
  		trans_insert.setBusinessId(tranId);
  		trans_insert.setOpponentAccountId(loanAdvance.getAccountId());
  		trans_insert.setOpponentAccountName(loanAdvance.getCustomerName());
  		//设置回款垫付债务本金入账科目
  		if(paidCapital.compareTo(new BigDecimal(0))==1){
  			trans_insert.setAmount(paidCapital);
  			recode=accountChangeTrans(trans_insert,paidCapital,SystemConst.SubjectInfo.SUBJECTID_PAY_ADVANCE_CAPITAL_IN);
  			if(SystemConst.FAIL.equals(recode)){
  				throw new RuntimeException("回款垫付债务本金入账交易失败!");
  			}
  		}
  		//设置回款垫付债务利息入账科目
  		if(paidInterest.compareTo(new BigDecimal(0))==1){
  			trans_insert.setAmount(paidInterest);
  			recode=accountChangeTrans(trans_insert,paidInterest,SystemConst.SubjectInfo.SUBJECTID_PAY_ADVANCE_INTEREST_IN);
  			if(SystemConst.FAIL.equals(recode)){
  				throw new RuntimeException("回款垫付债务利息入账交易失败!");
  			}
  		}
  		
  		//更新放款账务主表
  		LoanAdvanceRecord loanAdvance_query=new LoanAdvanceRecord();
		loanAdvance_query.setTransMainId(transMainId);
		LoanAdvanceRecord loanAdvance_sum=loanAdvanceRecordMapper.queryAdvancesum(loanAdvance_query);
		if(loanAdvance_sum !=null){
			LoanTransMain loanTransMain_update=new LoanTransMain();
			loanTransMain_update.setTransMainId(transMainId);
			loanTransMain_update.setLastModifyTime(loanTransMain.getLastModifyTime());
			if(loanAdvance_sum.getPaidCapital()!=null)
			loanTransMain_update.setPaidAdvancedCapitalAmount(loanAdvance_sum.getPaidCapital());
			if(loanAdvance_sum.getPaidInterest()!=null)
				loanTransMain_update.setPaidAdvancedInterestAmount(loanAdvance_sum.getPaidInterest());
			flag=loanTransMainMapper.updateByPrimaryKeySelective(loanTransMain_update);
			if(flag<1)throw new RuntimeException("垫付还款更新放款主数据失败!");
		}
		
		LoanAdvanceRecord loanAdvance_query_advanceId=new LoanAdvanceRecord();
		loanAdvance_query_advanceId.setAdvanceId(loanAdvanceRecord.getAdvanceId());
		loanAdvance_query_advanceId.setTransMainId(loanAdvanceRecord.getTransMainId());
		LoanAdvanceRecord loanAdvance_sum_advanceId=loanAdvanceRecordMapper.queryAdvancesum(loanAdvance_query_advanceId);
		//应还债务利息
		loanAdvance.setRepaymentInterest(interest);
		loanAdvance.setPaidTotal(loanAdvance_sum_advanceId.getPaidTotal());
		loanAdvance.setPaidCapital(loanAdvance_sum_advanceId.getPaidCapital());
		loanAdvance.setPaidInterest(loanAdvance_sum_advanceId.getPaidInterest());
		loanAdvance.setPaidAdvanceTime(paidAdvanceTime);
		//如果已还垫付总额大于等于垫付总额设置还款状态为已完成
		if(loanAdvance_sum_advanceId.getPaidTotal().compareTo(loanAdvance.getAdvanceTotal())!=-1){
			loanAdvance.setStatus(SystemConst.Status.STATUS90);
		}
		loanAdvance.setLastUser(loanAdvanceRecord.getLastUser());
		loanAdvance.setLastModifyTime(new Date());
		flag=loanAdvanceMapper.updateByPrimaryKeySelective(loanAdvance);
		if(flag<1)throw new RuntimeException("更新垫付记录失败!");
		
		return SystemConst.SUCCESS;
	}
	
	/**
	 * 
	 * advanceAmountCompute:垫付债务利息计算器 <br/>
	 * @param advance 查询的垫付记录+当前垫付日期
	 * @return 债务天数、债务利息总额、应还总额
	 * @since JDK 1.8
	 */
	public LoanAdvance advanceAmountCompute(LoanAdvance advance) {
		if(advance==null ||advance.getNowPaidAdvanceTime()==null ||advance.getAdvanceTime()==null
				|| StringUtils.isEmpty(advance.getAdvanceId())){
			throw new RuntimeException("垫付还款更新放款主数据失败!");
		}
		
		LoanAdvance res=new LoanAdvance();
		//上次计算的债务利息
		BigDecimal repaymentInterest =new BigDecimal(0);
		//已还债务利息
		BigDecimal paidInterest =new BigDecimal(0);
		//复制loanPayPlan属性
  		BeanUtils.copyProperties(advance,res);
		//LoanAdvance advanceTwo=loanAdvenceMapper.selectByPrimaryKey(advance.getAdvanceId());
		Date paidAdvanceTime=advance.getPaidAdvanceTime();
		
		if(paidAdvanceTime==null)paidAdvanceTime=advance.getAdvanceTime();
		
		//债务天数
		int advanceDays = DateUtils.daysBetween(advance.getAdvanceTime(), advance.getNowPaidAdvanceTime());
		//本次计算债务利息天数
		int days = DateUtils.daysBetween(paidAdvanceTime, advance.getNowPaidAdvanceTime());
		if (days<=0) {
			days=0;
		}
		//已还债务本金
		BigDecimal paidCapital=new BigDecimal(0);
		if(advance.getPaidTotal()!=null){
			paidCapital=advance.getPaidCapital();
		}
		
		if(advance.getRepaymentInterest()!=null){
			repaymentInterest=advance.getRepaymentInterest();
		}
		if(advance.getPaidInterest()!=null){
			paidInterest=advance.getPaidInterest();
		}
		
		//剩余债务总额
		BigDecimal advanceTotal=advance.getAdvanceTotal().subtract(paidCapital);
		BigDecimal interest=advanceTotal.multiply(new BigDecimal(advance.getAdvanceRate()).multiply(new BigDecimal(days)));
		//债务利息：上一次的债务利息+本次计算的债务利息
		repaymentInterest=repaymentInterest.add(interest);
		//垫付天数
		res.setOverdueDays(advanceDays);
		res.setRepaymentInterest(repaymentInterest);
		//应还总额
		res.setPaidTotal(advanceTotal.add(repaymentInterest).subtract(paidInterest));
		//剩余债务利息
		res.setRemainRepaymentInterest(repaymentInterest.subtract(paidInterest));
		//剩余债务总额
		res.setRemainAdvanceTotal(advanceTotal);
		return res;
	}
	/**
	 * 
	 * accountChangeTrans:动账交易
	 * @param LoanAccountsTrans 转账、冻结、放款、收款、还款
	 * @return
	 * @since JDK 1.8
	 */
	@Override
	@Transactional(readOnly = false)
	public String accountChangeTrans(LoanAccountsTrans loanAccountsTrans)  {
		String recode=SystemConst.FAIL;
		if(loanAccountsTrans==null){
			logger.error("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额、交易类型不能为空");
			throw new RuntimeException("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额、交易类型不能为空");
			
		}
		//动账账号ID
		String accountId=loanAccountsTrans.getAccountId();
		//科目编码
		String subjectId=loanAccountsTrans.getSubjectId();
		//交易金额
		BigDecimal amount=loanAccountsTrans.getAmount();
		//交易类型
		String tansType=loanAccountsTrans.getTransType();
		if(StringUtils.isEmpty(accountId)|| StringUtils.isEmpty(accountId)||StringUtils.isEmpty(tansType)||amount==null){
			logger.error("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额、交易类型不能为空");
			throw new RuntimeException("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额、交易类型不能为空");
		}
		if(amount.compareTo(new BigDecimal(0))!=1){
			throw new RuntimeException("accountChangeTrans error 动账交易错误：交易金额必须大于0"+loanAccountsTrans.getAccountId()+"-"+loanAccountsTrans.getAmount());
		}
		//插入交易流水
		loanAccountsTrans.setStatus(SystemConst.Status.STATUS91);
		loanAccountsTrans.setTransId(GetUniqueNoUtil.getCustNo());
		//设置交易年份
		loanAccountsTrans.setTransYear(DateUtils.getYear());
		//线下交易时间
		if(loanAccountsTrans.getTransTime()==null){
			//交易时间
			loanAccountsTrans.setTransTime(new Date());
		}
		loanAccountsTrans.setLastModifyTime(new Date());
		loanAccountsTrans.setCreateTime(new Date());
		int flag=loanAccountsTransMapper.insert(loanAccountsTrans);
		if(flag<1){
			throw new RuntimeException("loanAccountsTrans 插入交易流水失败");
		}
		
		try {
			LoanAccountInfo loanAccountInfo=loanAccountInfoMapper.selectByPrimaryKey(accountId);
			
			if(loanAccountInfo==null ||!"03".equals(loanAccountInfo.getStatus())){
				logger.error("accountId:"+accountId+" 没查询到账号信息或者账户状态无效。");
				throw new Exception("accountId:"+accountId+" 没查询到账号信息或者账户状态无效。");
			}
			//设置账户所有者ID、NAME
			loanAccountsTrans.setAccountName(loanAccountInfo.getOwnerName());
			loanAccountsTrans.setOwnerName(loanAccountInfo.getOwnerName());
			loanAccountsTrans.setOwnerId(loanAccountInfo.getOwnerId());
			//查询科目信息
			LoanAccountsSubjectInfo loanAccountsSubjectInfo=loanAccountsSubjectInfoMapper.selectByPrimaryKey(subjectId);
			if(loanAccountsSubjectInfo==null ||StringUtils.isEmpty(loanAccountsSubjectInfo.getSubjectId())){
				throw new RuntimeException("subjectId:"+subjectId+"科目不存在，请核对。");
			}
			//设置交易方向
			String direction=loanAccountsSubjectInfo.getDirection();
			loanAccountsTrans.setDirection(direction);
			
			//如果科目为入账
			if(Direction.IN.getId().equals(direction)||SystemConst.TransType.TYPEID1012.equals(tansType)){
				loanAccountsTrans.setDebitAmount(amount);
				loanAccountsTrans.setCreditAmount(new BigDecimal(0.00));
			//如果科目为出账
			}else if(Direction.OUT.getId().equals(direction)||SystemConst.TransType.TYPEID1011.equals(tansType)){
				loanAccountsTrans.setDebitAmount(new BigDecimal(0.00));
				loanAccountsTrans.setCreditAmount(amount);
			}
			
			//设置科目名称
			loanAccountsTrans.setSubjectName(loanAccountsSubjectInfo.getSubjectName());
			
			//如果交易为冻结和解冻
			if(SystemConst.TransType.TYPEID1011.equals(tansType)||
					SystemConst.TransType.TYPEID1012.equals(tansType)){
				accountCoreTransService.accountTransFreeze(loanAccountsTrans);
			}else{
				accountCoreTransService.accountTransInOrOut(loanAccountsTrans);
			}
			//交易成功
			loanAccountsTrans.setStatus(SystemConst.Status.STATUS90);
			recode=SystemConst.SUCCESS;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			//更新交易状态
			loanAccountsTrans.setLastModifyTime(new Date());
			loanAccountsTransMapper.updateByPrimaryKeySelective(loanAccountsTrans);
		}
		
		
		return recode;
	}
	
	/**
	 * 生成还款计划
	 */
	@Override
	public List<LoanPayPlanTemp> generateLoanPayPlanTemp(LoanTransMain loanTransMain) {
		List<LoanPayPlanTemp> LoanPayPlanTempList = new ArrayList<>();
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		String isPayPlanTemp = loanTransMain.getIsPayPlanTemp();//是否生成还款计划
		Date lendingTime = loanTransMain.getLendingTime();//放款时间
		Short term = loanTransMain.getTerm();//借款期限
		String termUnit = loanTransMain.getTermUnit();//借款期限单位
		String interestWay = loanTransMain.getInterestWay();//结息方式
		Date dateOfInterest = loanTransMain.getDateOfInterest();//结息指定日期
		BigDecimal rate = loanTransMain.getRate();//借款利率
		if (rate!=null) {
			rate = rate.divide(new BigDecimal(100),32,BigDecimal.ROUND_HALF_UP);
		}else {
			throw new RuntimeException("生成还款计划-借款利率不能为空！");
		}
		String rateUnit = loanTransMain.getRateUnit();//借款利率单位
		BigDecimal amount = loanTransMain.getAmount();//借款金额
		String orderNo = loanTransMain.getBusinessId();//报单编号
		String customerId = loanTransMain.getBorrowerId();//客户编号
		String customerName = loanTransMain.getBorrower();//客户姓名
		String accountId = loanTransMain.getBorrowerAccountId();//客户账号
		String repaymentMethod = loanTransMain.getRepaymentMethod();//还款方式
		if (StringUtils.isEmpty(rateUnit)||StringUtils.isEmpty(amount)||StringUtils.isEmpty(orderNo)||StringUtils.isEmpty(customerId)||StringUtils.isEmpty(accountId)||StringUtils.isEmpty(repaymentMethod)) {
			throw new RuntimeException("生成还款计划-借款利率单位、借款金额、报单编号、客户编号、客户账号、还款方式不能为空！");
		}
		//根据单号删除临时表数据
		loanPayPlanTempMapper.deleteByOrderNo(orderNo);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (SystemConst.RepaymentMethod.ONCE_PAYMENT.equals(repaymentMethod)) {
			//利率换算为日利率
			if (SystemConst.RateUnit.MONTH.equals(rateUnit)) {
				rate = rate.multiply(new BigDecimal(12)).divide(new BigDecimal(365),32,BigDecimal.ROUND_HALF_UP);
			}else if (SystemConst.RateUnit.YEAR.equals(rateUnit)) {
				rate = rate.divide(new BigDecimal(365),32,BigDecimal.ROUND_HALF_UP);
			}
			List<Map<String, Date>> listDate = DateUtils.getStartEndTimeList(repaymentMethod, term, termUnit, lendingTime, dateOfInterest);
			Map<String, Date> map = listDate.get(0);
			Date start = map.get("start");
			Date end = map.get("end");
			Date appoint = map.get("appoint");
			//借款天数
			BigDecimal days = new BigDecimal(DateUtils.daysBetween(start,end)+1);
			
			//计算利息
			BigDecimal interest = new BigDecimal(0);
			if (SystemConst.RateUnit.TIME.equals(rateUnit)) {
				interest = amount.multiply(rate);
			}else {
				interest = amount.multiply(rate).multiply(days);
			}
			LoanPayPlanTemp loanPayPlanTemp = new LoanPayPlanTemp();
			loanPayPlanTemp.setOrderNo(orderNo);
			loanPayPlanTemp.setCustomerId(customerId);
			loanPayPlanTemp.setCustomerName(customerName);
			loanPayPlanTemp.setAccountId(accountId);
			loanPayPlanTemp.setRepaymentMethod(repaymentMethod);
			loanPayPlanTemp.setRepaymentTotal(interest.add(amount));//应还总额
			loanPayPlanTemp.setRepaymentCapital(amount);
			loanPayPlanTemp.setRepaymentCapitalTime(end);
			loanPayPlanTemp.setRepaymentInterest(interest);
			if (SystemConst.InterestWay.LOAN_DATE.equals(interestWay)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(start);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.add(Calendar.SECOND, -1);
				loanPayPlanTemp.setRepaymentInterestTime(calendar.getTime());
			}else if (SystemConst.InterestWay.DUE_DATE.equals(interestWay)) {
				loanPayPlanTemp.setRepaymentInterestTime(end);
			}else if (SystemConst.InterestWay.SPECIFIED_DATE.equals(interestWay)) {
				loanPayPlanTemp.setRepaymentInterestTime(appoint);
			}
			loanPayPlanTemp.setPeriod((short)(1));//当前期数
			loanPayPlanTemp.setTransType(SystemConst.Status.STATUS03);
			loanPayPlanTemp.setInterestStart(sdf.format(start));
			loanPayPlanTemp.setInterestEnd(sdf.format(end));
			if (SystemConst.IsPayPlanTemp.YES.equals(isPayPlanTemp)) {//生成还款计划
				loanPayPlanTemp.setStatus(SystemConst.Status.STATUS03);
			}else {
				loanPayPlanTemp.setStatus(SystemConst.Status.STATUS01);
			}
			loanPayPlanTemp.setCreateUser(user.getUserId().toString());//创建人
			loanPayPlanTemp.setCreateTime(new Date());//创建时间
			loanPayPlanTemp.setLastUser(user.getUserId().toString());//最后操作人
			loanPayPlanTemp.setLastModifyTime(new Date());//最后操作时间
			loanPayPlanTemp.setField1(loanTransMain.getRate().setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+rateUnit);//借款利率
			
			loanPayPlanTempMapper.insertSelective(loanPayPlanTemp);
			LoanPayPlanTempList.add(loanPayPlanTemp);
			loanTransMain.setSumTerm((short)1);
			
		}else if (RepaymentMethod.MONTHLY_INTERSETS.equals(repaymentMethod)) {
			//利率换算为月利率
			if (SystemConst.RateUnit.DAY.equals(rateUnit)) {
				rate = rate.multiply(new BigDecimal(365)).divide(new BigDecimal(12),32,BigDecimal.ROUND_HALF_UP);
			}else if (SystemConst.RateUnit.YEAR.equals(rateUnit)) {
				rate = rate.divide(new BigDecimal(12),32,BigDecimal.ROUND_HALF_UP);
			}
			List<Map<String, Date>> listDate = DateUtils.getStartEndTimeList(repaymentMethod, term, termUnit, lendingTime, dateOfInterest);
			
			Map<String, Date> lastMap = listDate.get(listDate.size()-1);
			Date lastStart = lastMap.get("start");
			Date lastEnd = lastMap.get("end");
			Date naturalEnd = lastMap.get("naturalEnd");
			int newTerm = listDate.size();
			
			//最后一期实际天数
			BigDecimal lastTermDays = new BigDecimal(DateUtils.daysBetween(lastStart,lastEnd)+1);
			//最后一期理论天数
			BigDecimal lastTermMonthDays = new BigDecimal(DateUtils.daysBetween(lastStart,naturalEnd)+1);
			//总利息
			BigDecimal interestTotal = amount.multiply(rate).multiply(new BigDecimal(newTerm).subtract(new BigDecimal(1)).add(lastTermDays.divide(lastTermMonthDays,32,BigDecimal.ROUND_HALF_UP)));
			//每期利息（前n-1期利息）
			BigDecimal interest = amount.multiply(rate);	
			//最后一期利息
			BigDecimal lastInterest = interestTotal.subtract(interest.multiply(new BigDecimal(newTerm).subtract(new BigDecimal(1))));		
			for (int i = 0; i < newTerm; i++) {
				Map<String, Date> map = listDate.get(i);
				Date start = map.get("start");
				Date end = map.get("end");
				Date appoint = map.get("appoint");

				LoanPayPlanTemp loanPayPlanTemp = new LoanPayPlanTemp();
				loanPayPlanTemp.setOrderNo(orderNo);
				loanPayPlanTemp.setCustomerId(customerId);
				loanPayPlanTemp.setCustomerName(customerName);
				loanPayPlanTemp.setAccountId(accountId);
				loanPayPlanTemp.setRepaymentMethod(repaymentMethod);
				if (i==newTerm-1) {
					loanPayPlanTemp.setRepaymentCapital(amount);
					loanPayPlanTemp.setRepaymentInterest(lastInterest);
					loanPayPlanTemp.setRepaymentTotal(lastInterest.add(amount));//应还总额
					loanPayPlanTemp.setRepaymentCapitalTime(end);
				}else{
					loanPayPlanTemp.setRepaymentCapital(new BigDecimal(0));
					loanPayPlanTemp.setRepaymentInterest(interest);
					loanPayPlanTemp.setRepaymentTotal(interest);//应还总额
					loanPayPlanTemp.setRepaymentCapitalTime(null);
				}
				if (SystemConst.InterestWay.LOAN_DATE.equals(interestWay)) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(start);
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					calendar.add(Calendar.SECOND, -1);
					loanPayPlanTemp.setRepaymentInterestTime(calendar.getTime());
				}else if (SystemConst.InterestWay.DUE_DATE.equals(interestWay)) {
					loanPayPlanTemp.setRepaymentInterestTime(end);
				}else if (SystemConst.InterestWay.SPECIFIED_DATE.equals(interestWay)) {
					loanPayPlanTemp.setRepaymentInterestTime(appoint);
				}
				loanPayPlanTemp.setPeriod((short)(i+1));//当前期数
				loanPayPlanTemp.setTransType(SystemConst.Status.STATUS03);
				loanPayPlanTemp.setInterestStart(sdf.format(start));
				loanPayPlanTemp.setInterestEnd(sdf.format(end));
				if (SystemConst.IsPayPlanTemp.YES.equals(isPayPlanTemp)) {//生成还款计划
					loanPayPlanTemp.setStatus(SystemConst.Status.STATUS03);
				}else {
					loanPayPlanTemp.setStatus(SystemConst.Status.STATUS01);
				}
				loanPayPlanTemp.setCreateUser(user.getUserId().toString());//创建人
				loanPayPlanTemp.setCreateTime(new Date());//创建时间
				loanPayPlanTemp.setLastUser(user.getUserId().toString());//最后操作人
				loanPayPlanTemp.setLastModifyTime(new Date());//最后操作时间
				loanPayPlanTemp.setField1(loanTransMain.getRate().setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+rateUnit);//借款利率
				
				loanPayPlanTempMapper.insertSelective(loanPayPlanTemp);
				LoanPayPlanTempList.add(loanPayPlanTemp);
			}
			loanTransMain.setSumTerm((short)newTerm);
		}else if(RepaymentMethod.EQUAL_PRINCIPAL_INTEREST.equals(repaymentMethod)){
			//TODO
		}
		BigDecimal sumRepaymentTotal = new BigDecimal(0);
		for (int i = 0; i < LoanPayPlanTempList.size(); i++) {
			LoanPayPlanTemp loanPayPlanTemp = LoanPayPlanTempList.get(i);
			BigDecimal repaymentTotal = loanPayPlanTemp.getRepaymentTotal();
			if (repaymentTotal!=null) {
				sumRepaymentTotal = sumRepaymentTotal.add(repaymentTotal);
			}
		}
		loanTransMain.setSumFeesTotal(sumRepaymentTotal);
		return LoanPayPlanTempList;
	}
	
	/**
	 * 生成放款记录保存到临时表
	 * @param loanTransMain
	 * @return
	 */
	@Override
	public List<LoanRecordTemp> generateLoanRecordTemp(LoanTransMain loanTransMain) {
		//获取借款人信息
		List<LoanRecordTemp> loanRecordTempList = loanTransMain.getListLoanRecordTemp();
		//存放结果
		List<LoanRecordTemp> loanRecordTempListNew = new ArrayList<LoanRecordTemp>();
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		String isLoanRecordTemp = loanTransMain.getIsLoanRecordTemp();//是否生成放款记录
		Date lendingTime = loanTransMain.getLendingTime();//放款时间
		Short term = loanTransMain.getTerm();//借款期限
		String termUnit = loanTransMain.getTermUnit();//借款期限单位
		String rateUnit = loanTransMain.getRateUnit();//借款利率单位
		String orderNo = loanTransMain.getBusinessId();//报单编号
		String customerId = loanTransMain.getBorrowerId();//客户编号
		String customerName = loanTransMain.getBorrower();//客户姓名
		String repaymentMethod = loanTransMain.getRepaymentMethod();//还款方式
		if (StringUtils.isEmpty(rateUnit)||StringUtils.isEmpty(orderNo)||StringUtils.isEmpty(customerId)||StringUtils.isEmpty(repaymentMethod)) {
			throw new RuntimeException("生成放款记录-借款利率单位、报单编号、客户编号、还款方式、交易类型不能为空！");
		}
		//根据单号删除临时表数据
		loanRecordTempMapper.deleteByOrderNo(orderNo);
		
		for (int i = 0; i < loanRecordTempList.size(); i++) {
			BigDecimal rate = loanTransMain.getRate();//借款利率
			if (rate!=null) {
				rate = rate.divide(new BigDecimal(100),32,BigDecimal.ROUND_HALF_UP);
			}else {
				throw new RuntimeException("生成放款记录-借款利率不能为空！");
			}
			LoanRecordTemp loanRecordTemp = loanRecordTempList.get(i);

			BigDecimal loanRate = loanRecordTemp.getLoanRate();//放款利率
			if (loanRate!=null) {
				loanRate = loanRate.divide(new BigDecimal(100),32,BigDecimal.ROUND_HALF_UP);
			}else {
				throw new RuntimeException("生成放款记录-借款利率不能为空！");
			}
			BigDecimal loanAmount = loanRecordTemp.getLoanAmount();//放款金额
			String loanRateUnit = loanRecordTemp.getLoanRateUnit();//放款利率单位
			String accountId = loanRecordTemp.getAccountId();//放款人账号ID
			//放款利息
			BigDecimal loanInterest = new BigDecimal(0);
			//借款利息
			BigDecimal interest = new BigDecimal(0);
			
			if (SystemConst.RepaymentMethod.ONCE_PAYMENT.equals(repaymentMethod)) {
				//放款利率换算为日利率
				if (SystemConst.RateUnit.YEAR.equals(loanRateUnit)) {
					loanRate = loanRate.divide(new BigDecimal(365),32,BigDecimal.ROUND_HALF_UP);
				}else if (SystemConst.RateUnit.MONTH.equals(loanRateUnit)) {
					loanRate = loanRate.multiply(new BigDecimal(12)).divide(new BigDecimal(365),32,BigDecimal.ROUND_HALF_UP);
				}
				//借款利率换算为日利率
				if (SystemConst.RateUnit.YEAR.equals(rateUnit)) {
					rate = rate.divide(new BigDecimal(365),32,BigDecimal.ROUND_HALF_UP);
				}else if (SystemConst.RateUnit.MONTH.equals(rateUnit)) {
					rate = rate.multiply(new BigDecimal(12)).divide(new BigDecimal(365),32,BigDecimal.ROUND_HALF_UP);
				}
				List<Map<String, Date>> listDate = DateUtils.getStartEndTimeList(repaymentMethod, term, termUnit, lendingTime, null);
				Map<String, Date> map = listDate.get(0);
				Date start = map.get("start");
				Date end = map.get("end");
				//借款天数
				BigDecimal days = new BigDecimal(DateUtils.daysBetween(start,end)+1);
				
				if (SystemConst.RateUnit.TIME.equals(loanRateUnit)) {//利率为次计算放款利息
					//计算放款利息
					loanInterest = loanAmount.multiply(loanRate);
				}else {
					//计算放款利息
					loanInterest = loanAmount.multiply(loanRate).multiply(days);
				}
				if (SystemConst.RateUnit.TIME.equals(rateUnit)) {//利率为次计算借款利息
					//计算借款利息
					interest = loanAmount.multiply(rate);
				}else {
					//计算借款利息
					interest = loanAmount.multiply(rate).multiply(days);
				}
			}else if (SystemConst.RepaymentMethod.MONTHLY_INTERSETS.equals(repaymentMethod)) {
				//放款利率换算为月利率
				if (SystemConst.RateUnit.YEAR.equals(loanRateUnit)) {
					loanRate = loanRate.divide(new BigDecimal(12),32,BigDecimal.ROUND_HALF_UP);
				}else if (SystemConst.RateUnit.DAY.equals(loanRateUnit)) {
					loanRate = loanRate.multiply(new BigDecimal(365)).divide(new BigDecimal(12),32,BigDecimal.ROUND_HALF_UP);
				}
				//借款利率换算为月利率
				if (SystemConst.RateUnit.YEAR.equals(rateUnit)) {
					rate = rate.divide(new BigDecimal(12),32,BigDecimal.ROUND_HALF_UP);
				}else if (SystemConst.RateUnit.DAY.equals(rateUnit)) {
					rate = rate.multiply(new BigDecimal(365)).divide(new BigDecimal(12),32,BigDecimal.ROUND_HALF_UP);
				}
				List<Map<String, Date>> listDate = DateUtils.getStartEndTimeList(repaymentMethod, term, termUnit, lendingTime, null);
				Map<String, Date> lastMap = listDate.get(listDate.size()-1);
				Date lastStart = lastMap.get("start");
				Date lastEnd = lastMap.get("end");
				Date naturalEnd = lastMap.get("naturalEnd");
				int newTerm = listDate.size();
				
				//最后一期实际天数
				BigDecimal lastTermDays = new BigDecimal(DateUtils.daysBetween(lastStart,lastEnd)+1);
				//最后一期理论天数
				BigDecimal lastTermMonthDays = new BigDecimal(DateUtils.daysBetween(lastStart,naturalEnd)+1);
				//计算放款利息
				loanInterest = loanAmount.multiply(loanRate).multiply(new BigDecimal(newTerm).subtract(new BigDecimal(1)).add(lastTermDays.divide(lastTermMonthDays,32,BigDecimal.ROUND_HALF_UP)));
				//计算借款利息
				interest = loanAmount.multiply(rate).multiply(new BigDecimal(newTerm).subtract(new BigDecimal(1)).add(lastTermDays.divide(lastTermMonthDays,32,BigDecimal.ROUND_HALF_UP)));
			}else if (SystemConst.RepaymentMethod.EQUAL_PRINCIPAL_INTEREST.equals(repaymentMethod)) {
				//TODO
			}
			
			//计算息差
			BigDecimal interestSpread = interest.subtract(loanInterest);
			
			LoanRecordTemp loanRecordTempNew = new LoanRecordTemp();
			//复制loanAdvance属性
			BeanUtils.copyProperties(loanRecordTemp,loanRecordTempNew);
			String id=SystemConst.TransType.TYPEID1002+GetUniqueNoUtil.getCustNo();
			loanRecordTempNew.setId(id);
			loanRecordTempNew.setCustomerId(customerId);//客户编号
			loanRecordTempNew.setAccountId(accountId);//放款人账户编号
			loanRecordTempNew.setOrderNo(orderNo);//报单编号
			loanRecordTempNew.setLenderId(loanRecordTemp.getLenderId());//出借人id
			loanRecordTempNew.setLenderName(loanRecordTemp.getLenderName());//出借人姓名
			loanRecordTempNew.setOrgId(loanRecordTemp.getOrgId());//机构ID
			loanRecordTempNew.setOrgName(loanRecordTemp.getOrgName());//机构名称
			loanRecordTempNew.setCustomerNature(loanRecordTemp.getCustomerNature());//客户性质
			loanRecordTempNew.setCustomerNatureName(loanRecordTemp.getCustomerNatureName());//客户性质
			loanRecordTempNew.setLoanAmount(loanRecordTemp.getLoanAmount());//放款金额
			loanRecordTempNew.setLoanTerm(term.toString());//放款期限
			loanRecordTempNew.setLoanRate(loanRecordTemp.getLoanRate());//放款利率
			loanRecordTempNew.setLoanRateUnit(loanRecordTemp.getLoanRateUnit());//放款利率单位
			loanRecordTempNew.setLoanTime(lendingTime);//放款时间
			loanRecordTempNew.setAnticipatedInterest(loanInterest);//预期利息
			loanRecordTempNew.setInterestMargin(interestSpread);//息差
			loanRecordTempNew.setApplyTime(loanRecordTemp.getApplyTime());//申请时间
			loanRecordTempNew.setTransType(SystemConst.Status.STATUS03);//
			loanRecordTempNew.setCompanyId(loanRecordTemp.getCompanyId());//所属公司
			loanRecordTempNew.setUnionId(loanRecordTemp.getUnionId());//归属集团
			if (SystemConst.IsLoanRecordTemp.YES.equals(isLoanRecordTemp)) {//生成放款记录
				loanRecordTempNew.setStatus(SystemConst.Status.STATUS03);
			}else {
				loanRecordTempNew.setStatus(SystemConst.Status.STATUS01);
			}
			loanRecordTempNew.setCreateUser(user.getUserId().toString());//创建人
			loanRecordTempNew.setCreateTime(new Date());//创建时间
			loanRecordTempNew.setLastUser(user.getUserId().toString());//最后操作人
			loanRecordTempNew.setLastModifyTime(new Date());//最后操作时间
			loanRecordTempNew.setCustomerName(customerName);//借款人姓名
			loanRecordTempNew.setRate(loanTransMain.getRate().setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+rateUnit);//借款利率
			loanRecordTempNew.setTerm(term+termUnit);//借款期限
			
			loanRecordTempMapper.insertSelective(loanRecordTempNew);
			loanRecordTempListNew.add(loanRecordTempNew);
		}
		return loanRecordTempListNew;
	}
	
	/**
	 * 生成待付款计划
	 */
	@Override
	public List<LoanPayPlanCompanyTemp> generatePayPlanCompanyTemp(LoanTransMain loanTransMain) {
		
		//获取放款记录信息
		List<LoanRecordTemp> loanRecordTempList = loanTransMain.getListLoanRecordTemp();
		//存放待付款计划信息
		List<LoanPayPlanCompanyTemp> LoanPayPlanCompanyTempList = new ArrayList<LoanPayPlanCompanyTemp>();
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
	    String isPayPlanCompanyTemp = loanTransMain.getIsPayPlanCompanyTemp();//是否生成待付款计划
		Date lendingTime = loanTransMain.getLendingTime();//放款时间
		Short term = loanTransMain.getTerm();//借款期限
		String termUnit = loanTransMain.getTermUnit();//借款期限单位
		String payInterestWay = loanTransMain.getPayInterestWay();//付息方式
		Date dateOfPayInterest = loanTransMain.getDateOfPayInterest();//付息指定日期
		String orderNo = loanTransMain.getBusinessId();//报单编号
		String repaymentMethod = loanTransMain.getRepaymentMethod();//还款方式
		BigDecimal rate = loanTransMain.getRate();//借款利率
		String rateUnit = loanTransMain.getRateUnit();//借款利率单位
		
		if (StringUtils.isEmpty(orderNo)||StringUtils.isEmpty(repaymentMethod)) {
			throw new RuntimeException("生成待付款计划-报单编号、还款方式不能为空！");
		}
		//根据单号删除临时表数据
		loanPayPlanCompanyTempMapper.deleteByOrderNo(orderNo);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (int i = 0; i < loanRecordTempList.size(); i++) {
			LoanRecordTemp loanRecordTemp = loanRecordTempList.get(i);

			BigDecimal loanRate = loanRecordTemp.getLoanRate();//放款利率
			if (loanRate!=null) {
				loanRate = loanRate.divide(new BigDecimal(100),32,BigDecimal.ROUND_HALF_UP);
			}else {
				throw new RuntimeException("生成放款记录-放款利率不能为空！");
			}
			String customerId = loanRecordTemp.getLenderId();//客户编号
			String customerName = loanRecordTemp.getLenderName();//客户姓名
			BigDecimal loanAmount = loanRecordTemp.getLoanAmount();//放款金额
			String loanRateUnit = loanRecordTemp.getLoanRateUnit();//放款利率单位
			String accountId = loanRecordTemp.getAccountId();//出借人账号
			
			if (SystemConst.RepaymentMethod.ONCE_PAYMENT.equals(repaymentMethod)) {
				
				//放款利率换算为日利率
				if (SystemConst.RateUnit.YEAR.equals(loanRateUnit)) {
					loanRate = loanRate.divide(new BigDecimal(365),32,BigDecimal.ROUND_HALF_UP);
				}else if (SystemConst.RateUnit.MONTH.equals(loanRateUnit)) {
					loanRate = loanRate.multiply(new BigDecimal(12)).divide(new BigDecimal(365),32,BigDecimal.ROUND_HALF_UP);
				}
				List<Map<String, Date>> listDate = DateUtils.getStartEndTimeList(repaymentMethod, term, termUnit, lendingTime, dateOfPayInterest);
				Map<String, Date> map = listDate.get(0);
				Date start = map.get("start");
				Date end = map.get("end");
				Date appoint = map.get("appoint");
				//放款天数
				BigDecimal days = new BigDecimal(DateUtils.daysBetween(start,end)+1);
				
				BigDecimal loanInterest = new BigDecimal(0);
				if (SystemConst.RateUnit.TIME.equals(loanRateUnit)) {
					//计算放款利息
					loanInterest = loanAmount.multiply(loanRate);
				}else {
					//计算放款利息
					loanInterest = loanAmount.multiply(loanRate).multiply(days);
				}
				LoanPayPlanCompanyTemp loanPayPlanCompanyTemp = new LoanPayPlanCompanyTemp();
				loanPayPlanCompanyTemp.setOrderNo(orderNo);
				loanPayPlanCompanyTemp.setCustomerId(customerId);
				loanPayPlanCompanyTemp.setCustomerName(customerName);
				loanPayPlanCompanyTemp.setAccountId(accountId);
				loanPayPlanCompanyTemp.setRepaymentMethod(repaymentMethod);
				loanPayPlanCompanyTemp.setRepaymentTotal(loanInterest.add(loanAmount));//应收总额
				loanPayPlanCompanyTemp.setRepaymentCapital(loanAmount);
				loanPayPlanCompanyTemp.setRepaymentCapitalTime(end);
				loanPayPlanCompanyTemp.setRepaymentInterest(loanInterest);
				if (SystemConst.InterestWay.LOAN_DATE.equals(payInterestWay)) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(start);
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					calendar.add(Calendar.SECOND, -1);
					loanPayPlanCompanyTemp.setRepaymentInterestTime(calendar.getTime());
				}else if (SystemConst.InterestWay.DUE_DATE.equals(payInterestWay)) {
					loanPayPlanCompanyTemp.setRepaymentInterestTime(end);
				}else if (SystemConst.InterestWay.SPECIFIED_DATE.equals(payInterestWay)) {
					loanPayPlanCompanyTemp.setRepaymentInterestTime(appoint);
				}
				loanPayPlanCompanyTemp.setPeriod((short)(1));//当前期数
				loanPayPlanCompanyTemp.setTransType(SystemConst.Status.STATUS03);
				loanPayPlanCompanyTemp.setInterestStart(sdf.format(start));
				loanPayPlanCompanyTemp.setInterestEnd(sdf.format(end));
				if (SystemConst.IsPayPlanCompanyTemp.YES.equals(isPayPlanCompanyTemp)) {//生成待付款计划
					loanPayPlanCompanyTemp.setStatus(SystemConst.Status.STATUS03);
				}else {
					loanPayPlanCompanyTemp.setStatus(SystemConst.Status.STATUS01);
				}
				loanPayPlanCompanyTemp.setCreateUser(user.getUserId().toString());//创建人
				loanPayPlanCompanyTemp.setCreateTime(new Date());//创建时间
				loanPayPlanCompanyTemp.setLastUser(user.getUserId().toString());//最后操作人
				loanPayPlanCompanyTemp.setLastModifyTime(new Date());//最后操作时间
				if (rate!=null) {
					loanPayPlanCompanyTemp.setField1(rate.setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+rateUnit);//借款利率
				}
				loanPayPlanCompanyTemp.setField2(loanRecordTemp.getLoanRate().setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+loanRateUnit);//放款利率
				//保存到数据库
				loanPayPlanCompanyTempMapper.insertSelective(loanPayPlanCompanyTemp);
				LoanPayPlanCompanyTempList.add(loanPayPlanCompanyTemp);
				
			}else if (SystemConst.RepaymentMethod.MONTHLY_INTERSETS.equals(repaymentMethod)) {
				//放款利率换算为月利率
				if (SystemConst.RateUnit.YEAR.equals(loanRateUnit)) {
					loanRate = loanRate.divide(new BigDecimal(12),32,BigDecimal.ROUND_HALF_UP);
				}else if (SystemConst.RateUnit.DAY.equals(loanRateUnit)) {
					loanRate = loanRate.multiply(new BigDecimal(365)).divide(new BigDecimal(12),32,BigDecimal.ROUND_HALF_UP);
				}

				List<Map<String, Date>> listDate = DateUtils.getStartEndTimeList(repaymentMethod, term, termUnit, lendingTime, dateOfPayInterest);
				
				Map<String, Date> lastMap = listDate.get(listDate.size()-1);
				Date lastStart = lastMap.get("start");
				Date lastEnd = lastMap.get("end");
				Date naturalEnd = lastMap.get("naturalEnd");
				int newTerm = listDate.size();
				
				//最后一期实际天数
				BigDecimal lastTermDays = new BigDecimal(DateUtils.daysBetween(lastStart,lastEnd)+1);
				//最后一期理论天数
				BigDecimal lastTermMonthDays = new BigDecimal(DateUtils.daysBetween(lastStart,naturalEnd)+1);
				//计算放款利息
				BigDecimal loanInterest = loanAmount.multiply(loanRate).multiply(new BigDecimal(newTerm).subtract(new BigDecimal(1)).add(lastTermDays.divide(lastTermMonthDays,32,BigDecimal.ROUND_HALF_UP)));
				//每期利息（前n-1期利息）
				BigDecimal interest = loanAmount.multiply(loanRate);	
				//最后一期利息
				BigDecimal lastInterest = loanInterest.subtract(interest.multiply(new BigDecimal(newTerm).subtract(new BigDecimal(1))));						
			
				for (int j = 0; j < newTerm; j++) {
					Map<String, Date> map = listDate.get(j);
					Date start = map.get("start");
					Date end = map.get("end");
					Date appoint = map.get("appoint");
					
					LoanPayPlanCompanyTemp loanPayPlanCompanyTemp = new LoanPayPlanCompanyTemp();
					loanPayPlanCompanyTemp.setOrderNo(orderNo);
					loanPayPlanCompanyTemp.setCustomerId(customerId);
					loanPayPlanCompanyTemp.setCustomerName(customerName);
					loanPayPlanCompanyTemp.setAccountId(accountId);
					loanPayPlanCompanyTemp.setRepaymentMethod(repaymentMethod);
					if (j==newTerm-1) {
						loanPayPlanCompanyTemp.setRepaymentCapital(loanAmount);
						loanPayPlanCompanyTemp.setRepaymentInterest(lastInterest);
						loanPayPlanCompanyTemp.setRepaymentTotal(lastInterest.add(loanAmount));//应付总额
						loanPayPlanCompanyTemp.setRepaymentCapitalTime(end);
					}else{
						loanPayPlanCompanyTemp.setRepaymentCapital(new BigDecimal(0));
						loanPayPlanCompanyTemp.setRepaymentInterest(interest);
						loanPayPlanCompanyTemp.setRepaymentTotal(interest);//应付总额
						loanPayPlanCompanyTemp.setRepaymentCapitalTime(null);
					}
					if (SystemConst.InterestWay.LOAN_DATE.equals(payInterestWay)) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(start);
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						calendar.add(Calendar.SECOND, -1);
						loanPayPlanCompanyTemp.setRepaymentInterestTime(calendar.getTime());
					}else if (SystemConst.InterestWay.DUE_DATE.equals(payInterestWay)) {
						loanPayPlanCompanyTemp.setRepaymentInterestTime(end);
					}else if (SystemConst.InterestWay.SPECIFIED_DATE.equals(payInterestWay)) {
						loanPayPlanCompanyTemp.setRepaymentInterestTime(appoint);
					}
					loanPayPlanCompanyTemp.setPeriod((short)(j+1));//当前期数
					loanPayPlanCompanyTemp.setTransType(SystemConst.Status.STATUS03);
					loanPayPlanCompanyTemp.setInterestStart(sdf.format(start));
					loanPayPlanCompanyTemp.setInterestEnd(sdf.format(end));
					if (SystemConst.IsPayPlanCompanyTemp.YES.equals(isPayPlanCompanyTemp)) {//生成待付款计划
						loanPayPlanCompanyTemp.setStatus(SystemConst.Status.STATUS03);
					}else {
						loanPayPlanCompanyTemp.setStatus(SystemConst.Status.STATUS01);
					}
					loanPayPlanCompanyTemp.setCreateUser(user.getUserId().toString());//创建人
					loanPayPlanCompanyTemp.setCreateTime(new Date());//创建时间
					loanPayPlanCompanyTemp.setLastUser(user.getUserId().toString());//最后操作人
					loanPayPlanCompanyTemp.setLastModifyTime(new Date());//最后操作时间
					if (rate!=null) {
						loanPayPlanCompanyTemp.setField1(rate.setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+rateUnit);//借款利率
					}
					loanPayPlanCompanyTemp.setField2(loanRecordTemp.getLoanRate().setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+loanRateUnit);//放款利率
					//保存到数据库
					loanPayPlanCompanyTempMapper.insertSelective(loanPayPlanCompanyTemp);
					LoanPayPlanCompanyTempList.add(loanPayPlanCompanyTemp);
				}	
			}else if (SystemConst.RepaymentMethod.EQUAL_PRINCIPAL_INTEREST.equals(repaymentMethod)) {
				//TODO
			}
		}
		//计算待付款总额
		BigDecimal sumRepaymentTotal = new BigDecimal(0);
		for (int i = 0; i < LoanPayPlanCompanyTempList.size(); i++) {
			LoanPayPlanCompanyTemp loanPayPlanCompanyTemp = LoanPayPlanCompanyTempList.get(i);
			BigDecimal repaymentTotal = loanPayPlanCompanyTemp.getRepaymentTotal();
			if (repaymentTotal!=null) {
				sumRepaymentTotal = sumRepaymentTotal.add(repaymentTotal);
			}
		}
		loanTransMain.setSumFeesTotalPay(sumRepaymentTotal);
		return LoanPayPlanCompanyTempList;
	}
	
	/**
	 * 生成收入支出计划
	 */
	@Override
	public List<LoanFeesPlan> generateLoanFeesPlan(LoanTransMain loanTransMain) {
		
		Date lendingTime = loanTransMain.getLendingTime();//放款时间
		Short term = loanTransMain.getTerm();//借款期限
		String termUnit = loanTransMain.getTermUnit();//借款期限单位
		String payInterestWay = loanTransMain.getPayInterestWay();//结息方式
		String repaymentMethod = loanTransMain.getRepaymentMethod();//还款方式
		String orderNo = loanTransMain.getBusinessId();//订单编号
	    Date dateOfPayInterest = loanTransMain.getDateOfPayInterest();//付息指定日期
		//收入信息
		List<LoanFeesPlan> feesPlansInList = loanTransMain.getListFeesPlan_in();
		//支出信息
		List<LoanFeesPlan> feesPlansOutList = loanTransMain.getListFeesPlan_out();
		if(feesPlansInList==null && feesPlansOutList==null){
			return null;
		}
		//收入支出列表
		List<LoanFeesPlan> loanFeesPlanList= new ArrayList<>();
		//入库之前根据单号删除信息
		loanFeesPlanMapper.deleteByOrderNo(orderNo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(feesPlansInList!=null){
			for (int i = 0; i < feesPlansInList.size(); i++) {//处理收入信息
				LoanFeesPlan feesPlan = feesPlansInList.get(i);
				feesPlan.setTransType(SystemConst.InOutCome.IN);//设置收入支出类型为收入
				dealInOutInfo(lendingTime, term, termUnit, payInterestWay, repaymentMethod, dateOfPayInterest,loanFeesPlanList, sdf, feesPlan);
			}
		}
		if(feesPlansOutList!=null){
			for (int i = 0; i < feesPlansOutList.size(); i++) {//支出信息
				LoanFeesPlan feesPlan = feesPlansOutList.get(i);
				feesPlan.setTransType(SystemConst.InOutCome.OUT);//设置收入支出类型为收入
				dealInOutInfo(lendingTime, term, termUnit, payInterestWay, repaymentMethod, dateOfPayInterest,loanFeesPlanList, sdf, feesPlan);
			}
		}
		
		return loanFeesPlanList;
	}
	
	/**
	 * 处理收入支出信息
	 * @param lendingTime 放款时间
	 * @param term 期数
	 * @param termUnit 期数单位
	 * @param payInterestWay 付息方式
	 * @param repaymentMethod 还款方式
	 * @param dateOfPayInterest 付息指定日期
	 * @param loanFeesPlanList
	 * @param sdf 日期格式化
	 * @param feesPlan 收入支出信息
	 * @param transWay 收入支出方式
	 */
	private void dealInOutInfo(Date lendingTime, Short term, String termUnit, String payInterestWay,
			String repaymentMethod, Date dateOfPayInterest, List<LoanFeesPlan> loanFeesPlanList, SimpleDateFormat sdf,
			LoanFeesPlan feesPlan) {
		
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		feesPlan.setCreateUser(user.getUserId().toString());//创建人
		feesPlan.setCreateTime(new Date());//创建时间
		feesPlan.setLastUser(user.getUserId().toString());//最后操作人
		feesPlan.setLastModifyTime(new Date());//最后操作时间
		feesPlan.setStatus(SystemConst.Status.STATUS01);
		String transWay = feesPlan.getTransWay();//收入方式
		
		if (SystemConst.transWay.ONCE.equals(transWay)) {//一次性收取
			feesPlan.setPeriod((short)1);
			List<Map<String, Date>> listDate = DateUtils.getStartEndTimeList(repaymentMethod, term, termUnit, lendingTime, dateOfPayInterest);
			Map<String, Date> map = listDate.get(0);
			Date start = map.get("start");
			Date end = map.get("end");
			Date appoint = map.get("appoint");
			feesPlan.setInterestStart(sdf.format(start));
			feesPlan.setInterestEnd(sdf.format(end));
			if (SystemConst.InterestWay.LOAN_DATE.equals(payInterestWay)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(start);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.add(Calendar.SECOND, -1);
				feesPlan.setPlanDoTime(calendar.getTime());
			}else if (SystemConst.InterestWay.DUE_DATE.equals(payInterestWay)) {
				feesPlan.setPlanDoTime(end);
			}else if (SystemConst.InterestWay.SPECIFIED_DATE.equals(payInterestWay)) {
				feesPlan.setPlanDoTime(appoint);
			}
			feesPlan.setPlanDoTime(start);
			loanFeesPlanMapper.insertSelective(feesPlan);//保存到收入支出计划表
			loanFeesPlanList.add(feesPlan);
		}else if(SystemConst.transWay.MONTH.equals(transWay)){//按月收取
			if (SystemConst.RepaymentMethod.ONCE_PAYMENT.equals(repaymentMethod)) {//一次性还款
				//一次性还款不能按月收取
				throw new RuntimeException("一次性还款不能按月收取!");
			}else if (SystemConst.RepaymentMethod.MONTHLY_INTERSETS.equals(repaymentMethod)) {//按月还款
				List<Map<String, Date>> listDate = DateUtils.getStartEndTimeList(repaymentMethod, term, termUnit, lendingTime, dateOfPayInterest);
				for (int j = 0; j < listDate.size(); j++) {
					Map<String, Date> map = listDate.get(j);
					Date start = map.get("start");
					Date end = map.get("end");
					Date appoint = map.get("appoint");
					feesPlan.setPeriod((short)(j+1));
					
					if (SystemConst.InterestWay.DUE_DATE.equals(payInterestWay)) {
						feesPlan.setPlanDoTime(end);
					}else if (SystemConst.InterestWay.LOAN_DATE.equals(payInterestWay)) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(start);
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						calendar.add(Calendar.SECOND, -1);
						feesPlan.setPlanDoTime(calendar.getTime());
					}else if (SystemConst.InterestWay.SPECIFIED_DATE.equals(payInterestWay)) {
						feesPlan.setPlanDoTime(appoint);
					}
					feesPlan.setInterestStart(sdf.format(start));
					feesPlan.setInterestEnd(sdf.format(end));
					
					loanFeesPlanMapper.insertSelective(feesPlan);//保存到收入支出计划表
					loanFeesPlanList.add(feesPlan);
				}	
			}else if (SystemConst.RepaymentMethod.EQUAL_PRINCIPAL_INTEREST.equals(repaymentMethod)) {
				//TODO
			}
		}
	}


	
}

