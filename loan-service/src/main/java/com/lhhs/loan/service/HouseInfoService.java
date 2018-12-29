/**
 * Project Name:loan-service
 * File Name:HouseInfoService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年6月29日下午2:50:35
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanHouseInfo;

/**
 * ClassName:HouseInfoService <br/>
 * Function: 客户管理模块中借款人的房抵押信息接口<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 下午2:50:35 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface HouseInfoService {
	/**
	 * 
	 *saveHouseInfo:保存借款人的房抵押信息<br/>
	 * @author xiangfeng
	 * @param houseLists
	 * @param custId
	 * @return
	 * @since JDK 1.8
	 */
	public int saveHouseInfo(List<LoanHouseInfo> houseLists,String custId,String customerId);
}

