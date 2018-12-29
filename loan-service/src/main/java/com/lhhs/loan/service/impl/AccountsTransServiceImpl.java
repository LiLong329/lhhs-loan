/**
 * Project Name:loan-service
 * File Name:AccountsTransServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月31日上午11:27:45
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanAccountsTransMapper;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.vo.LoanAccountsTransVo;
import com.lhhs.loan.service.AccountsTransService;

/**
 * ClassName:AccountsTransServiceImpl <br/>
 * Function: 账户交易记录 <br/>
 * Date:     2017年7月31日 上午11:27:45 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service("accountsTransService")
public class AccountsTransServiceImpl extends CrudService<LoanAccountsTransMapper,LoanAccountsTrans> implements AccountsTransService {

	@Override
	public Page queryOrganizationTransList(LoanAccountsTransVo entity) {
		Page page = entity.getPage();
		page.setResultList(dao.queryOrganizationTransList(entity));
		page.setTotalCount(dao.queryOrganizationTransCount(entity));
		return page;
	}

	@Override
	public Map<String, BigDecimal> queryOrganizationTransTotalAmount(
			LoanAccountsTransVo entity) {
		return dao.queryOrganizationTransTotalAmount(entity);
	}

	@Override
	public LoanAccountsTrans querySumAmount(LoanAccountsTrans entity) {
		return dao.querySumAmount(entity);
	}

}