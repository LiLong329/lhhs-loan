/**
 * Project Name:loan-service
 * File Name:TransMainService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年8月1日下午3:54:05
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
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.excel.AdvancePayFinishedExcelVo;
import com.lhhs.loan.dao.domain.excel.PayFinishedExcelVo;
import com.lhhs.loan.dao.domain.vo.LoanTransRecordVo;

/**
 * ClassName:TransMainService <br/>
 * Function: 放款账务主表（放款、还款、付款）服务类<br/>
 * Date:     2017年8月1日 下午3:54:05 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface TransMainService extends BaseService<LoanTransMain> {

	/**
	 * queryTotalAmount:
	 * 查询还清总额(已还本金总额+已还利息总额+已还逾期利息总额+已还违约赔偿金总额) <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,BigDecimal> queryTotalAmount(LoanTransMain entity);

	/**
	 * queryPayFinishedExport:<br/>
	 * 已还清记录列表<br/>
	 * 导出列表
	 * 
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<PayFinishedExcelVo> queryPayFinishedExport(LoanTransMain entity);

	/**
	 * queryAdvancePayFinishedExport:
	 * 提前还款记录列表<br/>
	 * 导出列表
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<AdvancePayFinishedExcelVo> queryAdvancePayFinishedExport(LoanTransMain entity);
	
	
	/**
	 * 从事业部维度查询数据
	 */
	public Page queryBusinessListPage(LoanTransRecordVo entity);
	
	/**
	 * 从借款人维度查询数据
	 */
	public Page queryBorrowerListPage(LoanTransRecordVo entity);
	
	/**
	 * 从放款人维度查询数据
	 */
	public Page queryLoanerListPage(LoanTransRecordVo entity);
	
	/**
	 * 查询部门
	 */
	public List<String> queryDepartment();
	
	BigDecimal queryBorrowerList(LoanTransRecordVo entity);

    BigDecimal queryBusinessList(LoanTransRecordVo entity);
	
	BigDecimal queryLoanerList(LoanTransRecordVo entity);

	Page queryListPageByTask(LoanTransMain entity);


	List<Map<String, Object>> queryTransAmount(Map<String, Object> map);
	
}