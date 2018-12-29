package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanInvestCustomerInfo;

public interface LoanInvestCustomerInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoanInvestCustomerInfo record);

    int insertSelective(LoanInvestCustomerInfo record);

    LoanInvestCustomerInfo selectByPrimaryKey(String id);
    
    LoanInvestCustomerInfo selectByCustomerId(String customerId);

    int updateByPrimaryKeySelective(LoanInvestCustomerInfo record);

    int updateByPrimaryKey(LoanInvestCustomerInfo record);

	List<LoanInvestCustomerInfo> investCustomerList(Map<String, Object> map);

	Integer investCustomerListCount(Map<String, Object> map);

	List<Map<String, Object>> queryAllDepts();

	int selectByMobile(String mobile);

	List<Map<String, Object>> queryAllCompany(Map<String, Object> paramsMap);

	List<Map<String, Object>> queryAllManager(String affiliatedCompanyId);

}