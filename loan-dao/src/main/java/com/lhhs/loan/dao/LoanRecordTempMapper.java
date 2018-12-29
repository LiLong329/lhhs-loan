package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanRecordTemp;

public interface LoanRecordTempMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanRecordTemp record);

    int insertSelective(LoanRecordTemp record);

    LoanRecordTemp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanRecordTemp record);

    int updateByPrimaryKey(LoanRecordTemp record);

	int deleteByOrderNo(String orderNo);
}