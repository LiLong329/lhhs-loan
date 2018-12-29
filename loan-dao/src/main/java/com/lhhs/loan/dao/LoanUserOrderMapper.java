package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanUserOrder;

public interface LoanUserOrderMapper {
    int deleteByPrimaryKey(Byte id);

    int insert(LoanUserOrder record);

    int insertSelective(LoanUserOrder record);

    LoanUserOrder selectByPrimaryKey(Byte id);

    int updateByPrimaryKeySelective(LoanUserOrder record);

    int updateByPrimaryKey(LoanUserOrder record);
    
    
    List<LoanUserOrder> selectLoanUserOrderById(int intentUserId);
    
    LoanUserOrder selectLoanUserOrderByOrderNo(String orderNo);
}