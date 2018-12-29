/**
 * Project Name:loan-service
 * File Name:AccountManagerServiceImpl.java
 * Package Name:com.lhhs.loan.service.account.impl
 * Date:2017年8月13日下午3:20:38
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.account.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanAccountsSubjectInfoMapper;
import com.lhhs.loan.dao.LoanAccountsTotalMapper;
import com.lhhs.loan.dao.LoanBankMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.LoanBank;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.account.AccountManagerService;

/**
 * 账户信息管理实现
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月13日 下午3:20:38 <br/>
 * @author   dongfei
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class AccountManagerServiceImpl extends CrudService<LoanAccountInfoMapper,LoanAccountInfo>  implements AccountManagerService{
	//账户余额汇总 Mapper
	@Autowired
	private LoanAccountsTotalMapper loanAccountsTotalMapper;
	//卡信息
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	//科目信息Mapper
	@Autowired
	private LoanAccountsSubjectInfoMapper loanAccountsSubjectInfoMapper;
	@Autowired
	private LoanBankMapper loanBankMapper;
	public String saveOrUpdateCard(LoanAccountCard parm){
		String recode=SystemConst.FAIL;
		if(parm==null)return recode;
		Long cardId=parm.getId();
		String accountId=parm.getAccountId();
		String ownerId=parm.getOwnerId();
		String accountType=parm.getAccountType();
		String unionId =parm.getUnionId();
		
		String bankCardNo=parm.getBankCardNo();
		//手机号小于11位的清空
		if(parm.getMobile()!=null&&parm.getMobile().length()<11){
			parm.setMobile(null);
		}
		//账户编号为空
		if(StringUtils.isEmpty(accountId)){
			if(StringUtils.isEmpty(ownerId)||StringUtils.isEmpty(unionId)||StringUtils.isEmpty(accountType)){
				throw new RuntimeException("账户编号为空时，账户所有者、账户类型、公司、集团，不能为空！");
			}
			LoanAccountInfo act_query=new LoanAccountInfo();
			act_query.setOwnerId(ownerId);
			act_query.setAccountType(accountType);
			act_query.setUnionId(unionId);
			LoanAccountInfo act=get(act_query);
			if(act==null){
				//复制loanPayPlan属性
				accountId=CommonUtils.getAutoIncrement("loan_account_info", accountType.substring(0, 1), 10).toString();
				BeanUtils.copyProperties(parm,act_query);
				act_query.setAccountId(accountId);
				
				save(act_query);
				parm.setAccountId(accountId);
				recode=SystemConst.SUCCESS;
			}else{
				//更新账户信息
				parm.setAccountId(act.getAccountId());
				accountId=act.getAccountId();
				//BeanUtils.copyProperties(parm,act_query);
				
				update(parm);
				recode=SystemConst.SUCCESS;
			}
			
			if(!StringUtils.isEmpty(bankCardNo)){
				LoanAccountCard cardQuery=new LoanAccountCard();
				cardQuery.setAccountId(accountId);
				if(!bankCardNo.equals(parm.getHidBankCardNo())&&parm.getHidBankCardNo()!=null){
					cardQuery.setBankCardNo(parm.getHidBankCardNo());
				}else{
					cardQuery.setBankCardNo(bankCardNo);
				}
				List<LoanAccountCard> list_card=loanAccountCardMapper.queryList(cardQuery);
				if(list_card.size()>0){
					Long card_id= list_card.get(0).getId();
					parm.setId(card_id);
					//设置银行信息
					setLoanBank(parm);
					loanAccountCardMapper.updateByPrimaryKeySelective(parm);
				}else{
					parm.setCreateTime(new Date());
					parm.setStatus(SystemConst.Status.STATUS03);
					//设置银行信息
					setLoanBank(parm);
					loanAccountCardMapper.insertSelective(parm);
				}
			}
			else{
				loanAccountCardMapper.deleteByBankCardNo(parm.getHidBankCardNo());
			}
		}else if(cardId==null){
			LoanAccountCard cardQuery=new LoanAccountCard();
			cardQuery.setAccountId(accountId);
			cardQuery.setBankCardNo(bankCardNo);
			List<LoanAccountCard> list_card=loanAccountCardMapper.queryList(cardQuery);
			if(list_card.size()>0){
				Long card_id= list_card.get(0).getId();
				parm.setId(card_id);
				//设置银行信息
				setLoanBank(parm);
				loanAccountCardMapper.updateByPrimaryKeySelective(parm);
			}else{
				parm.setCreateTime(new Date());
				parm.setStatus(SystemConst.Status.STATUS03);
				//设置银行信息
				setLoanBank(parm);
				loanAccountCardMapper.insertSelective(parm);
			}
			recode=SystemConst.SUCCESS;
		}else{
			LoanAccountCard cardQuery=new LoanAccountCard();
			cardQuery.setId(cardId);
			List<LoanAccountCard> list_card=loanAccountCardMapper.queryList(cardQuery);
			if(list_card.size()>0){
				Long card_id= list_card.get(0).getId();
				parm.setId(card_id);
				//设置银行信息
				setLoanBank(parm);
				loanAccountCardMapper.updateByPrimaryKeySelective(parm);
			}else{
				parm.setCreateTime(new Date());
				parm.setStatus(SystemConst.Status.STATUS03);
				//设置银行信息
				setLoanBank(parm);
				loanAccountCardMapper.insertSelective(parm);
			}
			recode=SystemConst.SUCCESS;
			
		}
		return recode;
	}
	private void setLoanBank(LoanAccountCard parm){
		if(parm!=null && StringUtils.isEmpty(parm.getBankId())
				&&!StringUtils.isEmpty(parm.getBankName())){
			LoanBank loanBank=new LoanBank();
			loanBank.setBankName(parm.getBankName());
			List<LoanBank> list=queryLoanBankList(loanBank);
			if(list!=null&&list.size()>0){
				LoanBank temp=list.get(0);
				parm.setBankId(temp.getBankId());
				parm.setBankAb(temp.getBankAb());
				parm.setBankName(temp.getBankName());
			}
			
		}
	}
	/**
	 * 查询卡信息
	 * @param entity
	 * @return
	 */
	@Override
	public List<LoanAccountCard> queryAccountCardList(LoanAccountCard parm) {
		
		List<LoanAccountCard> list_card=loanAccountCardMapper.queryList(parm);
		return list_card;
	}
	
	/**
	 * 查询卡和余额信息列表
	 * @param entity
	 * @return
	 */
	public List<LoanAccountCard> queryAccountCardBalList(LoanAccountCard parm){
		List<LoanAccountCard> list_card=loanAccountCardMapper.queryCardAndBalList(parm);
		return list_card;
	}
	/**
	 * 查询卡信息分页数据
	 * @param entity
	 * @return
	 */
	@Override
	public Page queryAccountCardListPage(LoanAccountCard parm) {
		Page result =new Page();
		result.setResultList(loanAccountCardMapper.queryList(parm));
		result.setTotalCount(loanAccountCardMapper.queryCount(parm));
		return result;
	}
	/**
	 * 查询账户余额
	 * @param entity
	 * @return
	 */
	@Override
	public List<LoanAccountsTotal> queryAccountsTotalList(LoanAccountsTotal parm) {
		List<LoanAccountsTotal> list=loanAccountsTotalMapper.queryList(parm);
		return list;
		
	}
	/**
	 * 查询账户余额分页数据
	 * @param entity
	 * @return
	 */
	@Override
	public Page queryAccountsTotalListPage(LoanAccountsTotal parm) {
		Page result =new Page();
		result.setResultList(loanAccountsTotalMapper.queryList(parm));
		result.setTotalCount(loanAccountsTotalMapper.queryCount(parm));
		return result;
	}
	/**
	 * 查询单个账户余额
	 * @param entity
	 * @return
	 */
	@Override
	public LoanAccountsTotal getLoanAccountsTotal(String accountId) {
		if(StringUtils.isEmpty(accountId)){
			return null;
		}
		LoanAccountsTotal parm=new LoanAccountsTotal();
		parm.setAccountId(accountId);
		List<LoanAccountsTotal> list=queryAccountsTotalList(parm);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 查询科目列表
	 * @param parm 科目查询实体
	 * @return 科目列表
	 */
	@Override
	public List<LoanAccountsSubjectInfo> queryAccountsSubjectInfoList(LoanAccountsSubjectInfo parm) {
		if(parm==null){
			return null;
		}
		List<LoanAccountsSubjectInfo> list=loanAccountsSubjectInfoMapper.queryList(parm);
		return list;
	}
	
	/**
	 * 查询银行列表
	 * @param parm 银行实体
	 * @return 银行列表
	 */
	@Override
	public List<LoanBank> queryLoanBankList(LoanBank parm) {
		List<LoanBank> list=loanBankMapper.queryList(parm);
		return list;
	}

}

