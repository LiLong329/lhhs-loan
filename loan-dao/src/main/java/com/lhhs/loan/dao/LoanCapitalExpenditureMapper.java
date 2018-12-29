package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanFeesPlan;

public interface LoanCapitalExpenditureMapper {
    int deleteByPrimaryKey(String capitalExpenditureId);

    int insert(LoanCapitalExpenditure record);

    int insertSelective(LoanCapitalExpenditure record);

    LoanCapitalExpenditure selectByPrimaryKey(String capitalExpenditureId);

    int updateByPrimaryKeySelective(LoanCapitalExpenditure record);

    int updateByPrimaryKey(LoanCapitalExpenditure record);
    
    /**
     * 查询放款申请表-支出信息
     * @return
     */
    List<LoanCapitalExpenditure> selectLoanCapitalExpenditure(Map<String, Object> map);
    
    Integer delByOrderNo(String orderNo);
    
    /**
     * 根据订单号组装支出列表数据
     * @return
     */
    List<LoanFeesPlan> queryFeesPlanOutByOrderNo(String orderNo);
    
}