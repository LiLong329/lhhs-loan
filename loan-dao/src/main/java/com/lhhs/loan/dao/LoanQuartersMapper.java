package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanQuarters;

public interface LoanQuartersMapper {
    int deleteByPrimaryKey(Integer lqQuartersId);

    int insert(LoanQuarters record);

    int insertSelective(LoanQuarters record);

    LoanQuarters selectByPrimaryKey(Integer lqQuartersId);

    int updateByPrimaryKeySelective(LoanQuarters record);

    int updateByPrimaryKey(LoanQuarters record);
    
	List<LoanQuarters> getQuarters(Map<String, Object> params);

	Integer getQuartersCount(Map<String, Object> params);
	
	List<LoanQuarters> getQuartersByParams(Map<String, Object> params);
	
	LoanQuarters selectByQuartersId(Integer lqQuartersId);
	
	int updateByQuartersId(LoanQuarters record);
	
	/**
	 * selectLoanQuartersByQuartersId:根据QuartersId查询对象
	 * @author kernel.org
	 * @param lqQuartersId
	 * @return
	 * @since JDK 1.8
	 */
	LoanQuarters selectLoanQuartersByQuartersId(Integer lqQuartersId);
	/**
	 * 查找上家岗位id
	 */
	Integer selectParentIdByIdDept(@Param("lqQuartersId")Integer lqQuartersId,@Param("lqDeptId")Integer lqDeptId);

}