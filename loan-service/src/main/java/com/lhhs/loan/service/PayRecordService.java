/**
 * Project Name:loan-service
 * File Name:PayRecordService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月31日上午11:05:02
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.math.BigDecimal;
import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.LoanPayRecord;

/**
 * ClassName:PayRecordService <br/>
 * Function: 已还清记录查询服务 <br/>
 * Date:     2017年7月31日 上午11:05:02 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface PayRecordService extends BaseService<LoanPayRecord> {

	/**
	 * queryTotalAmount：
	 * 根据查询条件查询已还款总额 <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,BigDecimal> queryTotalAmount(LoanPayRecord entity);

}

