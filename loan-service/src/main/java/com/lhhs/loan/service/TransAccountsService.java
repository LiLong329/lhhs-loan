/**
 * Project Name:loan-service
 * File Name:TransAccountsService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月28日上午11:08:13
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanInvestCustomerInfo;
import com.lhhs.loan.dao.domain.LoanPayPlanCompanyTemp;
import com.lhhs.loan.dao.domain.LoanTransAccounts;

/**
 * ClassName:TransAccountsService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年7月28日 上午11:08:13 <br/>
 * @author   Administrator
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface TransAccountsService extends BaseService<LoanTransAccounts>{

	List<LoanTransAccounts> investCustomerList(Map<String, Object> map,Page page);

	List<LoanTransAccounts> investCustomerExport(Map<String, Object> map);

	LoanTransAccounts selectByPrimaryKey(String customerId);
	
	Map<String, Object> transAccountsAdd(LoanTransAccounts transAccounts);

	/**
	 * 
	 * transAccountsList:(固定理财转账记录). <br/>
	 * @author Administrator
	 * @param map
	 * @param page
	 * @return List
	 * @since JDK 1.8
	 */
	List<LoanTransAccounts> transAccountsList(Map<String, Object> map, Page page);
	/**
	 * get:(固定理财转账记录详情). <br/>
	 * @author Administrator
	 * @param customerId
	 * @return
	 * @since JDK 1.8
	 */
	LoanTransAccounts get(String customerId);

	List<Map<String, Object>> queryAccountCustType();

	List<Map<String, Object>> queryAccountCustProperty(Map<String, Object> map);

	List<LoanDictionary> getCustPropertyByTypeId(Map<String, Object> params);
	/**
	 * transExamineAdd:固定理财转账审核
	 * @author suncj
	 * @param transAccounts
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> transExamineAdd(LoanTransAccounts transAccounts);

	List<Map<String, Object>> queryAccountSubject(Map<String, Object> map);

	/**
	 * 
	 * getAccountsByCustId:转账记账申请-用户id或公司id ---- 检索转出账户信息
	 * @author suncj
	 * @param map
	 * @since JDK 1.8
	 */
	Map<String, Object> getAccountsByCustId(Map<String, Object> map);

	/**
	 * 
	 * getAccountInOutInfo:线下充值提现记账信息查询--by 充值订单编号
	 * @author suncj
	 * @param rechargeOrderNo: 充值订单编号
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> getAccountInOutInfo(String rechargeOrderNo);

	List<LoanPayPlanCompanyTemp> getPayPlanCompanyTemp(String transId);

	int selectByCustId(String custId);

	List<Map<String, Object>> queryAllManager(String accountManagerDepartmentNo);
}

