package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanTransAccounts;

public interface LoanTransAccountsMapper {
    int deleteByPrimaryKey(Long transId);

    int insert(LoanTransAccounts record);

    int insertSelective(LoanTransAccounts record);

    LoanTransAccounts selectByPrimaryKey(String transId);

    int updateByPrimaryKeySelective(LoanTransAccounts record);

    int updateByPrimaryKey(LoanTransAccounts record);

	List<LoanTransAccounts> transAccountsList(Map<String, Object> map);

	Integer transAccountsListCount(Map<String, Object> map);

	List<Map<String, Object>> queryAccountCustType();

	List<Map<String, Object>> queryAccountCustProperty(Map<String, Object> map);

	Map<String, Object> transExamineAdd(LoanTransAccounts transAccounts);

	int updateByPayPlanCompany(LoanTransAccounts accounts);

	List<Map<String, Object>> queryAccountSubject(Map<String, Object> map);

	Map<String, Object> queryAccountSubject(String custId);

//	LoanAccountInfo getAccountsByCustId(String custId);

	LoanTransAccounts getAccountInOutInfo(String transNo);

	LoanTransAccounts getAccountsByCustId(Map<String, Object> map);

	int selectByCustId(String custId);

	List<Map<String, Object>> queryAllManager(String accountManagerDepartmentNo);
}