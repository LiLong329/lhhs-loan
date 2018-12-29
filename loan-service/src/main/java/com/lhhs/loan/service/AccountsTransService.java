/**
 * Project Name:loan-service
 * File Name:AccountsTransService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月31日上午11:05:02
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.vo.LoanAccountsTransVo;

/**
 * ClassName:AccountsTransService <br/>
 * Function: 账户交易记录 <br/>
 * Date:     2017年7月31日 上午11:05:02 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface AccountsTransService extends BaseService<LoanAccountsTrans> {
	/**
	 * 
	 * 汇总收入金额、支出金额
	 */
	public LoanAccountsTrans querySumAmount(LoanAccountsTrans entity);
	
	/**
	 * queryOrganizationTransList: 
	 * 机构资金交易记录分页数据<br/>
	 * @author chenyinhui
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	public Page queryOrganizationTransList(LoanAccountsTransVo entity);
	
	/**
	 * queryOrganizationTransTotalAmount: 
	 * 根据查询条件统计机构收入总额、支出总额<br/>
	 * @author chenyinhui
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,BigDecimal> queryOrganizationTransTotalAmount(LoanAccountsTransVo entity);
	
}