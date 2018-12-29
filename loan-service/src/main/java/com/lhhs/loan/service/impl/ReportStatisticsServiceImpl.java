/**
 * Project Name:loan-service
 * File Name:ReportStatisticsServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年8月29日下午5:48:17
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
import com.lhhs.loan.dao.LoanPayPlanCompanyMapper;
import com.lhhs.loan.dao.LoanTransAccountsMapper;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanTransAccounts;
import com.lhhs.loan.service.ReportStatisticsService;

/**
 * ClassName:ReportStatisticsServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月29日 下午5:48:17 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
@SuppressWarnings("all")
public class ReportStatisticsServiceImpl implements ReportStatisticsService{

//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportStatisticsServiceImpl.class);
	
	@Autowired
	private LoanPayPlanCompanyMapper loanPayPlanCompanyMapper;
	
	@Override
	public LoanPayPlanCompany get(String id) {
		return null;
	}

	@Override
	public LoanPayPlanCompany get(LoanPayPlanCompany entity) {
		return null;
	}

	@Override
	public List queryList(LoanPayPlanCompany entity) {
		return null;
	}

	@Override
	public int save(LoanPayPlanCompany entity) {
		return 0;
	}

	@Override
	public int update(LoanPayPlanCompany entity) {
		return 0;
	}

	@Override
	public int delete(LoanPayPlanCompany entity) {
		return 0;
	}

	@Override
	public int queryCount(LoanPayPlanCompany entity) {
		return 0;
	}
	
	/**
	 * 固定理财报表统计分页查询
	 * @see com.lhhs.loan.common.service.BaseService#queryListPage(com.lhhs.loan.common.shared.page.BaseObject)
	 */
	@Override
	public Page queryListPage(LoanPayPlanCompany entity) {
		Page page = entity.getPage();
		page.setResultList(loanPayPlanCompanyMapper.queryListPage(entity));
		page.setTotalCount(loanPayPlanCompanyMapper.queryCountByReport(entity));
		return page;
	}

//	@Override
//	public Map<String, BigDecimal> queryTotalAmount(LoanPayPlanCompany entity) {
//		return loanPayPlanCompanyMapper.queryTotalAmountByReport(entity);
//	}

	@Override
	public List<LoanPayPlanCompany> queryReportExport(LoanPayPlanCompany entity) {
		return loanPayPlanCompanyMapper.queryListPage(entity);
	}
}

