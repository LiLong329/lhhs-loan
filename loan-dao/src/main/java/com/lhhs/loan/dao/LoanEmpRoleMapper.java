package com.lhhs.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanEmpRole;

public interface LoanEmpRoleMapper {
    int deleteByPrimaryKey(Integer lerErId);

    int insert(LoanEmpRole record);

    int insertSelective(LoanEmpRole record);

    LoanEmpRole selectByPrimaryKey(Integer lerErId);

    int updateByPrimaryKeySelective(LoanEmpRole record);

    int updateByPrimaryKey(LoanEmpRole record);
    
    LoanEmpRole isEmpRole(@Param("empId") Integer empId,@Param("roleId") Integer roleId);
    
    List<LoanEmpRole> allMyEmpRole(@Param("empId") Integer empId);
    
    List<Integer> getRoleIdByEmpId(@Param("empId") Integer empId);
}