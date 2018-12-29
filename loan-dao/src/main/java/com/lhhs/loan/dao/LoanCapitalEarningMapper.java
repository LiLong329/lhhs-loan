package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanFeesPlan;

public interface LoanCapitalEarningMapper {
    int deleteByPrimaryKey(String capitalEarningId);

    int insert(LoanCapitalEarning record);

    int insertSelective(LoanCapitalEarning record);

    LoanCapitalEarning selectByPrimaryKey(String capitalEarningId);

    int updateByPrimaryKeySelective(LoanCapitalEarning record);

    int updateByPrimaryKey(LoanCapitalEarning record);
    
    /**
     * 查询放款申请表-收入信息
     * @return
     */
    List<LoanCapitalEarning> selectLoanCapitalEarning(Map<String, Object> map);
    /**
     * 删除放款申请表信息根据订单号
     */
    Integer delByOrderNo(String orderNo);
    
    /**
     * 根据订单号组装收入列表数据
     * @return
     */
    List<LoanFeesPlan> queryFeesPlanInByOrderNo(String orderNo);
}