/**
 * Project Name:loan-service
 * File Name:AccountsSubjectInfoService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月31日上午11:05:02
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;

/**
 * ClassName:AccountsSubjectInfoService <br/>
 * Function: 科目字典表 <br/>
 * Date:     2017年7月31日 上午11:05:02 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface AccountsSubjectInfoService extends BaseService<LoanAccountsSubjectInfo>{
	
	/**
	 * selectSubjectByDirection: 
	 * 根据支出方向查询科目<br/>
	 *
	 * @author chenyinhui
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanAccountsSubjectInfo> selectSubjectByDirection(String direction);
	
}