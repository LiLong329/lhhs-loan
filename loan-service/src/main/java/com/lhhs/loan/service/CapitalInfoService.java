/**
 * Project Name:loan-service
 * File Name:CapitalInfoService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年6月29日下午2:52:29
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

/**
 * ClassName:CapitalInfoService <br/>
 * Function: 报单资金方信息<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 下午2:52:29 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface CapitalInfoService {
	
	/**
	 * 
	 * saveCapitalInfo更新本次放款金额或已放款金额:<br/>
	 *	
	 * @author chenyinhui
	 * @param type
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String, Object> updateCapitalInfo(int type,String orderNo);
	
}

