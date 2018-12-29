/**
 * Project Name:loan-service
 * File Name:BorrowerInfoService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月8日上午11:36:01
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.Map;

import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;

/**
 * ClassName:BorrowerInfoService <br/>
 * Function: 借款人信息操作 <br/>
 * Date:     2017年7月8日 上午11:36:01 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface BorrowerInfoService {
	/**
	 * saveBorrowerBaseInfo:保存借款人基本信息<br/>
	 * @author xiangfeng
	 * @param loanBorrowerInfoWithBLOBs
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String,Object> saveBorrowerBaseInfo(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs);
	
	public LoanCustomerInfo selectByCustomerId(String customerId);
	
}

