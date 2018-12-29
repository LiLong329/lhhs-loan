/**
 * Project Name:loan-service
 * File Name:TransMainServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年8月1日下午4:09:38
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
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.LoanTransMainMapper;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.excel.AdvancePayFinishedExcelVo;
import com.lhhs.loan.dao.domain.excel.PayFinishedExcelVo;
import com.lhhs.loan.dao.domain.vo.LoanTransRecordVo;
import com.lhhs.loan.service.TransMainService;

/**
 * ClassName:TransMainServiceImpl <br/>
 * Function: 放款账务主表（放款、还款、付款）服务类<br/>
 * Date:     2017年8月1日 下午4:09:38 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class TransMainServiceImpl implements TransMainService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransMainServiceImpl.class);
	
	@Autowired
	private LoanTransMainMapper loanTransMainMapper;

	@Override
	public LoanTransMain get(String id) {
		return null;
	}

	@Override
	public LoanTransMain get(LoanTransMain entity) {
		return loanTransMainMapper.getByEntity(entity);
	}

	@Override
	public List queryList(LoanTransMain entity) {
		return null;
	}

	@Override
	public Page queryListPage(LoanTransMain entity) {
		Page page = entity.getPage();
		page.setResultList(loanTransMainMapper.queryListPage(entity));
		page.setTotalCount(loanTransMainMapper.queryCount(entity));
		return page;
	}

	@Override
	public int queryCount(LoanTransMain entity) {
		return 0;
	}

	@Override
	public Map<String,BigDecimal> queryTotalAmount(LoanTransMain entity) {
		return loanTransMainMapper.queryTotalAmount(entity);
	}

	@Override
	public int save(LoanTransMain entity) {
		return 0;
	}

	@Override
	public int update(LoanTransMain entity) {
		return 0;
	}

	@Override
	public int delete(LoanTransMain entity) {
		return 0;
	}

	@Override
	public List<PayFinishedExcelVo> queryPayFinishedExport(LoanTransMain entity) {
		return loanTransMainMapper.queryPayFinishedExcelList(entity);
	}

	@Override
	public List<AdvancePayFinishedExcelVo> queryAdvancePayFinishedExport(LoanTransMain entity) {
		return loanTransMainMapper.queryAdvancePayFinishedExcelList(entity);
	}

	@Override
	public Page queryBusinessListPage(LoanTransRecordVo entity) {
		Page page = entity.getPage();
		if(!StringUtil.isEmpty(entity.getField5()) && entity.getField5().equals("noPage")){
			entity.setPage(null);
		}
		page.setResultList(loanTransMainMapper.queryBusinessListPage(entity));
		page.setTotalCount(loanTransMainMapper.queryBusinessCount(entity));
		return page;
	}

	@Override
	public Page queryBorrowerListPage(LoanTransRecordVo entity) {
		Page page = entity.getPage();
		if(!StringUtil.isEmpty(entity.getField5()) && entity.getField5().equals("noPage")){
			entity.setPage(null);
		}
		page.setResultList(loanTransMainMapper.queryBorrowerListPage(entity));
		page.setTotalCount(loanTransMainMapper.queryBorrowerCount(entity));
		return page;
	}

	@Override
	public Page queryLoanerListPage(LoanTransRecordVo entity) {
		Page page = entity.getPage();
		if(!StringUtil.isEmpty(entity.getField5()) && entity.getField5().equals("noPage")){
			entity.setPage(null);
		}
		page.setResultList(loanTransMainMapper.queryLoanerListPage(entity));
		page.setTotalCount(loanTransMainMapper.queryLoanerCount(entity));
		return page;
	}

	@Override
	public List<String> queryDepartment() {
		return loanTransMainMapper.queryDepartment();
	}

	@Override
	public BigDecimal queryBorrowerList(LoanTransRecordVo entity) {
		return loanTransMainMapper.queryBorrowerList(entity);
	}

	@Override
	public BigDecimal queryBusinessList(LoanTransRecordVo entity) {
		return loanTransMainMapper.queryBusinessList(entity);
	}

	@Override
	public BigDecimal queryLoanerList(LoanTransRecordVo entity) {
		return loanTransMainMapper.queryLoanerList(entity);
	}


	@Override
	public Page queryListPageByTask(LoanTransMain entity) {
		Page page = entity.getPage();
		page.setResultList(loanTransMainMapper.queryListPageByTask(entity));
		page.setTotalCount(loanTransMainMapper.queryListPageByTaskCount(entity));
		return page;
	}

	@Override
	public List<Map<String, Object>> queryTransAmount(Map<String, Object> map) {
		return loanTransMainMapper.queryTransAmount(map);
	}
}