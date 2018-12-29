package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanPayPlanCompanyTemp;

public interface LoanPayPlanCompanyTempMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanPayPlanCompanyTemp record);

    int insertSelective(LoanPayPlanCompanyTemp record);

    LoanPayPlanCompanyTemp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanPayPlanCompanyTemp record);

    int updateByPrimaryKey(LoanPayPlanCompanyTemp record);

	int deleteByOrderNo(String orderNo);

	List<LoanPayPlanCompanyTemp> selectByTransAccounts(String transId);
}