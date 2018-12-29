/**
 * Project Name:loan-service
 * File Name:LoanRecordServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月31日上午11:27:45
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
import com.lhhs.loan.dao.LoanCapitalInfoMapper;
import com.lhhs.loan.dao.LoanRecordMapper;
import com.lhhs.loan.dao.domain.LoanRecord;
import com.lhhs.loan.dao.domain.LoanRecordTemp;
import com.lhhs.loan.dao.domain.vo.LoanRecordVo;
import com.lhhs.loan.service.LoanRecordService;

/**
 * ClassName:LoanRecordServiceImpl <br/>
 * Function: 放款记录查询 <br/>
 * Date:     2017年7月31日 上午11:27:45 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service("loanRecordService")
public class LoanRecordServiceImpl implements LoanRecordService {
	
	@Autowired
	private LoanRecordMapper loanRecordMapper;
	@Autowired
	private LoanCapitalInfoMapper loanCapitalInfoMapper;

	@Override
	public LoanRecordVo get(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public LoanRecordVo get(LoanRecordVo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoanRecordVo> queryList(LoanRecordVo entity) {
		entity.setPage(null);
		return loanRecordMapper.queryListPage(entity);
	}

	@Override
	public Page queryListPage(LoanRecordVo entity) {
		Page page = entity.getPage();
		page.setResultList(loanRecordMapper.queryListPage(entity));
		page.setTotalCount(loanRecordMapper.queryCountPage(entity));
		return page;
	}

	@Override
	public int save(LoanRecordVo entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LoanRecordVo entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoanRecordVo entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(LoanRecordVo entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, BigDecimal> queryTotalAmount(LoanRecordVo entity) {
		return loanRecordMapper.queryTotalAmount(entity);
	}

	@Override
	public List<LoanRecordTemp> queryLoanRecordByOrderNo(String orderNo) {
		return loanCapitalInfoMapper.queryLoanRecordByOrderNo(orderNo);
	}


	@Override
	public List<Map<String,Object>> queryMonthStatisticsList(LoanRecordVo entity) {
		return loanRecordMapper.queryMonthStatisticsList(entity);
	}


	@Override
	public BigDecimal queryWeekStatisticsList(LoanRecordVo entity) {
		BigDecimal queryWeek = loanRecordMapper.queryWeekStatisticsList(entity);
		if(queryWeek==null){
			queryWeek=new BigDecimal(0);
		}
		return queryWeek;
	}


	@Override
	public List<Map<String,Object>> queryDayStatisticsList(LoanRecordVo entity) {
		return loanRecordMapper.queryDayStatisticsList(entity);
	}

}