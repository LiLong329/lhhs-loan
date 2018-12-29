/**
 * Project Name:loan-service
 * File Name:PayRecordServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月31日上午11:27:45
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
import com.lhhs.loan.dao.LoanPayRecordMapper;
import com.lhhs.loan.dao.domain.LoanPayRecord;
import com.lhhs.loan.service.PayRecordService;

/**
 * ClassName:PayRecordServiceImpl <br/>
 * Function: 已还清记录服务 <br/>
 * Date:     2017年7月31日 上午11:27:45 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class PayRecordServiceImpl implements PayRecordService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayRecordServiceImpl.class);
	
	@Autowired
	private LoanPayRecordMapper loanPayRecordMapper;

	@Override
	public LoanPayRecord get(String id) {

		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public LoanPayRecord get(LoanPayRecord entity) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List queryList(LoanPayRecord entity) {
		return loanPayRecordMapper.queryList(entity);
	}

	@Override
	public Page queryListPage(LoanPayRecord entity) {
		Page page = entity.getPage();
		page.setResultList(loanPayRecordMapper.queryListPage(entity));
		page.setTotalCount(loanPayRecordMapper.queryCount(entity));
		return page;
	}



	@Override
	public int queryCount(LoanPayRecord entity) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, BigDecimal> queryTotalAmount(LoanPayRecord entity) {
		return loanPayRecordMapper.queryTotalAmount(entity);
	}

	@Override
	public int save(LoanPayRecord entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LoanPayRecord entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoanPayRecord entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

}

