/**
 * Project Name:loan-service
 * File Name:AccountCustomerManagerService.java
 * Package Name:com.lhhs.loan.service.account
 * Date:2017年8月13日下午2:05:28
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.account;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.LoanBank;

/**
 * 账户和客户管理接口 <br/>
 * Date:     2017年8月13日 下午2:05:28 <br/>
 * @author   dongfei
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface AccountManagerService extends BaseService<LoanAccountInfo>{

	/**
	 * 
	 * saveOrUpdateCard:新增、或者更新账户或者卡信息. <br/>

	 * @param parm
	 * @return "成功：00；失败：01"
	 * @since JDK 1.8
	 */
	String saveOrUpdateCard (LoanAccountCard parm);
	/**
	 * 查询卡信息列表
	 * @param entity
	 * @return
	 */
	List<LoanAccountCard> queryAccountCardList(LoanAccountCard parm);
	
	/**
	 * 查询卡和余额信息列表
	 * @param entity
	 * @return
	 */
	List<LoanAccountCard> queryAccountCardBalList(LoanAccountCard parm);
	
	/**
	 * 查询卡信息分页数据
	 * @param entity
	 * @return
	 */
	public Page queryAccountCardListPage(LoanAccountCard parm);
	/**
	 * 查询账户余额
	 * @param entity
	 * @return
	 */
	List<LoanAccountsTotal> queryAccountsTotalList (LoanAccountsTotal parm);
	/**
	 * 查询账户余额分页数据
	 * @param entity
	 * @return
	 */
	public Page queryAccountsTotalListPage(LoanAccountsTotal parm);
	/**
	 * 
	 * 账户余额查询
	 */
	LoanAccountsTotal getLoanAccountsTotal(String accountId);
	/**
	 * 查询科目列表
	 * @param parm 科目查询实体
	 * @return 科目列表
	 */
	List<LoanAccountsSubjectInfo> queryAccountsSubjectInfoList(LoanAccountsSubjectInfo parm);
	/**
	 * 查询银行列表
	 * @param parm 银行实体
	 * @return 银行列表
	 */
	List<LoanBank> queryLoanBankList(LoanBank parm) ;

}

