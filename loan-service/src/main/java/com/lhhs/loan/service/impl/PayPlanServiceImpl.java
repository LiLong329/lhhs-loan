/**
 * Project Name:loan-service
 * File Name:PayPlanServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月28日下午2:55:57
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanPayPlanMapper;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.excel.PayPlanExcelVo;
import com.lhhs.loan.dao.domain.vo.PayPlanVo;
import com.lhhs.loan.service.PayPlanService;

/**
 * ClassName:PayPlanServiceImpl <br/>
 * Function: 还款管理模块 <br/>
 * Date:     2017年7月28日 下午2:55:57 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
@SuppressWarnings("all")
public class PayPlanServiceImpl implements PayPlanService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayPlanServiceImpl.class);
	
	@Autowired
	private LoanPayPlanMapper loanPayPlanMapper;

	@Override
	public LoanPayPlan get(String id) {
		return loanPayPlanMapper.selectByPrimaryKey(id);
	}

	@Override
	public LoanPayPlan get(LoanPayPlan entity) {
		return null;
	}

	@Override
	public List queryList(LoanPayPlan entity) {
		return loanPayPlanMapper.queryList(entity);
	}

	/**
	 * 还款计划表分页查询
	 * @see com.lhhs.loan.common.service.BaseService#queryListPage(com.lhhs.loan.common.shared.page.BaseObject)
	 */
	@Override
	public Page queryListPage(LoanPayPlan entity) {
		Page page = entity.getPage();
		page.setResultList(loanPayPlanMapper.queryListPage(entity));
		page.setTotalCount(loanPayPlanMapper.queryCount(entity));
		return page;
	}



	@Override
	public int queryCount(LoanPayPlan entity) {
		return 0;
	}

	@Override
	public Map<String, BigDecimal> queryTotalAmount(LoanPayPlan entity) {
		return loanPayPlanMapper.queryTotalAmount(entity);
	}

	@Override
	public int save(LoanPayPlan entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LoanPayPlan entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoanPayPlan entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PayPlanExcelVo> queryPayPlanExport(LoanPayPlan entity) {
		return loanPayPlanMapper.queryPayPlanExcelList(entity);
	}

	@Override
	public Page queryListPageReport(LoanPayPlan entity) {
		Page page = entity.getPage();
		page.setResultList(loanPayPlanMapper.queryListPageReport(entity));
		page.setTotalCount(loanPayPlanMapper.queryReportCount(entity));
		return page;
	}

	@Override
	public Page queryListPageReportByPerson(LoanPayPlan entity) {
		Page page = null;
		if(entity.getPage()!=null){
			page=new Page(entity.getPage().getPageIndex(), entity.getPage().getPageSize());
		}else{
			page=new Page();
		}
		page.setResultList(loanPayPlanMapper.queryListPageReportByPerson(entity));
		page.setTotalCount(loanPayPlanMapper.queryReportCountByPerson(entity));
		return page;
	}

	@Override
	public BigDecimal queryReportTotal(LoanPayPlan entity) {
		return loanPayPlanMapper.queryReportTotal(entity);
	}
	@Override
	public BigDecimal queryPersonReportTotal(LoanPayPlan entity) {
		return loanPayPlanMapper.queryPersonReportTotal(entity);
	}

}

