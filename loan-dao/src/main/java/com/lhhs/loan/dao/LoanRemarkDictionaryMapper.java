package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanRemarkDictionary;

public interface LoanRemarkDictionaryMapper {
    int deleteByPrimaryKey(String remarkId);

    int insert(LoanRemarkDictionary record);

    int insertSelective(LoanRemarkDictionary record);

    LoanRemarkDictionary selectByPrimaryKey(String remarkId);

    int updateByPrimaryKeySelective(LoanRemarkDictionary record);

    int updateByPrimaryKey(LoanRemarkDictionary record);
}