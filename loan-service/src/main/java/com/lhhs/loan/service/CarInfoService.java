/**
 * Project Name:loan-service
 * File Name:CarInfoService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年6月29日下午2:52:29
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanCarInfo;

/**
 * ClassName:CarInfoService <br/>
 * Function: 客户管理模块中借款人的车抵押信息接口<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 下午2:52:29 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface CarInfoService {
	/**
	 * 
	 * saveCarInfo:保存借款人的车抵押信息<br/>
	 *
	 * @author xiangfeng
	 * @param carLists
	 * @param custId
	 * @return
	 * @since JDK 1.8
	 */
	public int saveCarInfo(List<LoanCarInfo> carLists,String custId,String customerId);
}

