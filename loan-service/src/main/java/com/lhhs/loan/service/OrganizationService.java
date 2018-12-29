/**
 * Project Name:loan-service
 * File Name:OrganizationService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年8月4日上午10:35:15
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.LoanOrganization;

/**
 * ClassName:OrganizationService <br/>
 * Function: 机构管理模块服务 <br/>
 * Date:     2017年8月4日 上午10:35:15 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface OrganizationService extends BaseService<LoanOrganization> {
	
	LoanOrganization selectByOrgId(Long orgId);
	
	Map<String, Object> updataOrgInfo(LoanOrganization loanOrganization);
	
}

