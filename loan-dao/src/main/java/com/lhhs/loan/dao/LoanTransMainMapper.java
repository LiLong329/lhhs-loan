package com.lhhs.loan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanRecord;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.excel.AdvancePayFinishedExcelVo;
import com.lhhs.loan.dao.domain.excel.PayFinishedExcelVo;
import com.lhhs.loan.dao.domain.vo.LoanTransRecordVo;

public interface LoanTransMainMapper extends CrudDao<LoanTransMain>{


	List<LoanTransMain> queryListPage(LoanTransMain entity);

	Map<String,BigDecimal> queryTotalAmount(LoanTransMain entity);

	LoanTransMain getByEntity(LoanTransMain entity);
	
	/**
	 * 把还款计划临时表记录插入正式表
	 * @param entity
	 * @return
	 */
    int insertLoanPayPlan(LoanTransMain entity);
    
	/**
	 * 把待付款计划临时表记录插入正式表
	 * @param entity
	 * @return
	 */
    int insertLoanPayPlanCompany(LoanTransMain entity);
    
	/**
	 * 把放款记录临时表记录插入正式表
	 * @param entity
	 * @return
	 */
    int insertLoanRecord(LoanTransMain entity);
    
	/**
	 * 更新费用状态、账务主表ID
	 * @param entity
	 * @return
	 */
    int updateLoanFeesPlan(LoanTransMain entity);
    /**
     * 
     * 更新付款计划field3客户性质
     */
    int updateLoanPayPlanCompany(LoanTransMain entity);
	List<LoanTransMain> querySettleList(Map<String, Object> map);
	
	Integer querySettleListCount(Map<String, Object> map);
	
	List<LoanTransMain> querySettleListExport(Map<String, Object> map);
	
	Map<String, Object> queryPaidTotalAmount(Map<String, Object> map);
	
	List<Map<String, Object>> querySettleDetail(String orderNo);

	List<PayFinishedExcelVo> queryPayFinishedExcelList(LoanTransMain entity);

	List<AdvancePayFinishedExcelVo> queryAdvancePayFinishedExcelList(LoanTransMain entity);
	
	LoanRecord queryLoanRecordTempSum(LoanRecord entity);

	List<LoanTransRecordVo> queryBusinessListPage(LoanTransRecordVo entity);

	int queryBusinessCount(LoanTransRecordVo entity);

	List<LoanTransRecordVo> queryBorrowerListPage(LoanTransRecordVo entity);

	int queryBorrowerCount(LoanTransRecordVo entity);

	List<LoanTransRecordVo> queryLoanerListPage(LoanTransRecordVo entity);

	int queryLoanerCount(LoanTransRecordVo entity);

	List<String> queryDepartment();
	
	BigDecimal queryBorrowerList(LoanTransRecordVo entity);
	
	BigDecimal queryBusinessList(LoanTransRecordVo entity);
	
	BigDecimal queryLoanerList(LoanTransRecordVo entity);

	List<Map<String, Object>> queryTransAmount(Map<String, Object> map);

	List<LoanTransMain> queryListPageByTask(LoanTransMain entity);

	int queryListPageByTaskCount(LoanTransMain entity);
}