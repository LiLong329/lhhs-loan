/**
 * Project Name:loan-service
 * File Name:PayPlanService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月28日上午9:47:24
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
import com.lhhs.loan.dao.domain.excel.PayPlanExcelVo;

/**
 * ClassName:PayPlanService <br/>
 * Function: 还款管理模块 <br/>
 * Date:     2017年7月28日 上午9:47:24 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface PayPlanService extends BaseService<LoanPayPlan>{

	/**
	 * queryTotalAmount: 
	 * 根据查询条件统计应还总额<br/>
	 * 根据查询条件统计应还本金总额<br/>
	 * 根据查询条件统计应还利息总额<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,BigDecimal> queryTotalAmount(LoanPayPlan entity);
	/**
	 * queryPayPlanExport:
	 * 查询待还款计划导出列表<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<PayPlanExcelVo> queryPayPlanExport(LoanPayPlan entity);
	
	Page queryListPageReport(LoanPayPlan entity);
	Page queryListPageReportByPerson(LoanPayPlan entity);
	BigDecimal queryPersonReportTotal(LoanPayPlan entity);
	BigDecimal queryReportTotal(LoanPayPlan entity);
}

