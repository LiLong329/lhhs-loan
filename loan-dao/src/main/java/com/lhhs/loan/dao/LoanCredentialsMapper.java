package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanCredentials;

public interface LoanCredentialsMapper {
    int deleteByPrimaryKey(String credentialsId);

    int insert(LoanCredentials record);

    int insertSelective(LoanCredentials record);

    LoanCredentials credentialsId(String credentialsId);

    int updateByPrimaryKeySelective(LoanCredentials record);

    int updateByPrimaryKey(LoanCredentials record);

	List<LoanCredentials> queryCredentialsList(LoanCredentials entity);

	int queryCredentialsListCount(LoanCredentials entity);

	LoanCredentials selectByPrimaryKey(String credentialsId);

}