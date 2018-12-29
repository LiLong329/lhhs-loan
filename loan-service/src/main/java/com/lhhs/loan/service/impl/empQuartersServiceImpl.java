/**
 * Project Name:loan-service
 * File Name:empQuartersServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年11月6日下午5:36:30
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanEmpQuartersMapper;
import com.lhhs.loan.dao.LoanPayPlanMapper;
import com.lhhs.loan.dao.domain.LoanEmpQuarters;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.service.EmpQuartersService;
import com.lhhs.loan.service.PayPlanService;

/**
 * ClassName:empQuartersServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年11月6日 下午5:36:30 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
@SuppressWarnings("all")
public class empQuartersServiceImpl implements EmpQuartersService {

	
	@Autowired
	private LoanEmpQuartersMapper loanEmpQuartersMapper;
	
	/**
	 * 员工岗位列表分页查询
	 * @see com.lhhs.loan.common.service.BaseService#queryListPage(com.lhhs.loan.common.shared.page.BaseObject)
	 */
	@Override
	public Page queryListPage(LoanEmpQuarters entity) {
		Page page = entity.getPage();
		page.setResultList(loanEmpQuartersMapper.queryListPage(entity));
		page.setTotalCount(loanEmpQuartersMapper.queryCount(entity));
		return page;
	}
	
	
	@Override
	public List<Map<String, Object>> queryAllDept(String companyId) {
		return loanEmpQuartersMapper.queryAllDept(companyId);
	}
	

	@Override
	public List<Map<String, Object>> queryAllCompany(LoanEmpQuarters entity) {
		return loanEmpQuartersMapper.queryAllCompany(entity);
	}

	
	
	@Override
	public LoanEmpQuarters get(String id) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanEmpQuarters get(LoanEmpQuarters entity) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List queryList(LoanEmpQuarters entity) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(LoanEmpQuarters entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LoanEmpQuarters entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoanEmpQuarters entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(LoanEmpQuarters entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

}

