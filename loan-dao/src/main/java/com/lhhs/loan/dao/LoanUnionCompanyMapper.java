package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanUnionCompany;

public interface LoanUnionCompanyMapper extends CrudDao<LoanUnionCompany>{
	List<LoanUnionCompany> queryListPage(LoanUnionCompany entity);

	List<LoanUnionCompany> queryAllCompany();
	
	List<LoanUnionCompany> queryAllUnion();
	
	List<LoanUnionCompany> queryGroupCompany();
	
	List<LoanUnionCompany> queryCompany(String companyId);
	
	List<LoanUnionCompany> queryCompanySelective(Map<String,Object> params);
	
	List<LoanUnionCompany> selectProvinceByCompanyList(Map<String, Object> map);
	
	List<LoanUnionCompany> selectCompanyByProvinceCity(Map<String, Object> map);
	
	List<LoanUnionCompany> queryCompanyByCity(String city);
	
	List<LoanUnionCompany> queryList(Map<String,Object> params);
	
	List<LoanUnionCompany> getAllCompanyByUnionId(Map<String,Object> param);
}