package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanAuthgroupUser;

public interface LoanAuthgroupUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanAuthgroupUser record);

    int insertSelective(LoanAuthgroupUser record);

    LoanAuthgroupUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanAuthgroupUser record);

    int updateByPrimaryKey(LoanAuthgroupUser record);
    
    int deleteByUserId(Integer userId);
    
    List<Integer> selectAuthgroupIdsByUserId(Integer userId);
    
    List<LoanAuthgroupUser> getAuthGroupByUserId(LoanAuthgroupUser record);
}