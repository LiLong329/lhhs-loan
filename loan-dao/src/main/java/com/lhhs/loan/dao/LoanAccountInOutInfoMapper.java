package com.lhhs.loan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfo;

public interface LoanAccountInOutInfoMapper {
    int deleteByPrimaryKey(Long transNo);

    int insert(LoanAccountInOutInfo record);

    int insertSelective(LoanAccountInOutInfo record);

    LoanAccountInOutInfo selectByPrimaryKey(Long transNo);

    int updateByPrimaryKeySelective(LoanAccountInOutInfo record);

    int updateByPrimaryKey(LoanAccountInOutInfo record);
    
    List<Map<String,Object>> queryTransRecord(Map<String, Object> params);
   
    int queryTransRecordTotal(Map<String, Object> params);
    
    Map<String, Object>  queryDetailByTransNo(String transNo);
    
    List<Map<String,Object>> queryTransApproveRecord(Map<String, Object> params);
    
    Map<String,Object>  queryTransTotal(Map<String, Object> params);

	LoanAccountInOutInfo getAccountInOutInfo(String transNo);

	LoanOrderInfo queryOrderPlanTemp(SecurityUser user);

	List<LoanAccountInOutInfo> queryDepositApplyList(LoanAccountInOutInfo entity);

	int queryDepositApplyCount(LoanAccountInOutInfo entity);

	Map<String, BigDecimal> queryTotalAmount(LoanAccountInOutInfo entity);
    
}