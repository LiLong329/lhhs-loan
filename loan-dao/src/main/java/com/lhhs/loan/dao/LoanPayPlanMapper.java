package com.lhhs.loan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.excel.PayPlanExcelVo;
import com.lhhs.loan.dao.domain.vo.PayPlanVo;

public interface LoanPayPlanMapper  extends CrudDao<LoanPayPlan>{


	List<PayPlanVo> queryListPage(LoanPayPlan entity);

	Map<String, BigDecimal> queryTotalAmount(LoanPayPlan entity);
	
	LoanPayPlan queryPayPlansum(LoanPayPlan entity);

	List<PayPlanExcelVo> queryPayPlanExcelList(LoanPayPlan entity);
	
	List<LoanPayPlan> queryCapitalList(LoanPayPlan entity);
	
	int queryCapitalCount(LoanPayPlan entity);

	List<PayPlanVo> queryListPageReport(LoanPayPlan entity);

	int queryReportCount(LoanPayPlan entity);

	List<PayPlanVo> queryListPageReportByPerson(LoanPayPlan entity);

	int queryReportCountByPerson(LoanPayPlan entity);

	BigDecimal queryReportTotal(LoanPayPlan entity);

	BigDecimal queryPersonReportTotal(LoanPayPlan entity);

	List<LoanPayPlan> getPayPlanAndCompanyList();
}