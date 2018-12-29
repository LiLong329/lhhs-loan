package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;

public interface LoanOrderInfoDetailMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(LoanOrderInfoDetail record);

    int insertSelective(LoanOrderInfoDetail record);

    LoanOrderInfoDetail selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(LoanOrderInfoDetail record);

    int updateByPrimaryKey(LoanOrderInfoDetail record);
    
    /**
     * 通过订单号更新订单详情表中的部分字段
     * @param record
     * @return
     */
    int updateOrderInfoDetailByOrderNo(LoanOrderInfoDetail record);
}