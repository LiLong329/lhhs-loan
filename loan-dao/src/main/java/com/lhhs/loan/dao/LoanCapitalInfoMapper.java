package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanRecordTemp;

public interface LoanCapitalInfoMapper {
    int deleteByPrimaryKey(String capitalInfoId);

    int insert(LoanCapitalInfo record);

    int insertSelective(LoanCapitalInfo record);

    LoanCapitalInfo selectByPrimaryKey(String capitalInfoId);

    int updateByPrimaryKeySelective(LoanCapitalInfo record);

    int updateByPrimaryKey(LoanCapitalInfo record);
    
    /**
     * updateByBankCardIdAndOrderno:根据银行卡号和订单号更新放款申请表
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    int updateByBankCardIdAndOrderno(LoanCapitalInfo record);
    
    /**
     * selectLoanCapitalInfoByBankCardId:根据银行卡号查询放款申请表中的实体bean
     * @author kernel.org
     * @param bankcardId
     * @return
     * @since JDK 1.8
     */
    LoanCapitalInfo selectLoanCapitalInfoByBankCardIdAndOrderno(Map<String, Object> map);
    
    /**
     * 查询放款申请表-资金方基本信息
     * @return
     */
    List<LoanCapitalInfo> selectLoanCapitalInfo(Map<String, Object> map);
    
    List<LoanCapitalInfo> getCapitalInfoByOrderNo(String orderNo);
    /**
     * 删除放款申请表-资金府基本信息
     */
    Integer delByOrderNo(String orderNo);
    /**
     * 根据订单号查询放款总额
     */
    Map<String,Object> searchAmountPaidByOrderNo(String orderNo);
    
    /**
     * 根据订单号组装放款申请数据
     * @return
     */
    List<LoanRecordTemp> queryLoanRecordByOrderNo(String orderNo);
    
}