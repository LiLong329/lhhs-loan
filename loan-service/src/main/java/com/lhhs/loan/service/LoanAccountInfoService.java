/**
 * Project Name:loan-service
 * File Name:LoanAccountInfoService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月31日上午11:05:02
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.vo.LoanAccountInfoVo;

/**
 * ClassName:LoanAccountInfoService <br/>
 * Function: 账户信息查询 <br/>
 * Date:     2017年7月31日 上午11:05:02 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface LoanAccountInfoService extends BaseService<LoanAccountInfoVo> {
	
	/**
	 * 公司资金账户总览分页数据
	 * @param entity
	 * @return
	 */
	public Page queryCompanyAccountList(LoanAccountsTotal entity);
	
	/**
	 * 机构资金账户总览分页数据
	 * @param entity
	 * @return
	 */
	public Page queryOrganizationAccountList(LoanAccountsTotal entity);
	
	/**
	 * queryOrganizationAccountTotalAmount: 
	 * 根据查询条件统计账户总余额<br/>
	 *
	 * @author chenyinhui
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String,Object> queryOrganizationAccountTotalAmount(LoanAccountsTotal entity);

	public LoanAccountInfo selectByOwnerId(String outAccountCustId);
}