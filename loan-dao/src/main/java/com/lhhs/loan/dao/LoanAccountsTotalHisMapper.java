package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanAccountsTotalHis;

public interface LoanAccountsTotalHisMapper {
    int deleteByPrimaryKey(String accountId);

    int insert(LoanAccountsTotalHis record);

    int insertSelective(LoanAccountsTotalHis record);

    LoanAccountsTotalHis selectByPrimaryKey(String accountId);

    int updateByPrimaryKeySelective(LoanAccountsTotalHis record);

    int updateByPrimaryKey(LoanAccountsTotalHis record);
}