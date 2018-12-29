/**
 * Project Name:loan-service
 * File Name:EmpQuartersService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年11月6日下午5:27:47
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanEmpQuarters;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.workflow.dao.domain.TaskVo;

/**
 * ClassName:EmpQuartersService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年11月6日 下午5:27:47 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface EmpQuartersService extends BaseService<LoanEmpQuarters>{

	/**
	 * 
	 * queryAllCompany:查询公司
	 * @author suncj
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> queryAllCompany(LoanEmpQuarters entity);

	List<Map<String, Object>> queryAllDept(String companyId);

}

