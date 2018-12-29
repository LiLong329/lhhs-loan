package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanPayPlanTemp;

public interface LoanPayPlanTempMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanPayPlanTemp record);

    int insertSelective(LoanPayPlanTemp record);

    LoanPayPlanTemp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanPayPlanTemp record);

    int updateByPrimaryKey(LoanPayPlanTemp record);
    /**
     * 根据订单号删除临时表数据
     * @param orderNo 订单编号
     * @return
     */
	int deleteByOrderNo(String orderNo);
}