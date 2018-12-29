/**
 * Project Name:loan-service
 * File Name:AccountCoreTransServiceImpl.java
 * Package Name:com.lhhs.loan.service.account.impl
 * Date:2017年8月3日下午4:35:29
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.account.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.Direction;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanAccountsSubjectAmountMapper;
import com.lhhs.loan.dao.LoanAccountsSubjectInfoMapper;
import com.lhhs.loan.dao.LoanAccountsTotalMapper;
import com.lhhs.loan.dao.LoanAccountsTransMapper;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectAmount;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.service.account.AccountCoreTransService;

/**
 * 账户原子交易实现
 * Date:     2017年8月3日 下午4:35:29 <br/>
 * @author   dongfei
 * @version
 * @since    JDK 1.8
 * @see
 */
@Transactional(readOnly = false)
@Service
public class AccountCoreTransServiceImpl implements AccountCoreTransService {
private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	
	/**
	 * 
	 * accountChangeTrans:出账入账原子交易

	 * @author dongfei
	 * @param LoanAccountsTrans 交易实体
	 * @return 成功:00;失败:01
	 * @since JDK 1.8
	 */
	@Override
	@Transactional(readOnly = false)
	public String accountTransInOrOut(LoanAccountsTrans loanAccountsTrans) throws Exception {
		String recode=SystemConst.FAIL;
		if(loanAccountsTrans==null){
			//logger.error("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额不能为空");
			throw new Exception("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额不能为空");
			
		}
		//总额
		BigDecimal balance_total=new BigDecimal(0);
		//可用余额
		BigDecimal balance=new BigDecimal(0);
		//动账账号ID
		String accountId=loanAccountsTrans.getAccountId();
		//科目编码
		String subjectId=loanAccountsTrans.getSubjectId();
		//交易金额
		BigDecimal amount=loanAccountsTrans.getAmount();
		if(StringUtils.isEmpty(accountId)|| StringUtils.isEmpty(accountId)||amount==null){
			//logger.error("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额不能为空");
			throw new Exception("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额不能为空");
			
		}

		String direction=loanAccountsTrans.getDirection();
		
		//科目账信息
		LoanAccountsSubjectAmount act_sa_update=new LoanAccountsSubjectAmount();
		act_sa_update.setAccountId(accountId);
		act_sa_update.setSubjectId(subjectId);
		//设置账务日期
		act_sa_update.setBelongDate(DateUtils.getDate());
		//账户余额信息
		LoanAccountsTotal act_total_update=new LoanAccountsTotal();
		//初始化金额字段为0
		act_total_update.init();
		act_total_update.setAccountId(accountId);
		act_total_update.setBelongDate(DateUtils.getDate());
		act_total_update.setLastUser(loanAccountsTrans.getLastUser());
		act_total_update.setCreateUser(loanAccountsTrans.getLastUser());
		act_total_update.setStatus(SystemConst.Status.STATUS03);
		
		act_sa_update.setLastUser(loanAccountsTrans.getLastUser());
		act_sa_update.setCreateUser(loanAccountsTrans.getLastUser());
		act_sa_update.setStatus(SystemConst.Status.STATUS03);
		//查询账户余额
		LoanAccountsTotal loanAccountsTotal=loanAccountsTotalMapper.selectByPrimaryKey(accountId);
		
		if(loanAccountsTotal!=null&&loanAccountsTotal.getAmount()!=null){
			balance=loanAccountsTotal.getBalance();
			balance_total=loanAccountsTotal.getAmount();
		}
		//如果科目为入账
		if(Direction.IN.getId().equals(direction)){
			act_sa_update.setDebitAmount(amount);
			act_sa_update.setCreditAmount(new BigDecimal(0));
			act_total_update.setAmount(amount);
			act_total_update.setBalance(amount);
		//如果科目为出账
		}else if(Direction.OUT.getId().equals(direction)){
			//提现金额大于可用余额抛出错误
			if(SystemConst.SubjectInfo.SUBJECTID_ACCOUNT_OUT.equals(subjectId)&&amount.compareTo(balance)==1){
				logger.error("accountId:"+accountId+",balance："+balance.toString()+",amount:"+amount+",可用余额不足，不能提现。");
				throw new Exception("accountId:"+accountId+",balance："+balance.toString()+",amount:"+amount+",可用余额不足，不能提现。");
			}
			act_sa_update.setDebitAmount(new BigDecimal(0));
			act_sa_update.setCreditAmount(amount);
			act_total_update.setAmount(amount.multiply(new BigDecimal(-1)));
			act_total_update.setBalance(amount.multiply(new BigDecimal(-1)));
		//如果科目为冻结
		}else{
			logger.error("subjectId:"+subjectId+"科目编码对应的交易方向不正确，请检查程序。");
			throw new Exception("subjectId:"+subjectId+"科目编码对应的交易方向不正确，请检查。");
		}
		//查询对应的科目账信息
		LoanAccountsSubjectAmount act_sa_re= loanAccountsSubjectAmountMapper.selectByPrimaryKey(act_sa_update.getId());
		//更新记录标识
		int subject_flag=0;
		//如果存在科目账，进行更新，如果不存在进行插入
		if(act_sa_re==null ||StringUtils.isEmpty(act_sa_re.getId())){
			//科目余额
			act_sa_update.setAmount(act_sa_update.getDebitAmount().subtract(act_sa_update.getCreditAmount()));
			act_sa_update.setCreateTime(new Date());
			act_sa_update.setLastModifyTime(new Date());
			subject_flag=loanAccountsSubjectAmountMapper.insertSelective(act_sa_update);
		}else{
			//设置时间戳
			act_sa_update.setLastModifyTime(act_sa_re.getLastModifyTime());
			subject_flag=loanAccountsSubjectAmountMapper.updateAmount(act_sa_update);
		}
		//如果科目账更新成功，则更新总账户余额
		if(subject_flag>0){
			
			if(loanAccountsTotal==null ||StringUtils.isEmpty(loanAccountsTotal.getAccountId())){
				loanAccountsTotal=new LoanAccountsTotal();
				act_total_update.setLastModifyTime(new Date());
				act_total_update.setCreateTime(new Date());;
				subject_flag=loanAccountsTotalMapper.insertSelective(act_total_update);
				
			}else{
				//设置时间戳
				act_total_update.setLastModifyTime(loanAccountsTotal.getLastModifyTime());
				subject_flag=loanAccountsTotalMapper.updateAmount(act_total_update);
				
			}
			
		}
		//如果更新、插入失败抛出异常回滚
		if(subject_flag>0){
			//设置账户余额
			loanAccountsTrans.setAmountTotal(balance_total.add(act_total_update.getAmount()));
			loanAccountsTrans.setBalance(balance.add(act_total_update.getBalance()));
			recode=SystemConst.SUCCESS;
		}else{
			//设置账户余额
			loanAccountsTrans.setAmountTotal(balance_total);
			loanAccountsTrans.setBalance(balance);
			//logger.error("accountId:"+accountId+",subjectId:"+subjectId+",amount:"+amount.toString()+" 出账入账失败，请重试。");
			throw new Exception("accountId:"+accountId+",subjectId:"+subjectId+",amount:"+amount.toString()+" 出账入账失败，请重试。");
		}
		return recode;
	}
	/**
	 * 
	 * accountChangeTrans:冻结、解冻原子交易
	 * @author dongfei
	 * @param LoanAccountsTrans 交易实体
	 * @return
	 * @since JDK 1.8
	 */
	@Override
	@Transactional(readOnly = false)
	public String accountTransFreeze(LoanAccountsTrans loanAccountsTrans)throws Exception {
		String recode=SystemConst.FAIL;
		
		if(loanAccountsTrans==null){
			logger.error("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额不能为空");
			throw new Exception("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额不能为空");
			
		}
		
		//动账账号ID
		String accountId=loanAccountsTrans.getAccountId();
		//科目编码
		String subjectId=loanAccountsTrans.getSubjectId();
		//交易金额
		BigDecimal amount=loanAccountsTrans.getAmount();
		
		BigDecimal balance=new BigDecimal(0);
		BigDecimal balance_total=new BigDecimal(0);
		
		
		String transType=loanAccountsTrans.getTransType();
		//判断交易类型是否为冻结、解冻
		if(!SystemConst.TransType.TYPEID1011.equals(transType)
				&&!SystemConst.TransType.TYPEID1012.equals(transType)){
			logger.error("transType:"+transType+" 交易类型无效。");
			throw new Exception("transType:"+transType+" 交易类型无效。");
		}
		
		if(StringUtils.isEmpty(accountId)|| StringUtils.isEmpty(accountId)||amount==null){
			logger.error("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额不能为空");
			throw new Exception("accountChangeTrans error 动账交易错误：动账账号ID、科目编码、交易金额不能为空");
			
		}
		
		
		//交易方向入账、出账
		String direction=loanAccountsTrans.getDirection();
		
		//科目账信息
		LoanAccountsSubjectAmount act_sa_update=new LoanAccountsSubjectAmount();
		act_sa_update.setAccountId(accountId);
		act_sa_update.setSubjectId(subjectId);
		act_sa_update.setStatus(SystemConst.Status.STATUS03);
		act_sa_update.setLastUser(loanAccountsTrans.getLastUser());
		act_sa_update.setCreateUser(loanAccountsTrans.getLastUser());
		//设置账务日期
		act_sa_update.setBelongDate(DateUtils.getDate());
		//账户余额信息
		LoanAccountsTotal act_total_update=new LoanAccountsTotal();
		//初始化金额字段为0
		act_total_update.init();
		act_total_update.setAccountId(accountId);
		act_total_update.setBelongDate(DateUtils.getDate());
		act_total_update.setStatus(SystemConst.Status.STATUS03);
		act_total_update.setLastUser(loanAccountsTrans.getLastUser());
		act_total_update.setCreateUser(loanAccountsTrans.getLastUser());
		
		
		//查询账户余额信息
		LoanAccountsTotal loanAccountsTotal=loanAccountsTotalMapper.selectByPrimaryKey(accountId);
		BigDecimal balance_temp=new BigDecimal(0);
		if(loanAccountsTotal!=null&&loanAccountsTotal.getBalance()!=null){
			balance_temp=loanAccountsTotal.getBalance();
		}
		//提现金额大于可用余额抛出错误
		if(SystemConst.TransType.TYPEID1011.equals(transType)
				&&SystemConst.SubjectInfo.SUBJECTID_FREEZEWITHDRAWALS.equals(subjectId)
				&&amount.compareTo(balance_temp)==1){
			logger.error("accountId:"+accountId+",balance_temp："+balance_temp.toString()+",amount:"+amount+",可用余额不足，不能提现冻结。");
			throw new Exception("提现金额大于可用余额。");
		}
		
		//如果科目方向为冻结
		if(Direction.F.getId().equals(direction)){
			//解冻
			if(SystemConst.TransType.TYPEID1012.equals(transType)){
				act_sa_update.setDebitAmount(new BigDecimal(0));
				act_sa_update.setCreditAmount(amount);
				//余额增加
				act_total_update.setBalance(amount);
				//冻结减少
				//act_total_update.setFreezeTotal(amount.multiply(new BigDecimal(-1)));
				
				//理财冻结科目金额减少
				if(SystemConst.SubjectInfo.SUBJECTID_FREEZEFINANCING.equals(subjectId)){
					act_total_update.setFreezeFinancing(amount.multiply(new BigDecimal(-1)));
				//投资
				}else if(SystemConst.SubjectInfo.SUBJECTID_FREEZEINVEST.equals(subjectId)){
					act_total_update.setFreezeInvest(amount.multiply(new BigDecimal(-1)));
				//提现
				}else if(SystemConst.SubjectInfo.SUBJECTID_FREEZEWITHDRAWALS.equals(subjectId)){
					act_total_update.setFreezeWithdrawals(amount.multiply(new BigDecimal(-1)));
				}
			//冻结
			}else{
				act_sa_update.setDebitAmount(amount);
				act_sa_update.setCreditAmount(new BigDecimal(0));
				//余额减少
				act_total_update.setBalance(amount.multiply(new BigDecimal(-1)));
				//冻结总额增加
				//act_total_update.setFreezeTotal(amount);
				
				//理财冻结科目金额减少
				if(SystemConst.SubjectInfo.SUBJECTID_FREEZEFINANCING.equals(subjectId)){
					act_total_update.setFreezeFinancing(amount);
				//投资
				}else if(SystemConst.SubjectInfo.SUBJECTID_FREEZEINVEST.equals(subjectId)){
					act_total_update.setFreezeInvest(amount);
				//提现
				}else if(SystemConst.SubjectInfo.SUBJECTID_FREEZEWITHDRAWALS.equals(subjectId)){
					act_total_update.setFreezeWithdrawals(amount);
				}
			}
			
		//在途金额 ，钱已经转出去了，还没到达接收放账户。冻结和解冻，交易不改变可用余额，
		}else if(Direction.ON.getId().equals(direction)){
			//解冻出账
			if(SystemConst.TransType.TYPEID1012.equals(transType)){
				act_sa_update.setDebitAmount(new BigDecimal(0));
				act_sa_update.setCreditAmount(amount);
				//在途金额入账
				act_total_update.setTransitTotal(amount.multiply(new BigDecimal(-1)));
			//冻结入账
			}else{
				act_sa_update.setDebitAmount(amount);
				act_sa_update.setCreditAmount(new BigDecimal(0));
				//在途金额入账
				act_total_update.setTransitTotal(amount);
			}
		}else{
			logger.error("subjectId:"+subjectId+"科目编码对应的交易方向不正确，请检查程序。");
			throw new Exception("subjectId:"+subjectId+"科目编码对应的交易方向不正确，请检查。");
		}
		//查询对应的科目账信息
		LoanAccountsSubjectAmount act_sa_re= loanAccountsSubjectAmountMapper.selectByPrimaryKey(act_sa_update.getId());
		
		
		//更新记录标识
		int subject_flag=0;
		//如果存在科目账，进行更新，如果不存在进行插入
		if(act_sa_re==null ||StringUtils.isEmpty(act_sa_re.getId())){
			act_sa_update.setAmount(amount);
			act_sa_update.setLastModifyTime(new Date());
			act_sa_update.setCreateTime(new Date());
			subject_flag=loanAccountsSubjectAmountMapper.insertSelective(act_sa_update);
		}else{
			//设置时间戳
			act_sa_update.setLastModifyTime(act_sa_re.getLastModifyTime());
			subject_flag=loanAccountsSubjectAmountMapper.updateAmount(act_sa_update);
		}
		//如果科目账更新成功，则更新总账户余额
		if(subject_flag>0){
			if(loanAccountsTotal==null ||StringUtils.isEmpty(loanAccountsTotal.getAccountId())){
				act_total_update.setLastModifyTime(new Date());
				act_total_update.setCreateTime(new Date());
				subject_flag=loanAccountsTotalMapper.insertSelective(act_total_update);
			}else{
				balance=loanAccountsTotal.getBalance();
				balance_total=loanAccountsTotal.getAmount();
				//设置时间戳
				act_total_update.setLastModifyTime(loanAccountsTotal.getLastModifyTime());
				subject_flag=loanAccountsTotalMapper.updateAmount(act_total_update);
			}
			
		}
		//如果更新、插入失败抛出异常回滚
		if(subject_flag>0){
			//设置账户余额
			loanAccountsTrans.setAmountTotal(balance_total.add(act_total_update.getAmount()));
			loanAccountsTrans.setBalance(balance.add(act_total_update.getBalance()));
			recode=SystemConst.SUCCESS;
		}else{
			//设置账户余额
			loanAccountsTrans.setAmountTotal(balance_total);
			loanAccountsTrans.setBalance(balance);
			logger.error("accountId:"+accountId+",subjectId:"+subjectId+",amount:"+amount.toString()+" 出账入账失败，请重试。");
			throw new Exception("accountId:"+accountId+",subjectId:"+subjectId+",amount:"+amount.toString()+" 出账入账失败，请重试。");
		}
		return recode;
	}

}

