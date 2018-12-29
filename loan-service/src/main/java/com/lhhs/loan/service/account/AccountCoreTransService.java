/**
 * Project Name:loan-service
 * File Name:AccountCoreTransactionService.java
 * Package Name:com.lhhs.loan.service.account
 * Date:2017年8月3日下午4:32:24
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.account;

import com.lhhs.loan.dao.domain.LoanAccountsTrans;

/**
 * 账户原子交易接口
 * Date:     2017年8月3日 下午4:32:24 <br/>
 * @author   dongfei
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface AccountCoreTransService {
	/**
	 * 
	 * accountChangeTrans:出账入账原子交易

	 * @author dongfei
	 * @param LoanAccountsTrans 交易实体
	 * @return
	 * @since JDK 1.8
	 */
	public String accountTransInOrOut(LoanAccountsTrans loanAccountsTrans)throws Exception ;
	/**
	 * 
	 * accountChangeTrans:冻结、解冻原子交易

	 * @author dongfei
	 * @param LoanAccountsTrans 交易实体
	 * @return
	 * @since JDK 1.8
	 */
	public String accountTransFreeze(LoanAccountsTrans loanAccountsTrans)throws Exception ;
	
}

