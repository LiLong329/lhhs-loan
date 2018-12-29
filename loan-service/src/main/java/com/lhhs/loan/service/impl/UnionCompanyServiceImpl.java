/**
 * Project Name:loan-service
 * File Name:UnionCompanyServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年8月5日上午10:58:49
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanUnionCompanyMapper;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.UnionCompanyService;

/**
 * ClassName:UnionCompanyServiceImpl <br/>
 * Function: 集团公司管理服务类具体实现 <br/>
 * Date:     2017年8月5日 上午10:58:49 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
@SuppressWarnings("all")
public class UnionCompanyServiceImpl extends CrudService<LoanUnionCompanyMapper,LoanUnionCompany> implements UnionCompanyService {
	private static final Logger LOGGER = Logger.getLogger(UnionCompanyServiceImpl.class);
	

	@Override
	public Page queryListPage(LoanUnionCompany entity) {
		Page page  = entity.getPage();
		page.setResultList(dao.queryListPage(entity));
		page.setTotalCount(dao.queryCount(entity));
		return page;
	}
	
	@Override
	public List<LoanUnionCompany> getCompanyList(LoanUnionCompany entity){
		return dao.queryListPage(entity);
	}
	
	
	

	@Override
	public int save(LoanUnionCompany entity) {
		if(StrUtils.isNullOrEmpty(entity.getCompanyId())){
			entity.setCompanyId(CommonUtils.getAutoIncrement("loan_union_company", null, 5).toString());
			entity.setParentCompanyId(entity.getUnionId());
			entity.setLevel("2");
			entity.setStatus("03");
			return dao.insertSelective(entity);
		}else{
			if(!"1".equals(entity.getLevel())){
				entity.setParentCompanyId(entity.getUnionId());
			}
			return dao.updateByPrimaryKeySelective(entity);
		}
	}

	@Override
	public List<LoanUnionCompany> queryAllCompany(String unionId) {
		return dao.queryAllCompany();
	}

	@Override
	public List<LoanUnionCompany> queryAllUnion() {
		return dao.queryAllUnion();
	}

	@Override
	public Map<String, String> queryAllCompanyMap() {
		Map<String, String> companyMap=new HashMap<String, String>();
		List<LoanUnionCompany> list=dao.queryAllCompany();
		for(LoanUnionCompany temp:list){
			companyMap.put(temp.getCompanyId(), temp.getCompanyName());
		}
		return companyMap;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.UnionCompanyService#queryCompanyByCity(java.lang.String)
	 */
	 
	@Override
	public List<LoanUnionCompany> queryCompanyByCity(String city) {
		
		// TODO Auto-generated method stub
		return dao.queryCompanyByCity(city);
	}

	@Override
	public List<LoanUnionCompany> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}


}

