package com.lhhs.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanAuthority;

public interface LoanAuthorityMapper {
    int deleteByPrimaryKey(Integer laAuthorityId);

    int insert(LoanAuthority record);

    int insertSelective(LoanAuthority record);

    LoanAuthority selectByPrimaryKey(Integer laAuthorityId);

    int updateByPrimaryKeySelective(LoanAuthority record);

    int updateByPrimaryKey(LoanAuthority record);
    
    List<LoanAuthority> getAuthoritys(@Param("type") int type,@Param("roleId") int roleId);
    
    List<LoanAuthority> findAuthListByEmpId(@Param("type") int type,@Param("empId") int empId);
	
    List<LoanAuthority>  getAuthList();
    List<LoanAuthority> getMenuAuthList();
}