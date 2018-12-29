package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanCustomerBaseInfo;

public interface LoanCustomerBaseInfoMapper {
    int deleteByPrimaryKey(String customerId);

    int insert(LoanCustomerBaseInfo record);

    int insertSelective(LoanCustomerBaseInfo record);

    LoanCustomerBaseInfo selectByPrimaryKey(String customerId);

    int updateByPrimaryKeySelective(LoanCustomerBaseInfo record);

    int updateByPrimaryKey(LoanCustomerBaseInfo record);
    
    LoanCustomerBaseInfo selectByCustomerIdOrMobile(String customerId);
}