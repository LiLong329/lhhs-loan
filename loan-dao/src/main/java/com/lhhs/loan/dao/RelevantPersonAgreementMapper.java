package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.RelevantPersonAgreement;

public interface RelevantPersonAgreementMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(RelevantPersonAgreement record);

    int insertSelective(RelevantPersonAgreement record);

    RelevantPersonAgreement selectByPrimaryKey(Integer id);
    
    RelevantPersonAgreement selectByRpoId(Integer rpoId);

    int updateByPrimaryKeySelective(RelevantPersonAgreement record);

    int updateByPrimaryKey(RelevantPersonAgreement record);

	List<RelevantPersonAgreement> queryList(RelevantPersonAgreement params);

	void delete(RelevantPersonAgreement relevantPersonAgreement);

	void insertList(List<RelevantPersonAgreement> totalLoanList);
}