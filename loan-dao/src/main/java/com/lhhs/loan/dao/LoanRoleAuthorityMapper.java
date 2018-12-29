package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanRoleAuthority;

public interface LoanRoleAuthorityMapper {
    int deleteByPrimaryKey(Integer lraRaId);

    int insert(LoanRoleAuthority record);

    int insertSelective(LoanRoleAuthority record);

    LoanRoleAuthority selectByPrimaryKey(Integer lraRaId);

    int updateByPrimaryKeySelective(LoanRoleAuthority record);

    int updateByPrimaryKey(LoanRoleAuthority record);
    
    int delByRoleId(Integer lraRoleId);
}