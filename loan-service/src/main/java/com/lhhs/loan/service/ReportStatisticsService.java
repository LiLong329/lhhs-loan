/**
 * Project Name:loan-service
 * File Name:ReportStatisticsService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年8月29日下午5:47:40
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanTransAccounts;

/**
 * ClassName:ReportStatisticsService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月29日 下午5:47:40 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface ReportStatisticsService extends BaseService<LoanPayPlanCompany>{

	
	/**
	 * queryListPage:报表统计功能  分页查询
	 * @author suncj
	 * @param loanPayPlanCompany
	 * @param page
	 * @return
	 * @since JDK 1.8
	 */
	Page queryListPage(LoanPayPlanCompany entity);

	List<LoanPayPlanCompany> queryReportExport(LoanPayPlanCompany entity);

}

