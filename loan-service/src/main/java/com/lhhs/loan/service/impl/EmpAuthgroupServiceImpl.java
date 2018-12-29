/**
 * Project Name:loan-service
 * File Name:EmpAuthgroupServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2018年1月11日上午9:32:11
 * Copyright (c) 2018, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.dao.LoanAuthgroupUserMapper;
import com.lhhs.loan.dao.domain.LoanAuthgroupUser;
import com.lhhs.loan.service.EmpAuthgroupService;

/**
 * ClassName:EmpAuthgroupServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2018年1月11日 上午9:32:11 <br/>
 * @author   chen
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class EmpAuthgroupServiceImpl implements EmpAuthgroupService{

	@Autowired
	private LoanAuthgroupUserMapper loanAuthgroupUserMapper;
	
	@Override
	public List<LoanAuthgroupUser> getAuthGroupByUserId(LoanAuthgroupUser record) {
		return loanAuthgroupUserMapper.getAuthGroupByUserId(record);
	}

}

