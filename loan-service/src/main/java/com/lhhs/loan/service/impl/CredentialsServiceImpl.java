/**
 * Project Name:loan-service
 * File Name:CredentialsServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年8月3日上午10:12:41
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanCredentialsMapper;
import com.lhhs.loan.dao.domain.LoanCredentials;
import com.lhhs.loan.service.CredentialsService;

/**
 * ClassName:CredentialsServiceImpl <br/>
 * Function: 资质模板管理 <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月3日 上午10:12:41 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class CredentialsServiceImpl implements CredentialsService{
	
	@Autowired
	private LoanCredentialsMapper credentialsMapper; 
	
	@Override
	public LoanCredentials get(String credentialsId) {
		return credentialsMapper.selectByPrimaryKey(credentialsId);
	}


	@Override
	public LoanCredentials get(LoanCredentials entity) {
		return null;
	}

	@Override
	public List<LoanCredentials> queryList(LoanCredentials entity) {
		entity.setPage(null);
		return credentialsMapper.queryCredentialsList(entity);
	}

	@Override
	public Page queryListPage(LoanCredentials entity) {
		Page page = entity.getPage();
		page.setPageIndex(entity.getPageIndex());
		page.setResultList(credentialsMapper.queryCredentialsList(entity));
		page.setTotalCount(credentialsMapper.queryCredentialsListCount(entity));
		return page;
	}

	@Override
	public int queryCount(LoanCredentials entity) {
		return 0;
	}

	@Override
	public int save(LoanCredentials entity) {
		return credentialsMapper.insertSelective(entity);
	}

	@Override
	public int update(LoanCredentials entity) {
		return credentialsMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int delete(LoanCredentials entity) {
		return credentialsMapper.deleteByPrimaryKey(entity.getCredentialsId());
	}
	
}

