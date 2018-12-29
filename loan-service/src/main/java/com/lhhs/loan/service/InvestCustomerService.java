package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;
import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanInvestCustomerInfo;

public interface InvestCustomerService extends BaseService<LoanInvestCustomerInfo>{
	
	/**
	 * 查询理财人信息（银主）列表
	 * @param map
	 * @param page
	 * @return
	 */
	List<LoanInvestCustomerInfo> investCustomerList(Map<String, Object> map,Page page);

	List<LoanInvestCustomerInfo> investCustomerExport(Map<String, Object> map);

	LoanInvestCustomerInfo selectByPrimaryKey(String id);

	List<Map<String, Object>> queryAllDepts();

	int selectByMobile(String mobile);

	List<Map<String, Object>> queryAllCompany(Map<String, Object> paramsMap);

	List<Map<String, Object>> queryAllManager(String affiliatedCompanyId);

}