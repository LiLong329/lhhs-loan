/**
 * Project Name:loan-service
 * File Name:LoanAccountInfoServiceImpl.java
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
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.vo.LoanAccountInfoVo;
import com.lhhs.loan.service.LoanAccountInfoService;

/**
 * ClassName:LoanAccountInfoServiceImpl <br/>
 * Function: 账户信息查询 <br/>
 * Date:     2017年7月31日 上午11:27:45 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service("loanAccountInfoService")
public class LoanAccountInfoServiceImpl implements LoanAccountInfoService {
	
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;

	@Override
	public LoanAccountInfoVo get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanAccountInfoVo get(LoanAccountInfoVo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List queryList(LoanAccountInfoVo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryListPage(LoanAccountInfoVo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(LoanAccountInfoVo entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LoanAccountInfoVo entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoanAccountInfoVo entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(LoanAccountInfoVo entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Page queryCompanyAccountList(LoanAccountsTotal entity) {
		Page page = entity.getPage();
		page.setResultList(loanAccountInfoMapper.queryCompanyAccountList(entity));
		page.setTotalCount(loanAccountInfoMapper.queryCompanyAccountListCount(entity));
		return page;
	}

	@Override
	public Page queryOrganizationAccountList(LoanAccountsTotal entity) {
		Page page = entity.getPage();
		page.setResultList(loanAccountInfoMapper.queryOrganizationAccountList(entity));
		page.setTotalCount(loanAccountInfoMapper.queryOrganizationAccountCount(entity));
		return page;
	}

	@Override
	public Map<String,Object> queryOrganizationAccountTotalAmount(LoanAccountsTotal entity) {
		return loanAccountInfoMapper.queryOrganizationAccountTotalAmount(entity);
	}

	@Override
	public LoanAccountInfo selectByOwnerId(String outAccountCustId) {
		return loanAccountInfoMapper.selectByOwnerId(outAccountCustId);
	}

}