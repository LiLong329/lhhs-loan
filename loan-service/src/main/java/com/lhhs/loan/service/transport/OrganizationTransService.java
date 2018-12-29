/**
 * Project Name:loan-service
 * File Name:OrganizationTransService.java
 * Package Name:com.lhhs.loan.service.transport
 * Date:2017年6月29日上午8:59:20
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport;

import java.util.Map;

/**
 * ClassName:OrganizationTransService <br/>
 * Function: 与碰碰旺对接组织机构信息<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 上午8:59:20 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface OrganizationTransService {

	/**
	 * 
	 * saveOrganizationInfo:保存更新组织机构信息 <br/>
	 * 适用条件：与碰碰旺系统对接使用<br/>
	 *
	 * @author xiangfeng
	 * @param org
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String, Object> saveOrganizationInfo(String org);
}

