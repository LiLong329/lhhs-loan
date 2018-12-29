package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanTransType;

public interface LoanTransTypeMapper {
    int deleteByPrimaryKey(String transType);

    int insert(LoanTransType record);

    int insertSelective(LoanTransType record);

    LoanTransType selectByPrimaryKey(String transType);

    int updateByPrimaryKeySelective(LoanTransType record);

    int updateByPrimaryKey(LoanTransType record);
}