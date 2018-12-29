package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanAccountCard;

public interface LoanAccountCardMapper extends CrudDao<LoanAccountCard>{

    
    List<LoanAccountCard> queryAccountCard(Map<String, Object> map);
    
    List<LoanAccountCard> queryCardByCompany(@Param(value="companyId")String companyId,@Param(value="kind") String kind);
    
    LoanAccountCard queryCardByAccountAndCardNo(Map<String, Object> params);
    
    LoanAccountCard selectByPrimaryKey(Long id);
    
    int deleteByPrimaryKey(Long id);

	int updateByInvestCustomer(LoanAccountCard accountCard);
	
	List<LoanAccountCard> queryCardAndBalList(LoanAccountCard parm);

	void deleteByBankCardNo(String hidBankCardNo);

	LoanAccountCard getBankByCard(String bankCardNo);

	void deleteBankInfoByCustomerId(String customerId);

	List<LoanAccountCard> selectbankCarkList(String customerId);

	LoanAccountCard selectByAccountId(LoanAccountCard card);

	LoanAccountCard queryCusInfoAndBankInfo(Map<String, Object> map);
    
}