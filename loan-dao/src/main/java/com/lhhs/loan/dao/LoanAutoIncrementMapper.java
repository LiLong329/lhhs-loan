package com.lhhs.loan.dao;

import java.util.Map;

import com.lhhs.loan.dao.domain.LoanAutoIncrement;

public interface LoanAutoIncrementMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanAutoIncrement record);

    int insertSelective(LoanAutoIncrement record);

    LoanAutoIncrement selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanAutoIncrement record);

    int updateByPrimaryKey(LoanAutoIncrement record);

	LoanAutoIncrement getAutoIncrement(Map<String, Object> map);
}