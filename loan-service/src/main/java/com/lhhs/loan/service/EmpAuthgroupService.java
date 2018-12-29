/**
 * Project Name:loan-service
 * File Name:EmpAuthgroupService.java
 * Package Name:com.lhhs.loan.service
 * Date:2018年1月11日上午09:05:02
 * Copyright (c) 2018, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanAuthgroupUser;

/**
 * ClassName:EmpAuthgroupService <br/>
 * Function: 员工数据权限 <br/>
 * Date:     2018年1月11日上午09:05:02 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface EmpAuthgroupService{
	
	List<LoanAuthgroupUser> getAuthGroupByUserId(LoanAuthgroupUser record);
	
}