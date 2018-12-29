package com.lhhs.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;

public interface LoanAccountsSubjectInfoMapper extends CrudDao<LoanAccountsSubjectInfo>{
	
	List<LoanAccountsSubjectInfo> selectSubjectByDirection(String direction);
	
	
	/**
	 * 查询项目
	 * 
	 * @param direction
	 * @param subjectTypeArraySql  example '1,2,3'
	 * @return
	 */
	List<LoanAccountsSubjectInfo> selectSubjectByDirectionSubArray(@Param("direction") String direction ,@Param("subjectTypeArraySql") String subjectTypeArraySql);
	
}