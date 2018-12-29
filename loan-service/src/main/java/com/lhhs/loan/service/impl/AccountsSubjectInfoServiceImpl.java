/**
 * Project Name:loan-service
 * File Name:AccountsSubjectInfoServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月31日上午11:27:45
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.loan.dao.LoanAccountsSubjectInfoMapper;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.service.AccountsSubjectInfoService;

/**
 * ClassName:AccountsSubjectInfoServiceImpl <br/>
 * Function: 账户科目信息查询 <br/>
 * Date:     2017年7月31日 上午11:27:45 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class AccountsSubjectInfoServiceImpl extends CrudService<LoanAccountsSubjectInfoMapper,LoanAccountsSubjectInfo> implements AccountsSubjectInfoService {
	
	@Override
	public List<LoanAccountsSubjectInfo> selectSubjectByDirection(String direction) {
		LoanAccountsSubjectInfo parm=new LoanAccountsSubjectInfo();
		parm.setDirection(direction);
		return queryList(parm);
	}

}