/**
 * Project Name:loan-service
 * File Name:UnionCompanyService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年8月5日上午10:57:03
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.LoanUnionCompany;

/**
 * ClassName:UnionCompanyService <br/>
 * Function: 集团公司管理服务类 <br/>
 * Date:     2017年8月5日 上午10:57:03 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface UnionCompanyService extends BaseService<LoanUnionCompany> {
	//查询所有分公司
	List<LoanUnionCompany> queryAllCompany(String unionId);
	//查询所有集团
	List<LoanUnionCompany> queryAllUnion();
	//查询所有分公司
	Map<String,String> queryAllCompanyMap();
	
	List<LoanUnionCompany> queryCompanyByCity(String city);
	
	List<LoanUnionCompany> queryList(Map<String,Object> map);
	//根据条件获取分公司
	List<LoanUnionCompany> getCompanyList(LoanUnionCompany loanUnionCompany);
	
}

