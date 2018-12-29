package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanRoleCompany;

public interface LoanRoleCompanyMapper {
    int deleteByPrimaryKey(Integer lrcId);

    int insert(LoanRoleCompany record);

    int insertSelective(LoanRoleCompany record);

    LoanRoleCompany selectByPrimaryKey(Integer lrcId);

    int updateByPrimaryKeySelective(LoanRoleCompany record);

    int updateByPrimaryKey(LoanRoleCompany record);
    
    List<LoanRoleCompany> queryRoleCompany(String roleId);
    
    int deleteByRoleId(String roleId);
    
    List<String>  queryCompanyByListRoleId(List<Integer> roleIds);
}