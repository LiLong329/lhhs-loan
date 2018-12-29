package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanRoleNode;

public interface LoanRoleNodeMapper {
    int deleteByPrimaryKey(Integer lrnId);

    int insert(LoanRoleNode record);

    int insertSelective(LoanRoleNode record);

    LoanRoleNode selectByPrimaryKey(Integer lrnId);

    int updateByPrimaryKeySelective(LoanRoleNode record);

    int updateByPrimaryKey(LoanRoleNode record);
    
    int deleteByRoleId(String roleId);
    
    List<String> queryNodeByListRoleId(List<Integer> roleIds);
}