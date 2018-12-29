package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanCustomerInfo;

public interface LoanCustomerInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoanCustomerInfo record);

    int insertSelective(LoanCustomerInfo record);

    LoanCustomerInfo selectByPrimaryKey(String id);
    
    LoanCustomerInfo selectByCustomerId(String customerId);

    int updateByPrimaryKeySelective(LoanCustomerInfo record);

    int updateByPrimaryKey(LoanCustomerInfo record);
    
    /**
     * updateLoanCustomerInfoByEntity:根据LoanCustomerInfo实体bean更新
     * @author kernel.org
     * @param entity
     * @return
     * @since JDK 1.8
     */
    int updateLoanCustomerInfoByEntity(LoanCustomerInfo entity);
}