package com.lhhs.loan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;
import com.lhhs.loan.dao.domain.LoanTransAccounts;

public interface LoanPayPlanCompanyMapper extends CrudDao<LoanPayPlanCompany>{

    
    List<LoanPayPlanCompany> querypayplanCompany(Map<String,Object> map);

    int querypayplanCompanyCount(Map<String,Object> map);

    List<LoanPayPlanCompany> querypayplanCompanyExport(Map<String,Object> map);
    
    List<LoanPayPlanCompany> queryPayMentList(LoanPayPlanCompany loanPayPlanCompany);
    /**
     * 
     * 汇总应付本金、应付利息、应付总额。
     */
    LoanPayPlanCompany queryPayPlansum(LoanPayPlanCompany parm);
    
    List<LoanPayPlanCompany> queryAll(Map<String,Object> map);
    
    List<LoanPayPlanCompany> querycapitalPrepaid(Map<String,Object> map);

	/**
	 * 汇总应还总额、应还本金、应还利息，都减掉已还的。
	 */
	 
	Map<String, Object> queryTotalAmount(Map<String, Object> map);

    /**
     * getReportList:报表统计查询
     * @author suncj
     * @param loanPayPlanCompany
     * @param page
     * @since JDK 1.8
     */
	List<LoanPayPlanCompany> getReportList(LoanPayPlanCompany loanPayPlanCompany, Page page);

	int getReportListCount(LoanPayPlanCompany loanPayPlanCompany);

	List<LoanPayPlanCompany> queryListPage(LoanPayPlanCompany entity);

	int queryCountByReport(LoanPayPlanCompany entity);

//	Map<String, BigDecimal> queryTotalAmountByReport(LoanPayPlanCompany entity);
//	List<LoanPayPlanCompany> queryListPageByRecord(LoanPayPlanCompany entity);
//	int queryCountByRecord(LoanPayPlanCompany entity);
}