/**
 * Project Name:loan-service
 * File Name:LoanRecordService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月31日上午11:05:02
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.LoanRecord;
import com.lhhs.loan.dao.domain.LoanRecordTemp;
import com.lhhs.loan.dao.domain.vo.LoanRecordVo;

/**
 * ClassName:LoanRecordService <br/>
 * Function: 放款记录查询 <br/>
 * Date:     2017年7月31日 上午11:05:02 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface LoanRecordService extends BaseService<LoanRecordVo> {
	
	/**
	 * queryTotalAmount: 
	 * 根据查询条件统计放款总金额<br/>
	 *
	 * @author chenyinhui
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,BigDecimal> queryTotalAmount(LoanRecordVo entity);
	
	/**
	 * queryLoanRecordByOrderNo: 
	 *  根据订单号组装放款申请数据<br/>
	 *
	 * @author chenyinhui
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanRecordTemp> queryLoanRecordByOrderNo(String orderNo);

	List<Map<String,Object>> queryMonthStatisticsList(LoanRecordVo entity);

	BigDecimal queryWeekStatisticsList(LoanRecordVo entity);

	List<Map<String,Object>> queryDayStatisticsList(LoanRecordVo entity);
	
}